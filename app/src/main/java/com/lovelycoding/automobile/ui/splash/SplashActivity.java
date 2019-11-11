package com.lovelycoding.automobile.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lovelycoding.automobile.MainActivity;
import com.lovelycoding.automobile.R;

public class SplashActivity extends AppCompatActivity {
public static final int SPLASH_DISPLAY_LENGTH=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();

            }
        },SPLASH_DISPLAY_LENGTH);

    }
}
