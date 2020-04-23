package com.osmanyasirinan.begsanvet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    EditText srch;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.list);
        srch = findViewById(R.id.srch);
    }

    @Override
    protected void onStart() {
        super.onStart();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Database db = new Database(MainActivity.this);
                int did;
                if (srch.getText().toString().equals("")){
                    did = db.idListele().get(position);
                }else {
                    did = db.idListele(srch.getText().toString()).get(position);
                }
                Intent i = new Intent(MainActivity.this, HayvanDetay.class);
                i.putExtra("id", did);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (srch.getText().toString().equals("")){
            Listele();
        }else {
            listSearched(srch.getText().toString());
        }
        srch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listSearched(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemadd:
                Intent i = new Intent(MainActivity.this, YeniHayvan.class);
                startActivity(i);
                return true;

            case R.id.itemmore:
                Intent k = new Intent(MainActivity.this, Bilgi.class);
                startActivity(k);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Listele(){
        Database vt = new Database(MainActivity.this);
        List<String> list = vt.veriListele();
        adapter = new ArrayAdapter<>(MainActivity.this, R.layout.card, R.id.tvad, list);
        lv.setAdapter(adapter);
    }

    public void listSearched(String str){
        Database vt = new Database(this);
        List<String> list = vt.veriListele(str);
        adapter = new ArrayAdapter<>(this, R.layout.card, R.id.tvad, list);
        lv.setAdapter(adapter);
    }

    public String putZeros(int a){
        if (String.valueOf(a).length() == 1){
            return "0" + a;
        }else {
            return a + "";
        }
    }

}
