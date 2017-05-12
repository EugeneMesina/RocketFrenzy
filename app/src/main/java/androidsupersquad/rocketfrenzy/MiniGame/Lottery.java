package androidsupersquad.rocketfrenzy.MiniGame;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import androidsupersquad.rocketfrenzy.Data.RocketData;
import androidsupersquad.rocketfrenzy.DataBase.ByteArrayConverter;
import androidsupersquad.rocketfrenzy.DataBase.RocketContentProvider;
import androidsupersquad.rocketfrenzy.DataBase.RocketDB;
import androidsupersquad.rocketfrenzy.Fragments.Models.Rocket;
import androidsupersquad.rocketfrenzy.R;
//Lottery Game Class - Eugene MEsina
public class Lottery extends AppCompatActivity {
    //Initializing Game Variables
    ImageButton rocket, close;
    ImageView slot1,slot2,slot3;
    TextView won;
    Random random;
    int img1,img2,img3;
/*
 onCreate Method Life Cycle Method
 */
    MediaPlayer music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        music= MediaPlayer.create(this, R.raw.agm);
        //set music to loop
        music.setLooping(true);
        //start service
        music.start();
        //setting variables to match layout file
        random = new Random();
        rocket = (ImageButton) findViewById(R.id.RocketButton);
        close = (ImageButton) findViewById(R.id.LotteryCloseButton);
        close.setVisibility(View.INVISIBLE);
        slot1 = (ImageView)findViewById(R.id.imageView);
        slot2 = (ImageView)findViewById(R.id.imageView2);
        slot3 = (ImageView)findViewById(R.id.imageView3);
        won = (TextView)findViewById(R.id.win);
        won.setVisibility(View.INVISIBLE);
        rocket.setOnClickListener(new View.OnClickListener(){
            //anonymous onClick Method
            @Override
            public void onClick(View view){
                //does not allow user to reClick Slot Machine
                rocket.setEnabled(false);
                slot1.setBackgroundResource(R.drawable.animate);
                //set to animate xml
                //gets xml animation
                final AnimationDrawable slot1anim = (AnimationDrawable) slot1.getBackground();

                slot2.setBackgroundResource(R.drawable.animate);
                final AnimationDrawable slot2anim = (AnimationDrawable) slot2.getBackground();
                slot2anim.start();
                //set to animate xml
                //gets xml animation
                slot3.setBackgroundResource(R.drawable.animate);
                final AnimationDrawable slot3anim = (AnimationDrawable) slot3.getBackground();
                slot3anim.start();
                //set to animate xml
                //gets xml animation
                //delayed starts
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        slot1anim.start();
                    }
                },0);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        slot2anim.start();
                    }
                },50);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        slot3anim.start();
                    }
                },100);
                //handler for waiting for the spinning slot machine button
                Handler handler = new Handler();
                RotateAnimation r;
                //rotation animator for slot button
                r = new RotateAnimation(0.0f, -10.0f * 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                r.setDuration(2000);
                //spins for two seconds
                r.setRepeatCount(0);
                rocket.startAnimation(r);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //stops animation
                        slot1anim.stop();
                        slot2anim.stop();
                        slot3anim.stop();
                        //set images
                        setImages();
                        //get scores and gives rewards
                        getScore();
                    }
                },2000);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //goes out of activitiy
            }
        });
    }
    /*
    set images and numbers based on randomizer
     */
    public void setImages(){
        img1 = random.nextInt(3) +1;
        img2 = random.nextInt(3) +1;
        img3 = random.nextInt(3) +1;
        switch (img1){
            case 1:
                slot1.setBackgroundResource(R.drawable.slotone);
                break;
            case 2:
                slot1.setBackgroundResource(R.drawable.slottwo);
                break;
            case 3:
                slot1.setBackgroundResource(R.drawable.slotthree);
                break;


        }
        switch (img2){
            case 1:
                slot2.setBackgroundResource(R.drawable.slotone);
                break;
            case 2:
                slot2.setBackgroundResource(R.drawable.slottwo);
                break;
            case 3:
                slot2.setBackgroundResource(R.drawable.slotthree);
                break;



        }
        switch (img3){
            case 1:
                slot3.setBackgroundResource(R.drawable.slotone);
                break;
            case 2:
                slot3.setBackgroundResource(R.drawable.slottwo);
                break;
            case 3:
                slot3.setBackgroundResource(R.drawable.slotthree);
                break;



        }


    }
    /*
    get score from randomizer
    and determines winning conditions
     */
    public void getScore(){
        if(img1 == img2 && img2 == img3){
            //some number
            if(getPlayerName()!=null) {
                addRocketToPlayer(getPlayerName(), RocketData.giveRocket());
                addRocketToPlayer(getPlayerName(), RocketData.giveRocket());
                updatePlayerCoinAmount(getPlayerName(), 100, false);
                //adds two random rokcets and 100 coins
                won.setText("You Won 2 Rockets & 100 Gold ");
                won.setVisibility(View.VISIBLE);
            }
        }
        else if(img1 == img2 || img2 == img3 || img1==img3){
            addRocketToPlayer(getPlayerName(), RocketData.giveRocket());
            updatePlayerCoinAmount(getPlayerName(), 50, false);
            //gives 1 random rocket and 50 coins
            won.setText("You Won 1 Rocket & 50 Gold ");
            won.setVisibility(View.VISIBLE);
            //some number
        }
        else {
            won.setText("You Lose");
            won.setVisibility(View.VISIBLE);
        }
        close.setVisibility(View.VISIBLE);
    }
    //database methods
    /*
    gets and returns player name from databse
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

    /*
    gets player rockets and returns array list of rockets
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
        } catch (Exception e)
        {
            Log.d("ROCKET_INFO", "Username: " + playerName + "\nRocketLaunch names: null");
            return null;
        }
    }
    /*
    adds rockets to player
    returns int
     */

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
    /*
    gives coins to player
    returns int
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
    /*
    get coin amount returns int
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
    /*
    pause the activity
     */
    @Override
    protected void onPause()
    {
        super.onPause();
        music.stop();
    }

    /*
    resume the activity
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        music.start();
    }
}
