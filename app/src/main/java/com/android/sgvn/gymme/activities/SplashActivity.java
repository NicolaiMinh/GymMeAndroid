package com.android.sgvn.gymme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.sgvn.gymme.R;

public class SplashActivity extends BaseLoginActivity {

    private static final long TIME_DISPLAY = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //set up permission
        checkPermission();

        showDisplay();
    }

    //display splash when start app
    private void showDisplay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, TutorialActivity.class));
                finish();
            }
        }, TIME_DISPLAY);
    }

    private void checkPermission() {

    }


}
