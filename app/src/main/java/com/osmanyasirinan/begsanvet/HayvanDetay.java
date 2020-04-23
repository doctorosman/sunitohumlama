package com.osmanyasirinan.begsanvet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class HayvanDetay extends AppCompatActivity {

    Hayvan h;
    int id;
    TextView sahiptv, esgaltv, tohumtv, koytv, tarihtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hayvan_detay);

        sahiptv = findViewById(R.id.sahiptv);
        esgaltv = findViewById(R.id.esgaltv);
        tohumtv = findViewById(R.id.tohumtv);
        koytv = findViewById(R.id.koytv);
        tarihtv = findViewById(R.id.tarihtv);

        getSupportActionBar().setTitle("Hayvan Bilgisi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = getIntent().getIntExtra("id", 0);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemduzenle:
                Intent i = new Intent(HayvanDetay.this, HayvanDuzenle.class);
                i.putExtra("id", id);
                startActivity(i);
                return true;

            case R.id.itemsil:
                Database db = new Database(this);
                db.veriSil(id);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.detay, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
    }
}
