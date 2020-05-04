package com.osmanyasirinan.begsanvet.database;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.osmanyasirinan.begsanvet.R;

import java.util.Calendar;

public class HayvanDuzenle extends AppCompatActivity {

    EditText sahipet, koyet, tohumet, esgalet;
    Button kaydet, cb;
    String currentDate;
    int id;
    Hayvan h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hayvan_duzenle);

        getSupportActionBar().setTitle("Hayvan Düzenle");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sahipet = findViewById(R.id.sahipdet);
        koyet = findViewById(R.id.koydet);
        tohumet = findViewById(R.id.tohumdet);
        esgalet = findViewById(R.id.esgaldet);

        kaydet = findViewById(R.id.kayddet);
        cb = findViewById(R.id.cdb);

        id = getIntent().getIntExtra("id", 0);
        Database db = new Database(this);
        h = db.ara(id);

        currentDate = h.getTarih();

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar takvim = Calendar.getInstance();
                int gun = takvim.get(Calendar.DAY_OF_MONTH);
                int ay = takvim.get(Calendar.MONTH);
                int yil = takvim.get(Calendar.YEAR);

                DatePickerDialog dpd = new DatePickerDialog(HayvanDuzenle.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month += 1;
                        YeniHayvan y = new YeniHayvan();
                        currentDate = y.putZeros(dayOfMonth) + "." + y.putZeros(month) + "." + year;
                    }
                }, yil, ay, gun);

                dpd.show();
            }
        });

        setItems();

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database vt = new Database(HayvanDuzenle.this);
                vt.veriDuzenle(id, sahipet.getText().toString(), esgalet.getText().toString(), tohumet.getText().toString(), koyet.getText().toString(), currentDate);
                finish();
            }
        });
    }

    private void setItems(){
        sahipet.setText(h.getSahip());
        koyet.setText(h.getKoy());
        esgalet.setText(h.getEsgal());
        tohumet.setText(h.getTohum());
    }
}
