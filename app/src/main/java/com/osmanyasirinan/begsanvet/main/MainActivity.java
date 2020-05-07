package com.osmanyasirinan.begsanvet.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.osmanyasirinan.begsanvet.R;
import com.osmanyasirinan.begsanvet.database.YeniHayvan;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomnav = findViewById(R.id.navview);
        bottomnav.setOnNavigationItemSelectedListener(listener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, new HomeFragment(MainActivity.this)).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment;

            switch (item.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomeFragment(MainActivity.this);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, selectedFragment).commit();
                    break;
                case R.id.nav_stats:
                    selectedFragment = new StatsFragment(MainActivity.this);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, selectedFragment).commit();
                    break;

                case R.id.nav_add:
                    Intent i = new Intent(MainActivity.this, YeniHayvan.class);
                    startActivity(i);
                    break;
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemfiltered) {
            Intent i = new Intent(MainActivity.this, FilteredList.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
