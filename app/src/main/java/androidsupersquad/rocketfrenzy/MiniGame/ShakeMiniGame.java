package androidsupersquad.rocketfrenzy.MiniGame;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidsupersquad.rocketfrenzy.Data.RocketData;
import androidsupersquad.rocketfrenzy.DataBase.ByteArrayConverter;
import androidsupersquad.rocketfrenzy.DataBase.RocketContentProvider;
import androidsupersquad.rocketfrenzy.DataBase.RocketDB;
import androidsupersquad.rocketfrenzy.Fragments.Models.Rocket;
import androidsupersquad.rocketfrenzy.MainActivity;
import androidsupersquad.rocketfrenzy.R;


public class ShakeMiniGame extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    RelativeLayout view;
    TextView Shake,Counter;
    ProgressBar PGShake;
    boolean Register,start,win;
    CountDownTimer timers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_mini_game);
        Counter = (TextView) findViewById(R.id.SGCounter);
        view = (RelativeLayout) findViewById(R.id.SGView);
        PGShake =(ProgressBar) findViewById(R.id.progressBar);
        Shake =(TextView) findViewById(R.id.ShakeTV);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"fonts/TwoLines.ttf");
        Shake.setTypeface(myCustomFont);
        start=true;
       // Counter.setTypeface(myCustomFont);
        Register=false;
        view.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        if(start) {
                            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                            sensorManager.registerListener(ShakeMiniGame.this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
                            Counter.setText("GO");
                            win=false;
                            timers = new CountDownTimer(30000, 1000) {

                                public void onTick(long millisUntilFinished) {
                                    Long timeleft = (millisUntilFinished / 1000);
                                    Counter.setGravity(Gravity.CENTER);
                                    Counter.setText(timeleft.toString());
                                }

                                public void onFinish() {
                                    if(win)
                                    {
                                        Counter.setText("You Won");
                                        this.cancel();
                                        if(getPlayerName()!=null) {
                                            addRocketToPlayer(getPlayerName(), RocketData.giveRocket());
                                        }

                                        Intent x = new Intent(ShakeMiniGame.this, MainActivity.class);
                                        startActivity(x);
                                        finish();
                                    }
                                    else {
                                        Counter.setText("Game Over");
                                        this.cancel();
                                        Intent x = new Intent(ShakeMiniGame.this, MainActivity.class);
                                        startActivity(x);
                                        finish();
                                    }
                                }
                            }.start();
                            Register = true;
                            start=false;
                        }
                    }
                });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Ensure no memory leak occurs if we register the location listener but the call hasn't
        // been made yet.


    }
    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            int x= (int) Math.abs(event.values[0]);
            int y= (int) Math.abs(event.values[1]);
            int z= (int) Math.abs(event.values[2]);
            int Progress = PGShake.getProgress()+x+y+z;
            PGShake.setProgress(Progress);

            if(Progress>=PGShake.getMax())
            {
                sensorManager.unregisterListener(this);
                win=true;
                timers.onFinish();
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor,int accuracy){}
    @Override
    protected void onResume()
    {
        super.onResume();
        if(Register){
            sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);

        }
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        if(Register) {
            sensorManager.unregisterListener(this);
        }
    }
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
        } catch (Exception e)
        {
            Log.d("ROCKET_INFO", "Username: " + playerName + "\nRocketLaunch names: null");
            return null;
        }
    }


    private int addRocketToPlayer(String playerName, Rocket rocket)
    {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        ArrayList<Rocket> currentRockets = getPlayerRockets(playerName);
        if(currentRockets == null)
        {
            currentRockets = new ArrayList<Rocket>();
        }
        currentRockets.add(rocket);
        byte[]bytes = ByteArrayConverter.ObjectToByteArray(currentRockets);
        ContentValues values = new ContentValues();
        values.put(RocketDB.ROCKETS_OWNED_COLUMN, bytes);
        return getContentResolver().update(RocketContentProvider.CONTENT_URI, values, whereClause, whereArgs);
    }


}
