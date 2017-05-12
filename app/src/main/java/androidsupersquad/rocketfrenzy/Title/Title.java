package androidsupersquad.rocketfrenzy.Title;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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

    private Button start;
    private RelativeLayout titleLayout;
    private ImageView titleBg, titleRocket, titleCloud, titleName, startImage;
    private DisplayMetrics displayMetrics;
    private int screenWidth, screenHeight;
    private MediaPlayer music;

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
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animateStartButton2();
            }
        }, 1800);*/

    }

    public void startGame(View view) {
        Intent intent = new Intent(Title.this, MainActivity.class);
        startActivity(intent);
    }

    public void animateBackground() {
        TranslateAnimation moveBackground = new TranslateAnimation(0, 0, screenHeight, 0);
        moveBackground.setDuration(1000);
        titleBg.startAnimation(moveBackground);
        titleBg.setVisibility(View.VISIBLE);
    }

    public void animateCloud() {
        TranslateAnimation moveCloud = new TranslateAnimation(-screenWidth, 0, 0, 0);
        moveCloud.setDuration(500);
        titleCloud.startAnimation(moveCloud);
        titleCloud.setVisibility(View.VISIBLE);
    }

    public void animateRocket() {
        TranslateAnimation moveRocket = new TranslateAnimation(-screenWidth, 0, screenHeight, 0);
        moveRocket.setDuration(500);
        titleRocket.startAnimation(moveRocket);
        titleRocket.setVisibility(View.VISIBLE);
    }

    public void preAnimateName() {
        titleName.setVisibility(View.INVISIBLE);
        TranslateAnimation moveName = new TranslateAnimation(screenWidth, 0, 0, 0);
        moveName.setDuration(400);
        titleName.startAnimation(moveName);
        titleName.setVisibility(View.VISIBLE);
    }

    public void animateName() {
        titleName.setVisibility(View.INVISIBLE);
        ExpandDong expandName = new ExpandDong(titleName, 0, screenWidth, 0, screenHeight);
        expandName.setDuration(750);
        titleName.startAnimation(expandName);
        titleName.setVisibility(View.VISIBLE);
    }

    public void animateStartButton() {
        AlphaAnimation fadeStart = new AlphaAnimation(0f, 1f);
        fadeStart.setDuration(100);
        fadeStart.setRepeatCount(Animation.INFINITE);
        startImage.startAnimation(fadeStart);
    }

    public void animateStartButton2() {
        AlphaAnimation fadeStart = new AlphaAnimation(1f, 0f);
        fadeStart.setDuration(100);
        fadeStart.setRepeatCount(Animation.INFINITE);
        startImage.startAnimation(fadeStart);
    }

    

    private class ExpandDong extends Animation {

        final int targetHeight, targetWidth;
        View view;
        int startHeight, startWidth;

        public ExpandDong(View view, int startWidth, int targetWidth, int startHeight, int targetHeight) {
            this.view = view;
            this.startHeight = startHeight;
            this.targetHeight = targetHeight;
            this.startWidth = startWidth;
            this.targetWidth = targetWidth;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int newHeight = (int) (startHeight + (targetHeight - startHeight) * interpolatedTime);
            int newWidth = (int) (startWidth + (targetWidth - startWidth) * interpolatedTime);
            view.getLayoutParams().height = newHeight;
            view.getLayoutParams().width = newWidth;
            view.requestLayout();
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
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
