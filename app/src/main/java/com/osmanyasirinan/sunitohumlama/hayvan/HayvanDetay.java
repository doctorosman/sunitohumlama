package com.osmanyasirinan.sunitohumlama.hayvan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.osmanyasirinan.sunitohumlama.Database;
import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.tohum.Tohum;

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

        edit.setOnClickListener(v -> {
            Intent i = new Intent(HayvanDetay.this, HayvanDuzenle.class);
            i.putExtra("id", id);
            startActivity(i);
        });

        delete.setOnClickListener(v -> new AlertDialog.Builder(HayvanDetay.this)
               .setTitle("Oyi Suni Tohumlama")
               .setMessage("Silmek istediğinize emin misiniz?")
               .setPositiveButton(R.string.yes, (dialog, which) -> new AlertDialog.Builder(HayvanDetay.this)
                       .setTitle("Oyi Suni Tohumlama")
                       .setMessage("Tohumu kullandınız mı?")
                       .setPositiveButton(R.string.yes, ((dialog1, which1) -> {
                           Database db = new Database(HayvanDetay.this);
                           db.hayvanSil(id);
                           finish();
                       }))
                       .setNegativeButton(R.string.no, (((dialog1, which1) -> {
                           Database db = new Database(HayvanDetay.this);

                           for (Tohum t : db.tohumListele()) {
                               if (t.getIsim().equals(db.getHayvan(id).getTohum())) {
                                   db.tohumArttir(t.getId());
                                   break;
                               }
                           }

                           db.hayvanSil(id);
                           finish();
                       })))
                       .setIcon(R.drawable.dialog_delete)
                       .show())
               .setNegativeButton(R.string.no, null)
               .setIcon(R.drawable.dialog_delete)
               .show());
    }

    @Override
    protected void onResume() {
        super.onResume();
        guncelle();
    }

    public void guncelle() {
        Database db = new Database(this);
        h = db.getHayvan(id);
        sahiptv.setText(h.getSahip());
        esgaltv.setText(h.getEsgal());
        tohumtv.setText(h.getTohum());
        koytv.setText(h.getKoy());
        tarihtv.setText(h.getTarihStr());

        String tahmini = getString(R.string.tahmini) + "  " + h.getTahminiDogum();
        tahminidogum.setText(tahmini);
    }

}