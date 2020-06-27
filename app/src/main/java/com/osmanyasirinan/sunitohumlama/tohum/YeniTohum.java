package com.osmanyasirinan.sunitohumlama.tohum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.osmanyasirinan.sunitohumlama.main.MainActivity;

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
            String newstr = String.valueOf(newValue);
            miktar.setText(newstr);
        });

        azalt.setOnClickListener(v -> {
            int newValue = Integer.parseInt(miktar.getText().toString()) - 20;
            if (newValue >= 0){
                String newstr = String.valueOf(newValue);
                miktar.setText(newstr);
            }
        });

        kaydet.setOnClickListener(v -> {
            Database db = new Database(YeniTohum.this);
            boolean esit = false;

            for (Tohum t : db.tohumListele()) {
                if (t.getIsim().equals(tohum.getText().toString()))
                    esit = true;
            }

            if (!tohum.getText().toString().equals("")) {
                if (!esit) {
                    db.tohumEkle(tohum.getText().toString(), Integer.parseInt(miktar.getText().toString()));
                    Toast.makeText(getApplicationContext(), "Tohum eklendi.", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(getApplicationContext(), "Aynı adda tohum bulunuyor.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
