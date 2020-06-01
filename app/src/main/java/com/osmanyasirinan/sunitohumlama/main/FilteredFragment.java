package com.osmanyasirinan.sunitohumlama.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.database.Database;
import com.osmanyasirinan.sunitohumlama.database.Hayvan;
import com.osmanyasirinan.sunitohumlama.database.HayvanDetay;

import java.util.ArrayList;
import java.util.List;

public class FilteredFragment extends Fragment {

    private View v;
    private TextView tv;
    private ListView lv;
    private Context context;
    private String[] strings;
    private int[] parts;

    public FilteredFragment(Context context) {
        this.context = context;
    }

    public FilteredFragment(Context context, String[] strings, int[] parts) {
        this.context = context;
        this.strings = strings;
        this.parts = parts;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_filtered, container, false);

        tv = v.findViewById(R.id.kayitsayisi);
        lv = v.findViewById(R.id.filteredlistview);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (strings != null || parts != null) {
            Database db = new Database(context);
            List<String> sahips = new ArrayList<>();
            final List<Hayvan> source = db.filtrele(strings[0], strings[1], strings[2], strings[3], parts[0], parts[1]);

            for (Hayvan h : source) {
                sahips.add(h.getSahip());
            }
            tv.setText(sahips.size() + " kayıt bulundu.");

            ArrayAdapter adapter = new ArrayAdapter<>(context, R.layout.card, R.id.tvad, sahips);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(context, HayvanDetay.class);
                    i.putExtra("id", source.get(position).getId());
                    startActivity(i);
                }
            });
        }
    }
}
