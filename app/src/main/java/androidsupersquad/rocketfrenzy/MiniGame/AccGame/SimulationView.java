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
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidsupersquad.rocketfrenzy.R;

/**
 * Created by Jimmy Chao on 3/15/2017.
 * 012677182
 */

public class SimulationView extends View implements SensorEventListener {
    //Setting variables and bitmaps
    private SensorManager sensorManager;
    private Bitmap mField, mBasket, mBitmap;
    private Particle mBall;
    private Display mDisplay;
    private static final int BALL_SIZE=64;
    private static final int BASKET_SIZE=120;
    private float mXOrigin, mYOrigin , mHorizontalBound, mVerticalBound, mSensorX, mSensorY,mSensorZ;
    private long mSensorTimeStamp;
    int score, sleep;
    boolean scorekeeper;

    /**
     * The instantiation for the custom view
     * @param context the mainActivity ContextView
     * @param attributeSet
     */
    public SimulationView(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
        //set sensors
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        //set accelerometer event listener
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_GAME);
       //set new particle class to move ball and handle boundries
        mBall=new Particle();
        //set the drawable for the ball
        Bitmap ball= BitmapFactory.decodeResource(getResources(),R.drawable.ball);
        mBitmap = Bitmap.createScaledBitmap(ball,BALL_SIZE,BALL_SIZE,true);
        //set the drawable for the basket
        Bitmap basket= BitmapFactory.decodeResource(getResources(),R.drawable.basket);
        mBasket = Bitmap.createScaledBitmap(basket,BASKET_SIZE,BASKET_SIZE,true);
        //set the field
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDither = true;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        mField = BitmapFactory.decodeResource(getResources(),R.drawable.field,opts);

    // initial the current score
        score=0;
    //Get phone's screen width and height to set origin and bound
        WindowManager mWindowManager =(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mDisplay= mWindowManager.getDefaultDisplay();

        mXOrigin = mDisplay.getWidth()/2;
        mYOrigin = mDisplay.getHeight()/2;
        mHorizontalBound = mDisplay.getWidth()/2;
        mVerticalBound = mDisplay.getHeight()/2;
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
        canvas.drawBitmap(mField,0,0,null);
        canvas.drawBitmap(mBasket,(mXOrigin-BASKET_SIZE/2)-20,(mYOrigin-BASKET_SIZE/2)-755,null);
//update the ball position
        mBall.updatePosition(mSensorX,mSensorY,mSensorZ,mSensorTimeStamp);
        mBall.resolveCollisionWithBounds(mHorizontalBound,mVerticalBound);

        canvas.drawBitmap(mBitmap,(mXOrigin-BALL_SIZE/2)+mBall.mPosX, (mYOrigin-BALL_SIZE/2)-mBall.mPosY,null);
        //checkvalues to see if the ball image touches the basket image
        Integer xPos=Math.round((mXOrigin-BALL_SIZE/2)+mBall.mPosX);
        Integer YPos=Math.round((mYOrigin-BALL_SIZE/2)-mBall.mPosY);
        Integer Xbasket= Math.round(((mXOrigin-BASKET_SIZE/2)-20));
        Integer Ybasket= Math.round(((mYOrigin-BASKET_SIZE/2)-755));
//add score if the ball hits the basket image

        if((xPos<=Xbasket+20&&xPos>=Xbasket-20)&&(Ybasket+20>=YPos&&Ybasket-20<=YPos)&&scorekeeper)
        {
            System.out.println("HERE");
            score++;
            scorekeeper=false;
        }
        //sleep so the player doesn't just keep the ball in one place and indefinitely get an infinite score
        sleep++;
        if(sleep==100)
        {
            sleep=0;
            scorekeeper=true;

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
        canvas.drawText("Score "+score, mXOrigin, mYOrigin,titlePaint);


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

        mHorizontalBound = (w - BALL_SIZE) * 0.5f;
        mVerticalBound = (h - BALL_SIZE) * 0.5f;
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

        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            mSensorTimeStamp=System.nanoTime();
            //checks to see if it hasn't rotated
            if (mDisplay.getRotation()==Surface.ROTATION_0) {
                mSensorX = event.values[0];
                mSensorY = event.values[1];
                mSensorZ = event.values[2];
            }
            //checks to see if it has rotated
            else if(mDisplay.getRotation()==Surface.ROTATION_90)
            {
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
}
