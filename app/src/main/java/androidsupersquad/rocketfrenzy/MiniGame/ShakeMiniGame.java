package androidsupersquad.rocketfrenzy.MiniGame;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidsupersquad.rocketfrenzy.Data.RocketData;
import androidsupersquad.rocketfrenzy.DataBase.ByteArrayConverter;
import androidsupersquad.rocketfrenzy.DataBase.RocketContentProvider;
import androidsupersquad.rocketfrenzy.DataBase.RocketDB;
import androidsupersquad.rocketfrenzy.Fragments.Models.Rocket;
import androidsupersquad.rocketfrenzy.R;

/**
 * Shake Mini Game that is activated to reward player rocket/coins
 *
 * Created by: Jimmy Chao(Lazer)
 */
public class ShakeMiniGame extends AppCompatActivity implements SensorEventListener {
    /** Used to handle sensors */
    private SensorManager sensorManager;
    /** The relative layout in this fragment*/
    private RelativeLayout view;
    /**
     * TextViews used for displaying:
     *  1) Shake Game name
     *  2) The CountDownTimer
     *  3) Displays either 'You Won' or 'Game Over'
     */
    private TextView Shake,Counter, won;
    /** Button used to close the game */
    private ImageButton close;
    /** The amount of progress the player made shaking */
    private ProgressBar PGShake;
    /**
     * Booleans used for checking:
     *  1) Used to check whether the sensor has been registered to a listener
     *  2) Used to check whether 'Start' was clicked
     *  3) Used to check whether the player won or not
     *  4) Used to check whether the game is running or not
     */
    private boolean Register,start,win,isRunning;
    /** Countdown timer */
    private CountDownTimer timers;
    /** Handles music */
    private MediaPlayer music;

    /**
     * onCreate method
     *
     * @param savedInstanceState Data saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting game variables to layout file
        setContentView(R.layout.activity_shake_mini_game);
        music= MediaPlayer.create(this, R.raw.agm);
        //set music to loop
        music.setLooping(true);
        //start service
        music.start();
        Counter = (TextView) findViewById(R.id.SGCounter);
        close = (ImageButton) findViewById(R.id.ShakeCloseButton);
        close.setVisibility(View.INVISIBLE);
        won = (TextView)findViewById(R.id.winrocket);
        won.setVisibility(View.INVISIBLE);
        view = (RelativeLayout) findViewById(R.id.SGView);
        PGShake =(ProgressBar) findViewById(R.id.progressBar);
        Shake =(TextView) findViewById(R.id.ShakeTV);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"fonts/TwoLines.ttf");
        Shake.setTypeface(myCustomFont);
        isRunning=true;
        start=true;
        Register=false;
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //finishes activity and leaves
            }
        });
        //setting on click listener on the view
        view.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                if(start) {
                    sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                    sensorManager.registerListener(ShakeMiniGame.this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
                    Counter.setText("GO");
                    win=false;
                    timers = new CountDownTimer(10000, 1000) {
                    //count down timer
                        public void onTick(long millisUntilFinished) {
                            Long timeleft = (millisUntilFinished / 1000);
                            Counter.setGravity(Gravity.CENTER);
                            Counter.setText(timeleft.toString());
                            //ticks changes the time on screen
                        }

                        public void onFinish() {
                            isRunning = false;
                            if(win) {
                                //finishes win
                                Counter.setText("You Won");
                                this.cancel();
                                if(getPlayerName()!=null) {
                                    addRocketToPlayer(getPlayerName(), RocketData.giveRocket());
                                    updatePlayerCoinAmount(getPlayerName(), 200, false);
                                    won.setText("You won 1 Rocket & 200 Coins!");
                                    //win 1 random rocket and 200 coins
                                    won.setVisibility(View.VISIBLE);
                                }
                            } else {
                                //lose
                                Counter.setText("Game Over");
                                this.cancel();
                            }
                            close.setVisibility(View.VISIBLE);
                        }
                    }.start();
                    Register = true;
                    start=false;
                }
            }
        });
    }

    /**
     * Ran when app is destroyed
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Ensure no memory leak occurs if we register the location listener but the call hasn't
        // been made yet.


    }

    /**
     * Retrieve sensor data
     *
     * @param event The data held by the sensor
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(isRunning) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                int x = (int) Math.abs(event.values[0]);
                int y = (int) Math.abs(event.values[1]);
                int z = (int) Math.abs(event.values[2]);
                int Progress = PGShake.getProgress() + x + y + z;
                PGShake.setProgress(Progress);

                if (Progress >= PGShake.getMax()) {
                    sensorManager.unregisterListener(this);
                    win = true;
                    timers.onFinish();
                }
            }
        }
    }
    /**
     * accuracy change not needed to be read
     */
    public void onAccuracyChanged(Sensor sensor,int accuracy){}
    @Override

    /**
     * Ran when activity is resumed
     */
    protected void onResume()
    {
        super.onResume();
        music.start();
        if(Register){
            sensorManager.registerListener(this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                        SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    /**
     * Ran when activity is paused
     */
    @Override
    protected void onPause()
    {
        super.onPause();
        music.stop();
        if(Register) {
            sensorManager.unregisterListener(this);
        }
    }

    /******** DATABASE METHODS ********/

    /**
     * Gets and returns player name from databse
     */
    private String getPlayerName()
    {
        Cursor cursor = getContentResolver().query(RocketContentProvider.CONTENT_URI, null, null, null, null);
        int username = cursor.getColumnIndex(RocketDB.USER_NAME_COLUMN);
        cursor.moveToFirst();
        //still kind of testing//
        String name = cursor.getString(username);

        Log.d("PLAYER_NAME_INFO", "Username: " + name);
        return name;
    }

    /**
     * Gets the rockets owned by the player
     *
     * @param playerName The name of the player
     * @return An ArrayList of rockets owned by the player
     */
    private ArrayList<Rocket> getPlayerRockets(String playerName)
    {
        String where = RocketDB.USER_NAME_COLUMN + "= ?";
        String whereArgs[] = {playerName};
        String[] resultColumns = {RocketDB.ROCKETS_OWNED_COLUMN};
        Cursor cursor = getContentResolver().query(RocketContentProvider.CONTENT_URI, resultColumns, where, whereArgs, null);
        int rockets = cursor.getColumnIndex(RocketDB.ROCKETS_OWNED_COLUMN);
        cursor.moveToFirst();
        try {
            ArrayList<Rocket> rocketArray = (ArrayList<Rocket>) ByteArrayConverter.ByteArrayToObject(cursor.getBlob(rockets));
            String rocketString = "\t";

            for (Rocket r : rocketArray) {
                rocketString += (r + "\n\t");
            }
            Log.d("ROCKET_INFO", rocketString);
            return rocketArray;
        } catch (Exception e) {
            Log.d("ROCKET_INFO", "Username: " + playerName + "\nRocketLaunch names: null");
            return null;
        }
    }

    /**
     * Adds a rocket to the player inventory
     *
     * @param playerName The player name to add rocket to
     * @param rocket The type of rocket
     * @return The number of rows updated
     */
    private int addRocketToPlayer(String playerName, Rocket rocket)
    {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        ArrayList<Rocket> currentRockets = getPlayerRockets(playerName);
        if(currentRockets == null) {
            currentRockets = new ArrayList<Rocket>();
        }
        currentRockets.add(rocket);
        byte[]bytes = ByteArrayConverter.ObjectToByteArray(currentRockets);
        ContentValues values = new ContentValues();
        values.put(RocketDB.ROCKETS_OWNED_COLUMN, bytes);
        return getContentResolver().update(RocketContentProvider.CONTENT_URI, values, whereClause, whereArgs);
    }

    /**
     * Used to update the amount of coins a playe rhas
     *
     * @param playerName The player's name
     * @param coinAmount The amount of coins the player owns
     * @param set Whether the player has any coins or not
     * @return The new amount of coins the player has
     */
    private int updatePlayerCoinAmount(String playerName, int coinAmount, boolean set)
    {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        int newCoinAmount = 0;
        ContentValues newValues = new ContentValues();
        if(set) {
            newCoinAmount = coinAmount;
        } else {
            int currentCoins = getPlayerCoinAmount(playerName);
            newCoinAmount = currentCoins + coinAmount;
        }
        newValues.put(RocketDB.COIN_AMOUNT_COLUMN, newCoinAmount);
        return getContentResolver().update(RocketContentProvider.CONTENT_URI, newValues, whereClause, whereArgs);
    }

    /**
     * Gets the player's amount of coins
     * @param playerName The player's name
     * @return The amount of coins the player has
     */
    private int getPlayerCoinAmount(String playerName)
    {
        String where = RocketDB.USER_NAME_COLUMN + "= ?";
        String whereArgs[] = {playerName};
        String[] resultColumns = {RocketDB.COIN_AMOUNT_COLUMN};
        Cursor cursor = getContentResolver().query(RocketContentProvider.CONTENT_URI, resultColumns, where, whereArgs, null);
        int coin = cursor.getColumnIndex(RocketDB.COIN_AMOUNT_COLUMN);
        cursor.moveToFirst();
        int coinAmount = cursor.getInt(coin);
        Log.d("COIN_INFO", "Username: " + playerName + "\nCoin amount: " + coinAmount);
        return coinAmount;
    }
}