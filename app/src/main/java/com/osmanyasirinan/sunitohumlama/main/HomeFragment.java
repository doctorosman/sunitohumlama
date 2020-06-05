package com.osmanyasirinan.sunitohumlama.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.osmanyasirinan.sunitohumlama.database.Database;
import com.osmanyasirinan.sunitohumlama.database.HayvanDetay;
import com.osmanyasirinan.sunitohumlama.R;

import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private View v;
    private EditText et;
    private ListView lv;
    private ArrayAdapter adapter;
    private Context context;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public HomeFragment(Context context){
        this.context = context;
        prefs = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);

        et = v.findViewById(R.id.srch);
        lv = v.findViewById(R.id.list);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listSearched(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString("text", s.toString());
                editor.commit();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Database db = new Database(context);
                int did;
                if (et.getText().toString().equals(""))
                    did = db.idListele().get(position);
                else
                    did = db.idListele(et.getText().toString()).get(position);

                Intent i = new Intent(context, HayvanDetay.class);
                i.putExtra("id", did);
                startActivity(i);
            }
        });

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!prefs.getString("text", "").equals(""))
            et.setText(prefs.getString("text", ""));

        if (et.getText().toString().equals("")){
            Listele();
        }else {
            listSearched(et.getText().toString());
        }
    }

    private void Listele(){
        Database vt = new Database(context);
        List<String> list = vt.veriListele();
        adapter = new ArrayAdapter<>(context, R.layout.card, R.id.tvad, list);
        lv.setAdapter(adapter);
    }

    private void listSearched(String str){
        Database vt = new Database(context);
        List<String> list = vt.veriListele(str);
        adapter = new ArrayAdapter<>(context, R.layout.card, R.id.tvad, list);
        lv.setAdapter(adapter);
    }
}
