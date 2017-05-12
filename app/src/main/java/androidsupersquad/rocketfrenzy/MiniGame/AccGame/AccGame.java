package androidsupersquad.rocketfrenzy.MiniGame.AccGame;

import android.content.ContentValues;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidsupersquad.rocketfrenzy.DataBase.RocketContentProvider;
import androidsupersquad.rocketfrenzy.DataBase.RocketDB;
import androidsupersquad.rocketfrenzy.R;

/**
 * Jimmy Chao
 * 012677182
 */
public class AccGame extends AppCompatActivity  {
    private static final String TAG="androidsupersquad.rocketfrenzy.MiniGame.AccGame.AccGame";
    private PowerManager.WakeLock mWakeLock;
    private SimulationView simulationView;
    CountDownTimer timers;
    private TextView counter,won;
    MediaPlayer music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.accgame);
        music= MediaPlayer.create(this, R.raw.agm);
        //set music to loop
        music.setLooping(true);
        //start service
        music.start();
        //set wake lock
        PowerManager mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, TAG);
        //register/unregister sensor listener
        simulationView=(SimulationView) findViewById(R.id.view);
        simulationView.freeze();
        counter = (TextView) findViewById(R.id.AsteroidCounter);
        final ImageButton close = (ImageButton) findViewById(R.id.AccGameCloseButton);
        close.setVisibility(View.INVISIBLE);
        final TextView gameOver = (TextView) findViewById(R.id.accgameover);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close.setVisibility(View.VISIBLE);
                finish();
            }
        });
        gameOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                won = (TextView)findViewById(R.id.winAccgame);
                won.setVisibility(View.INVISIBLE);
                gameOver.setVisibility(View.INVISIBLE);
                simulationView.unFreeze();
                timers = new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        Long timeleft = (millisUntilFinished / 1000);
                        counter.setText(timeleft + "");
                    }

                    public void onFinish() {
                        counter.setText(0 + "");
                        gameOver.setText("GAME OVER");
                        gameOver.setClickable(false);
                        simulationView.freeze();
                        gameOver.setVisibility(View.VISIBLE);
                        int score = simulationView.getScore();
                        updatePlayerCoinAmount(getPlayerName(), score * 100, false);
                        won.setText("You won "+score*100+ " coins!");
                        won.setVisibility(View.VISIBLE);
                        close.setVisibility(View.VISIBLE);
                    }
                }.start();
            }
        });
    }

    /**
     * Runs when activity resumes
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        //get wakelock
        music.start();
        mWakeLock.acquire();
        //set listener
        simulationView.startSimulation();

    }

    /**
     * Runs when activity is paused
     */
    @Override
    protected void onPause()
    {
        super.onPause();
        music.stop();
        //release wakelock
        mWakeLock.release();
        //unregister listener
        simulationView.stopSimulation();
    }

    /**
     * Gets the current player's name
     *
     * @return the current player's name
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
     * Updates a player's coin count and stores it into the database
     *
     * @param playerName specified player
     * @param coinAmount amount to change
     * @param set if true, will set the amount to coin amount, if false, will add amount to coin amount
     * @return result of update
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
     * Gets the player's coin amount
     *
     * @param playerName specified player
     * @return the player's coin amount
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
