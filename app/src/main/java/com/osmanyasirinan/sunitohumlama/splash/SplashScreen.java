package com.osmanyasirinan.sunitohumlama.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.main.MainActivity;

public class SplashScreen extends AppCompatActivity {

    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        startService(new Intent(getBaseContext(), DetectService.class));

        thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1000);
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        thread.start();
    }
}
