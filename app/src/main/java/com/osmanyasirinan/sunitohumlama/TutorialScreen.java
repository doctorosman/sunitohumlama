package com.osmanyasirinan.sunitohumlama;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.hololo.tutorial.library.PermissionStep;
import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class TutorialScreen extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean first = prefs.getBoolean("firstStart", true);

        if (!first){
            splash();
        }else {
            addFragment(new Step.Builder().setTitle("Ana Sayfa")
                    .setContent(getString(R.string.step1))
                    .setBackgroundColor(Color.parseColor("#F6881C"))
                    .setDrawable(R.drawable.home_tutorial)
                    .setSummary("")
                    .build());

            addFragment(new Step.Builder().setTitle("Veriler")
                    .setContent(getString(R.string.step2))
                    .setBackgroundColor(Color.parseColor("#98AC04"))
                    .setDrawable(R.drawable.stats_tutorial)
                    .setSummary("")
                    .build());

            addFragment(new Step.Builder().setTitle("Yeni")
                    .setContent(getString(R.string.step3))
                    .setBackgroundColor(Color.parseColor("#1B4470"))
                    .setDrawable(R.drawable.new_tutorial)
                    .setSummary("")
                    .build());

            addFragment(new Step.Builder().setTitle("Filtreleme")
                    .setContent(getString(R.string.step4))
                    .setBackgroundColor(Color.parseColor("#D53904"))
                    .setDrawable(R.drawable.filtering_tutorial)
                    .setSummary("")
                    .build());

            addFragment(new PermissionStep.Builder().setTitle("Ayarlar")
                    .setContent(getString(R.string.step5))
                    .setBackgroundColor(Color.parseColor("#110F2A"))
                    .setDrawable(R.drawable.settings_tutorial)
                    .setSummary("")
                    .setPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
                    .build());

            setPrevText("ÖNCEKİ");
            setNextText("SONRAKİ");
            setFinishText("SON");
            setCancelText("");
            setGivePermissionText("İZİN VER");

            SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstStart", false);
            editor.apply();
        }
    }

    @Override
    public void finishTutorial(){ splash(); }

    @Override
    public void currentFragmentPosition(int position) {

    }

    private void splash(){
        Intent i = new Intent(TutorialScreen.this, SplashScreen.class);
        startActivity(i);
        finish();
    }

}
