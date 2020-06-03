package com.osmanyasirinan.sunitohumlama.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.database.FilterActivity;
import com.osmanyasirinan.sunitohumlama.database.YeniHayvan;

public class MainActivity extends AppCompatActivity {

    SpaceNavigationView nav;
    String[] strings;
    int[] parts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav = findViewById(R.id.navview);
        nav.initWithSaveInstanceState(savedInstanceState);
        nav.addSpaceItem(new SpaceItem("", R.drawable.ic_home_black_24dp));
        nav.addSpaceItem(new SpaceItem("", R.drawable.ic_show_chart_black_24dp));
        nav.addSpaceItem(new SpaceItem("", R.drawable.ic_sort_black_24dp));
        nav.addSpaceItem(new SpaceItem("", R.drawable.ic_settings_black_24dp));

        strings = getIntent().getStringArrayExtra("strings");
        parts = getIntent().getIntArrayExtra("parts");

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (strings != null && parts != null) {
            Fragment f = new FilteredFragment(MainActivity.this, strings, parts);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, f).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, new HomeFragment(MainActivity.this)).commit();
        }
        nav.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                nav.setCentreButtonSelectable(true);
                Intent i = new Intent(MainActivity.this, YeniHayvan.class);
                startActivity(i);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Fragment selectedFragment;
                switch (itemIndex) {
                    case 0:
                        selectedFragment = new HomeFragment(MainActivity.this);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, selectedFragment).commit();
                        break;

                    case 1:
                        selectedFragment = new StatsFragment(MainActivity.this);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, selectedFragment).commit();
                        break;

                    case 2:
                        selectedFragment = new FilteredFragment(MainActivity.this, strings, parts);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, selectedFragment).commit();
                        break;

                    case 3:
                        selectedFragment = new SettingsFragment(MainActivity.this);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, selectedFragment).commit();
                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                if (itemIndex == 2){
                    Intent i = new Intent(MainActivity.this, FilterActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    public void github(View view){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/doctorosman/sunitohumlama"));
        startActivity(i);
    }
}
