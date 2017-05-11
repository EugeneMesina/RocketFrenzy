package androidsupersquad.rocketfrenzy;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import androidsupersquad.rocketfrenzy.DataBase.RocketContentProvider;
import androidsupersquad.rocketfrenzy.DataBase.RocketDB;
import androidsupersquad.rocketfrenzy.tyrantgit.explosionfield.ExplosionField;

public class RocketLaunch extends AppCompatActivity {
    private ImageView img , img2;
    private TextView tx;
    private Context context;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket);
        tx = (TextView)findViewById(R.id.coinsamount);
        img2 =(ImageView)findViewById(R.id.coins);
        img = (ImageView)findViewById(R.id.rocket);
        tx.setVisibility(View.GONE);
        img2.setVisibility(View.GONE);
        Resources res = getResources();
        img.setImageResource(R.drawable.rocketdraft);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        random = new Random();

        TranslateAnimation animation = new TranslateAnimation(width+1000,0,height+1000,0);
        animation.setDuration(2000);
        context = this.context;
        final Activity activity =this;

        img.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              //  img.setVisibility(View.GONE);
                ExplosionField explosionField = ExplosionField.attach2Window(activity);
                explosionField.explode(findViewById(R.id.rocket));


            }
        }, 2050);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                img2.setVisibility(View.VISIBLE);
                tx.setVisibility(View.VISIBLE);
                startCountAnimation();

                img2.setImageResource(R.drawable.coin);
                RotateAnimation r;
                r = new RotateAnimation(0.0f, -10.0f * 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                r.setDuration(2000);
                r.setRepeatCount(0);
                img2.startAnimation(r);


            }
        }, 3000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            Intent x = new Intent(RocketLaunch.this, MainActivity.class);
            startActivity(x);
            finish();

            }
        }, 5000);




        }
    private void startCountAnimation() {
        int max = random.nextInt(100)+1;
        Integer newAmount=getPlayerCoinAmount(getPlayerName())+max;
        updatePlayerCoinAmount(getPlayerName(),newAmount,true);
        ValueAnimator animator = ValueAnimator.ofInt(0, max);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                tx.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
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

    }

