package com.osmanyasirinan.sunitohumlama.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.Database;
import com.osmanyasirinan.sunitohumlama.hayvan.Hayvan;
import com.osmanyasirinan.sunitohumlama.hayvan.HayvanDetay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilteredFragment extends Fragment {

    private View v;
    private TextView tv;
    private ListView lv;
    private Context context;
    private Button sort;

    private String[] params;
    private long baslangic = -1, bitis = -1;

    public FilteredFragment(Context context) {
        this.context = context;
    }

    public FilteredFragment(Context context, String[] params) {
        this.context = context;
        this.params = params;
    }

    public FilteredFragment(Context context, String[] params, long baslangic) {
        this.context = context;
        this.params = params;
        this.baslangic = baslangic;
    }

    public FilteredFragment(Context context, String[] params, long baslangic, long bitis) {
        this.context = context;
        this.params = params;
        this.baslangic = baslangic;
        this.bitis = bitis;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_filtered, container, false);

        tv = v.findViewById(R.id.kayitsayisi);
        lv = v.findViewById(R.id.filteredlistview);

        sort = v.findViewById(R.id.sortit);

        sort.setOnClickListener(v -> {
            Intent i = new Intent(context, FilterActivity.class);
            startActivity(i);
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (params != null) {
            Database db = new Database(context);
            List<String> sahipler = new ArrayList<>();
            List<Hayvan> result;

            if (baslangic != -1) {
               if (bitis != -1) {
                   result = db.hayvanFiltrele(params, new Date(baslangic), new Date(bitis));

                   for (Hayvan h : result) {
                       sahipler.add(h.getSahip());
                   }
               }else {
                   result = db.hayvanFiltrele(params, new Date(baslangic));

                   for (Hayvan h : result) {
                       sahipler.add(h.getSahip());
                   }
               }
            }else {
                result = db.hayvanFiltrele(params);

                for (Hayvan h : result) {
                    sahipler.add(h.getSahip());
                }
            }

            String str = String.valueOf(sahipler.size());
            tv.setText(str);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.card, R.id.tvad, sahipler);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener((parent, view, position, id) -> {
                Intent i = new Intent(context, HayvanDetay.class);
                i.putExtra("id", result.get(position).getId());
                startActivity(i);
            });
        }
    }

}
