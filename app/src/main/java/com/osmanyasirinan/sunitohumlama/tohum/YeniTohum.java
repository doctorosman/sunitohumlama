package com.osmanyasirinan.sunitohumlama.tohum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.osmanyasirinan.sunitohumlama.Database;
import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.Utils;
import com.osmanyasirinan.sunitohumlama.hayvan.YeniHayvan;

public class YeniTohum extends AppCompatActivity {

    private ImageView img;
    private EditText tohum, miktar;
    private ImageButton arttir, azalt;
    private Button kaydet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_tohum);

        img = findViewById(R.id.tohumnewimgview);
        tohum = findViewById(R.id.tohumnewet);
        miktar = findViewById(R.id.tohumnewmiktar);
        arttir = findViewById(R.id.tohumnewarttir);
        azalt = findViewById(R.id.tohumnewazalt);
        kaydet = findViewById(R.id.tohumnewb);

        tohum.addTextChangedListener(new Utils().watcher(img, R.drawable.ic_bubble_chart_black_24dp, R.drawable.bubble_colorful));

        arttir.setOnClickListener(v -> {
            int newValue = Integer.parseInt(miktar.getText().toString()) + 20;
            miktar.setText(newValue);
        });

        azalt.setOnClickListener(v -> {
            int newValue = Integer.parseInt(miktar.getText().toString()) - 20;
            miktar.setText(newValue);
        });

        kaydet.setOnClickListener(v -> {
            Database db = new Database(YeniTohum.this);
            db.tohumEkle(tohum.getText().toString(), Integer.parseInt(miktar.getText().toString()));
            Toast.makeText(getApplicationContext(), "Tohum eklendi.",Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
