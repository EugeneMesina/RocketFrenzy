package androidsupersquad.rocketfrenzy.MiniGame.AccGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

import java.util.Random;

import androidsupersquad.rocketfrenzy.R;

/**
 * View used for the AccGame
 *
 * Created by Jimmy Chao (Lazer)
 */

public class SimulationView extends View implements SensorEventListener {

    /** Sensor manager to handle sensors */
    private SensorManager sensorManager;
    /** Images of the planet and rocket */
    private Bitmap mPlanet, mBitmap;
    /** The physics for the rocket */
    private Particle mRocket;
    /** Provides data of the current display */
    private Display mDisplay;
    /** Size of the planet */
    private static final int PLANET_SIZE =120;
    /**
     * The floats represent the following:
     *  1) The x origin of the screen
     *  2) The y origin of the screen
     *  3) The horizontal bound of the screen
     *  4) The vertical bound of the screen
     *  5) Current x position of the accelerometer
     *  6) Current y position of the accelerometer
     *  7) Current z position of the accelerometer
     *
     */
    private float mXOrigin, mYOrigin , mHorizontalBound, mVerticalBound, mSensorX, mSensorY,mSensorZ;
    /** Last time the sensor sent an update */
    private long mSensorTimeStamp;
    /**
     * The ints represent the following:
     *  1) The player's score
     *  2) How long to sleep
     *  3) The planet width
     *  4) The planet Height
     */
    int score, sleep, planetWidth, planetHeight;
    /** Used to ensure that the score is only incremented once */
    boolean scorekeeper;
    /** The planet's x and y coordinates */
    private int xPlanet, yPlanet;
    /** Whether the game is running or not*/
    private boolean isRunning;

    /**
     * The instantiation for the custom view
     * @param context the mainActivity ContextView
     * @param attributeSet
     */
    public SimulationView(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
        //set sensors
        isRunning = true;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        //set accelerometer event listener
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_GAME);
       //set new particle class to move rocket and handle boundries
        mRocket =new Particle();
        //set the drawable for the rocket

        Bitmap rocket= BitmapFactory.decodeResource(getResources(),R.drawable.tiny_rocket);
        planetWidth = rocket.getWidth()/2;
        planetHeight = rocket.getHeight()/2;
        mBitmap = Bitmap.createScaledBitmap(rocket, planetWidth, planetHeight,true);
        //set the drawable for the basket
        Bitmap basket= BitmapFactory.decodeResource(getResources(),R.drawable.earth);
        mPlanet = Bitmap.createScaledBitmap(basket, PLANET_SIZE, PLANET_SIZE,true);
        //set the field

    // initial the current score
        score=0;
    //Get phone's screen width and height to set origin and bound
        WindowManager mWindowManager =(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mDisplay= mWindowManager.getDefaultDisplay();

        mXOrigin = mDisplay.getWidth()/2;
        mYOrigin = mDisplay.getHeight()/2;
        mHorizontalBound = mDisplay.getWidth()/2;
        mVerticalBound = mDisplay.getHeight()/2;

        xPlanet = Math.round(((mXOrigin- PLANET_SIZE /2)-20));
        yPlanet = Math.round(((mYOrigin- PLANET_SIZE /2)-755));

        sleep = 0;
        scorekeeper=true;
    }

    /**
     * A thread that continously runs
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
            //draw the Basket in it's static place
            canvas.drawBitmap(mPlanet, xPlanet, yPlanet, null);
            //update the ball position
            mRocket.updatePosition(mSensorX, mSensorY, mSensorZ, mSensorTimeStamp);
            mRocket.resolveCollisionWithBounds(mHorizontalBound, mVerticalBound);
            /*
            Matrix matrix = new Matrix();
            matrix.postRotate(mRocket.mAngle);
            Bitmap tempBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
            */
            canvas.drawBitmap(mBitmap, (mXOrigin - planetWidth / 2) + mRocket.mPosX, (mYOrigin - planetHeight / 2) - mRocket.mPosY, null);

            //checkvalues to see if the ball image touches the basket image
            Integer xPos = Math.round((mXOrigin - planetWidth / 2) + mRocket.mPosX);
            Integer YPos = Math.round((mYOrigin - planetHeight / 2) - mRocket.mPosY);

            //add score if the ball hits the basket image
            if ((xPos <= xPlanet + 80 && xPos >= xPlanet - 80) && (yPlanet + 80 >= YPos && yPlanet - 80 <= YPos) && scorekeeper) {
                System.out.println("HERE");
                score++;
                scorekeeper = false;
                mRocket.throwParticle();
                scramble();
            }
            //sleep so the player doesn't just keep the ball in one place and indefinitely get an infinite score
            sleep++;
            if (sleep == 100) {
                sleep = 0;
                scorekeeper = true;

            }
            setScore(canvas);
            invalidate();
    }

    /**
     * This displays the score on the view
     * @param canvas The current display to paint on
     */
    private void setScore(Canvas canvas){
        Paint titlePaint = new Paint();
        titlePaint.setColor(0xffffffff);
        titlePaint.setAntiAlias(true);
        titlePaint.setTypeface(Typeface.MONOSPACE);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTextSize(100);
        canvas.drawText("Score "+score, mXOrigin, mYOrigin*2 - 50,titlePaint);


    }

    /**
     * Make the app scale to size per device
     * @param w The new width
     * @param h The new height
     * @param oldw The old width
     * @param oldh The old height
     */
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mXOrigin = w * 0.5f;
        mYOrigin = h * 0.5f;

        mHorizontalBound = (w - planetWidth) * 0.5f;
        mVerticalBound = (h - planetHeight) * 0.5f;
    }

    /**
     * On sensor change
     * find if the sensor change is the accelerometer
     * and find if the device rotates
     * and set the x and y values accordingly
     *
     * @param event The type of sensor and it's data
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mSensorTimeStamp = System.nanoTime();
            //checks to see if it hasn't rotated
            if (mDisplay.getRotation() == Surface.ROTATION_0) {
                mSensorX = event.values[0];
                mSensorY = event.values[1];
                mSensorZ = event.values[2];
            }
            //checks to see if it has rotated
            else if (mDisplay.getRotation() == Surface.ROTATION_90) {
                mSensorX = event.values[1];
                mSensorY = event.values[0];
                mSensorZ = event.values[2];
            }
        }
    }

    /**
     * Unused
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Registers the listener
     */
    public void startSimulation(){
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * Unregisters the listener
     */
    public void stopSimulation() {
        sensorManager.unregisterListener(this);
    }

    /**
     * Ends the game
     */
    public void end()
    {
        isRunning = false;
        mRocket.freeze();
    }

    /**
     * Chooses a random location for the
     */
    public void scramble()
    {
        Random rand = new Random();
        xPlanet = 100 + rand.nextInt((int)mXOrigin*2 - 200);
        yPlanet = 100 + rand.nextInt((int)mYOrigin*2 - 200);
    }

    /**
     * Pause the game
     */
    public void freeze()
    {
        mRocket.freeze();
    }

    /**
     * Unpause the game
     */
    public void unFreeze()
    {
        mRocket.unFreeze();
    }

    /**
     * Get the score
     * @return the score
     */
    public int getScore()
    {
        return score;
    }
}
