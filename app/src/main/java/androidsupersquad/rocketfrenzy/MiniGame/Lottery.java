package androidsupersquad.rocketfrenzy.MiniGame;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

import androidsupersquad.rocketfrenzy.MainActivity;
import androidsupersquad.rocketfrenzy.R;

public class Lottery extends AppCompatActivity {
    ImageButton rocket;
    ImageView slot1,slot2,slot3;
    Random random;
    int img1,img2,img3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);

        random = new Random();
        rocket = (ImageButton) findViewById(R.id.RocketButton);
        slot1 = (ImageView)findViewById(R.id.imageView);
        slot2 = (ImageView)findViewById(R.id.imageView2);
        slot3 = (ImageView)findViewById(R.id.imageView3);

        rocket.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                rocket.setEnabled(false);
                slot1.setBackgroundResource(R.drawable.animate);
                final AnimationDrawable slot1anim = (AnimationDrawable) slot1.getBackground();
                slot1anim.start();

                slot2.setBackgroundResource(R.drawable.animate);
                final AnimationDrawable slot2anim = (AnimationDrawable) slot2.getBackground();
                slot2anim.start();

                slot3.setBackgroundResource(R.drawable.animate);
                final AnimationDrawable slot3anim = (AnimationDrawable) slot3.getBackground();
                slot3anim.start();

                Handler handler = new Handler();
                RotateAnimation r;
                r = new RotateAnimation(0.0f, -10.0f * 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                r.setDuration(2000);
                r.setRepeatCount(0);
                rocket.startAnimation(r);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        slot1anim.stop();
                        slot2anim.stop();
                        slot3anim.stop();

                        setImages();

                        getScore();
                    }
                },2500);
            }
        });
    }
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
    public void getScore(){
        if(img1 == img2 && img2 == img3){
            //some number
        }
        else if(img1 == img2 || img2 == img3 || img1==img3){
            //some number
        }
        else{
            Toast.makeText(this,"Loser",Toast.LENGTH_LONG).show();
        }
        Intent x = new Intent(Lottery.this, MainActivity.class);
        startActivity(x);
    }

}
