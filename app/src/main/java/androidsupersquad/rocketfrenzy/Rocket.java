package androidsupersquad.rocketfrenzy;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import androidsupersquad.rocketfrenzy.tyrantgit.explosionfield.ExplosionField;

public class Rocket extends AppCompatActivity {
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


        }
    private void startCountAnimation() {
        int max = random.nextInt(100)+1;
        ValueAnimator animator = ValueAnimator.ofInt(0, max);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                tx.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }


    }

