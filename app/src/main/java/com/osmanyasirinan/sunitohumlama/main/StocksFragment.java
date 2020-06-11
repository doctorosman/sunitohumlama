package com.osmanyasirinan.sunitohumlama.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.osmanyasirinan.sunitohumlama.Database;
import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.tohum.Tohum;
import com.osmanyasirinan.sunitohumlama.tohum.YeniTohum;

import java.util.ArrayList;
import java.util.List;

public class StocksFragment extends Fragment {

    private View v;
    private Context context;
    private FloatingActionButton fab;
    private ListView lv;

    public StocksFragment(Context context){ this.context = context; }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_stocks, container, false);

        fab = v.findViewById(R.id.tohumfab);
        fab.setOnClickListener(v -> {
            Intent i = new Intent(context, YeniTohum.class);
            startActivity(i);
        });

        lv = v.findViewById(R.id.tohumlist);


        return v;
    }

    private void tohumListele() {
        Database db = new Database(context);

        // TODO
    }

}
