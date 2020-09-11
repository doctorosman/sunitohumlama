package com.osmanyasirinan.sunitohumlama.tohum;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.osmanyasirinan.sunitohumlama.Database;
import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.hayvan.HayvanDetay;

public class TohumDetay extends AppCompatActivity {

    TextView tohum, miktar;
    ImageButton delete, edit;
    Database db = new Database(this);
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tohum_detay);

        tohum = findViewById(R.id.tohumdetaytohum);
        miktar = findViewById(R.id.tohumdetaymiktar);
        delete = findViewById(R.id.tohumdetaydelete);
        edit = findViewById(R.id.tohumdetayedit);

        id = getIntent().getIntExtra("id", 0);

        edit.setOnClickListener(v -> {
            Intent i = new Intent(TohumDetay.this, TohumDuzenle.class);
            i.putExtra("id", id);
            startActivity(i);
        });

        delete.setOnClickListener(v -> new AlertDialog.Builder(TohumDetay.this)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.dialog_delete))
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    db.tohumSil(id);
                    finish();
                })
                .setNegativeButton(R.string.no, null)
                .setIcon(R.drawable.dialog_delete)
                .show());
    }

    @Override
    protected void onResume() {
        super.onResume();
        tohum.setText(db.tohumAra(id).getIsim());
        miktar.setText(String.valueOf(db.tohumAra(id).getMiktar()));
    }
}
