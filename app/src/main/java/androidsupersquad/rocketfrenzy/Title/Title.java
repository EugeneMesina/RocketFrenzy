package androidsupersquad.rocketfrenzy.Title;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import androidsupersquad.rocketfrenzy.MainActivity;
import androidsupersquad.rocketfrenzy.R;

/**
 * Created by Daniel on 5/11/2017.
 */

public class Title extends AppCompatActivity {

    private Button start;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);
        start = (Button) findViewById(R.id.startButton);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1000);
    }

    public void startGame(View view) {
        Intent intent = new Intent(Title.this, MainActivity.class);
        startActivity(intent);
    }

    public void animateCloud() {

    }

}
