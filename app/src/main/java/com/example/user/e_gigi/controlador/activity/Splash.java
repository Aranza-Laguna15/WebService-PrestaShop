package com.example.user.e_gigi.controlador.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.user.e_gigi.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by User on 02/02/2017.
 */

public class Splash extends Activity {
ImageView imageView;
    private static final long Splash_delay = 3000;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        imageView=(ImageView)this.findViewById(R.id.imageView);

        TimerTask task =  new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        Splash.this, MainActivity.class);
                startActivity(mainIntent);

                finish();
            }
        };
        Timer timer= new Timer();
        timer.schedule(task, Splash_delay);

    }

}
