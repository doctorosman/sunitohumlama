package com.osmanyasirinan.sunitohumlama.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.osmanyasirinan.sunitohumlama.R;

public class HayvanDetay extends AppCompatActivity {

    ImageButton edit, delete;
    Hayvan h;
    int id;
    TextView sahiptv, esgaltv, tohumtv, koytv, tarihtv, tahminidogum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hayvan_detay);

        sahiptv = findViewById(R.id.sahiptv);
        esgaltv = findViewById(R.id.esgaltv);
        tohumtv = findViewById(R.id.tohumtv);
        koytv = findViewById(R.id.koytv);
        tarihtv = findViewById(R.id.tarihtv);
        tahminidogum = findViewById(R.id.tahminidogum);

        edit = findViewById(R.id.editbutton);
        delete = findViewById(R.id.deletebutton);

        id = getIntent().getIntExtra("id", 0);
    }

    @Override
    protected void onStart() {
        super.onStart();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HayvanDetay.this, HayvanDuzenle.class);
                i.putExtra("id", id);
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db = new Database(HayvanDetay.this);
                db.veriSil(id);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        guncelle();
    }

    public void guncelle() {
        Database db = new Database(this);
        h = db.ara(id);
        sahiptv.setText(h.getSahip());
        esgaltv.setText(h.getEsgal());
        tohumtv.setText(h.getTohum());
        koytv.setText(h.getKoy());
        tarihtv.setText(h.getTarih());

        String tahmini = getString(R.string.tahmini) + "  " + h.getTahminiDogum();
        tahminidogum.setText(tahmini);
    }
}
