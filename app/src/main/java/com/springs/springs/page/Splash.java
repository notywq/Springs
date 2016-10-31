package com.springs.springs.page;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.springs.springs.R;

public class Splash extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //getSupportActionBar().hide();
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                Intent i = new Intent(Splash.this, MainActivity.class);
                Splash.this.startActivity(i);
                Splash.this.finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
