package androidsupersquad.rocketfrenzy.Title;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidsupersquad.rocketfrenzy.MainActivity;
import androidsupersquad.rocketfrenzy.R;

/**
 * Created by Daniel on 5/11/2017.
 */

public class Title extends AppCompatActivity {

    /** Full screen button that allows user to click anywhere to move to next screen */
    private Button start;
    /** Layout that contains the background */
    private RelativeLayout titleLayout;
    /** The elements of the title screen ui */
    private ImageView titleBg, titleRocket, titleCloud, titleName, startImage;
    /** Used for getting screen */
    private DisplayMetrics displayMetrics;
    /** Screen Width and Screen Height */
    private int screenWidth, screenHeight;
    private MediaPlayer music;

    /**
     * Creates Title Screen
     *
     * @param savedInstanceState Bundle of data
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        music= MediaPlayer.create(this, R.raw.bg);
        //set music to loop
        music.setLooping(true);
        //start service
        music.start();
        View decorView = getWindow().getDecorView();
        int fullOption = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(fullOption);
        setContentView(R.layout.activity_title_screen);
        titleLayout = (RelativeLayout) findViewById(R.id.titleLayout);
        titleBg = (ImageView) findViewById(R.id.titleBg);
        titleRocket = (ImageView) findViewById(R.id.titleRocket);
        titleCloud = (ImageView) findViewById(R.id.titleCloud);
        titleName = (ImageView) findViewById(R.id.titleName);
        startImage = (ImageView) findViewById(R.id.startImage);
        start = (Button) findViewById(R.id.startButton);
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animateBackground();
            }
        }, 500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animateCloud();
            }
        }, 1300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animateRocket();
            }
        }, 1300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                preAnimateName();
            }
        }, 1500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animateStartButton();
            }
        }, 1700);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startButton();
            }
        }, 1800);

    }

    /**
     * Intent to go to the MainActivity
     *
     * @param view The current view
     */
    public void startGame(View view) {
        Intent intent = new Intent(Title.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Animates (Translates) Background into position on screen
     */
    public void animateBackground() {
        TranslateAnimation moveBackground = new TranslateAnimation(0, 0, screenHeight, 0);
        moveBackground.setDuration(1000);
        titleBg.startAnimation(moveBackground);
        titleBg.setVisibility(View.VISIBLE);
    }

    /**
     * Animates (Translates) cloud into position on screen
     */
    public void animateCloud() {
        TranslateAnimation moveCloud = new TranslateAnimation(-screenWidth, 0, 0, 0);
        moveCloud.setDuration(500);
        titleCloud.startAnimation(moveCloud);
        titleCloud.setVisibility(View.VISIBLE);
    }

    /**
     * Animates (Translates) rocket into position on screen
     */
    public void animateRocket() {
        TranslateAnimation moveRocket = new TranslateAnimation(-screenWidth, 0, screenHeight, 0);
        moveRocket.setDuration(500);
        titleRocket.startAnimation(moveRocket);
        titleRocket.setVisibility(View.VISIBLE);
    }

    /**
     * Animates (Translates) Logo into position on screen
     */
    public void preAnimateName() {
        titleName.setVisibility(View.INVISIBLE);
        TranslateAnimation moveName = new TranslateAnimation(screenWidth, 0, 0, 0);
        moveName.setDuration(400);
        titleName.startAnimation(moveName);
        titleName.setVisibility(View.VISIBLE);
    }

    /**
     * Unused animation for Start Button
     */
    public void animateStartButton() {
        AlphaAnimation fadeStart = new AlphaAnimation(0f, 1f);
        fadeStart.setInterpolator(new DecelerateInterpolator());
        fadeStart.setDuration(1000);

        AlphaAnimation fadeEnd = new AlphaAnimation(1f, 0f);
        fadeEnd.setInterpolator(new AccelerateInterpolator());
        fadeEnd.setStartOffset(1200);
        fadeStart.setDuration(1000);

        AnimationSet fullFade = new AnimationSet(false);
        fullFade.addAnimation(fadeStart);
        fullFade.addAnimation(fadeEnd);
        fullFade.setRepeatCount(Animation.INFINITE);
        startImage.startAnimation(fullFade);
    }

    /**
     * Animates fade in for Start button
     */
    public void startButton() {
        AlphaAnimation fadeStart = new AlphaAnimation(0f, 1f);
        fadeStart.setInterpolator(new DecelerateInterpolator());
        fadeStart.setDuration(800);
        startImage.startAnimation(fadeStart);
        startImage.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        music.stop();
    }

    @Override
    protected void onResume()
    {
       super.onResume();
        music.start();
    }

}
