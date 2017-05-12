package androidsupersquad.rocketfrenzy.MiniGame.AccGame;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import androidsupersquad.rocketfrenzy.R;

/**
 * Created by Jimmy Chao on 3/15/2017.
 * 012677182
 */

public class SimulationView extends View implements SensorEventListener {
    //Setting variables and bitmaps
    private SensorManager sensorManager;
    private Bitmap mBasket, mBitmap;
    private Particle mBall;
    private Display mDisplay;
    private static final int BASKET_SIZE=120;
    private float mXOrigin, mYOrigin , mHorizontalBound, mVerticalBound, mSensorX, mSensorY,mSensorZ;
    private long mSensorTimeStamp;
    int score, sleep, ballWidth, ballHeight;
    boolean scorekeeper;
    private int xBasket, yBasket;
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
       //set new particle class to move ball and handle boundries
        mBall=new Particle();
        //set the drawable for the ball

        Bitmap ball= BitmapFactory.decodeResource(getResources(),R.drawable.tiny_rocket);
        ballWidth = ball.getWidth()/2;
        ballHeight = ball.getHeight()/2;
        mBitmap = Bitmap.createScaledBitmap(ball,ballWidth,ballHeight,true);
        //set the drawable for the basket
        Bitmap basket= BitmapFactory.decodeResource(getResources(),R.drawable.basket);
        mBasket = Bitmap.createScaledBitmap(basket,BASKET_SIZE,BASKET_SIZE,true);
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

        xBasket= Math.round(((mXOrigin-BASKET_SIZE/2)-20));
        yBasket= Math.round(((mYOrigin-BASKET_SIZE/2)-755));

        sleep=0;
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
            canvas.drawBitmap(mBasket, xBasket, yBasket, null);
//update the ball position
            mBall.updatePosition(mSensorX, mSensorY, mSensorZ, mSensorTimeStamp);
            mBall.resolveCollisionWithBounds(mHorizontalBound, mVerticalBound);

//        Matrix matrix = new Matrix();
//        matrix.postRotate(mBall.mAngle);
//        Bitmap tempBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);

            canvas.drawBitmap(mBitmap, (mXOrigin - ballWidth / 2) + mBall.mPosX, (mYOrigin - ballHeight / 2) - mBall.mPosY, null);

            //checkvalues to see if the ball image touches the basket image
            Integer xPos = Math.round((mXOrigin - ballWidth / 2) + mBall.mPosX);
            Integer YPos = Math.round((mYOrigin - ballHeight / 2) - mBall.mPosY);
//add score if the ball hits the basket image

            if ((xPos <= xBasket + 80 && xPos >= xBasket - 80) && (yBasket + 80 >= YPos && yBasket - 80 <= YPos) && scorekeeper) {
                System.out.println("HERE");
                score++;
                scorekeeper = false;
                mBall.throwParticle();
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
     * @param canvas
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
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mXOrigin = w * 0.5f;
        mYOrigin = h * 0.5f;

        mHorizontalBound = (w - ballWidth) * 0.5f;
        mVerticalBound = (h - ballHeight) * 0.5f;
    }

    /**
     * On sensor change
     * find if the sensor change is the accelerometer
     * and find if the device rotates
     * and set the x and y values accordingly
     * @param event
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

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    //register/unregister the listener
    public void startSimulation(){
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_GAME);
    }
    public void stopSimulation(){
        sensorManager.unregisterListener(this);
    }

    public void end()
    {
        isRunning = false;
        mBall.freeze();
    }

    public void scramble()
    {
        Random rand = new Random();
        xBasket = 100 + rand.nextInt((int)mXOrigin*2 - 200);
        yBasket = 100 + rand.nextInt((int)mYOrigin*2 - 200);
    }

    public void freeze()
    {
        mBall.freeze();
    }
    public void unFreeze()
    {
        mBall.unFreeze();
    }

    public int getScore()
    {
        return score;
    }
}
