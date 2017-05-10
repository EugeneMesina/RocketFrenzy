package androidsupersquad.rocketfrenzy.MiniGame.AccGame;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import androidsupersquad.rocketfrenzy.R;

/**
 * Jimmy Chao
 * 012677182
 */
public class AccGame extends AppCompatActivity  {
    private static final String TAG="com.example.accelerometer.AccGame";
    private PowerManager.WakeLock mWakeLock;
    private SimulationView simulationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accgame);
        //set wake lock
        PowerManager mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, TAG);
        //register/unregister sensor listener
        simulationView=(SimulationView) findViewById(R.id.view);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //get wakelock
        mWakeLock.acquire();
        //set listener
        simulationView.startSimulation();

    }
    @Override
    protected void onPause()
    {
        super.onPause();
        //release wakelock
        mWakeLock.release();
        //unregister listener
        simulationView.stopSimulation();
    }
}
