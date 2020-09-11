package com.osmanyasirinan.sunitohumlama.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.osmanyasirinan.sunitohumlama.Database;
import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.Utils;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView nav;
    FrameLayout fragmentContainer;
    String[] params;
    long baslangic, bitis;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Database db = new Database(this);

        prefs = getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = prefs.edit();

        params = getIntent().getStringArrayExtra("params");
        baslangic = getIntent().getLongExtra("baslangic", -1);
        bitis = getIntent().getLongExtra("bitis", -1);

        nav = findViewById(R.id.bottomnav);
        fragmentContainer = findViewById(R.id.fragmentcontainer);

        nav.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomeFragment(MainActivity.this);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, selectedFragment).commit();
                    break;

                case R.id.nav_tohums:
                    selectedFragment = new StocksFragment(MainActivity.this);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, selectedFragment).commit();
                    break;

                case R.id.nav_filter:
                    Fragment f;

                    if (params != null) {
                        if (baslangic != -1){
                            if (bitis != -1){
                                f = new FilteredFragment(MainActivity.this, params, baslangic, bitis);
                            }else {
                                f = new FilteredFragment(MainActivity.this, params, baslangic);
                            }
                        }else {
                            f = new FilteredFragment(MainActivity.this, params);
                        }
                    }else {
                        f = new FilteredFragment(MainActivity.this);
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, f).commit();
                    break;

                case R.id.nav_stats:
                    selectedFragment = new StatsFragment(MainActivity.this);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, selectedFragment).commit();
                    break;

                case R.id.nav_settings:
                    selectedFragment = new SettingsFragment(MainActivity.this);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, selectedFragment).commit();
                    break;
            }
            return true;
        });

        nav.setOnNavigationItemReselectedListener(item -> {
            Fragment selectedFragment;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomeFragment(MainActivity.this);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, selectedFragment).commit();
                    break;

                case R.id.nav_tohums:
                    selectedFragment = new StocksFragment(MainActivity.this);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, selectedFragment).commit();
                    break;

                case R.id.nav_filter:
                    Intent i = new Intent(MainActivity.this, FilterActivity.class);
                    startActivity(i);
                    break;

                case R.id.nav_stats:
                    selectedFragment = new StatsFragment(MainActivity.this);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, selectedFragment).commit();
                    break;

                case R.id.nav_settings:
                    selectedFragment = new SettingsFragment(MainActivity.this);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, selectedFragment).commit();
                    break;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, new HomeFragment(this)).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (params != null) {
            Fragment f;
            if (baslangic != -1){
                if (bitis != -1){
                    f = new FilteredFragment(MainActivity.this, params, baslangic, bitis);
                }else {
                    f = new FilteredFragment(MainActivity.this, params, baslangic);
                }
            }else {
                f = new FilteredFragment(MainActivity.this, params);
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, f).commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.cleanPrefs(getApplicationContext());
    }

}