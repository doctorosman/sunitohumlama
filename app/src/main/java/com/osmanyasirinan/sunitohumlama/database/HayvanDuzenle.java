package com.osmanyasirinan.sunitohumlama.database;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.osmanyasirinan.sunitohumlama.R;

import java.util.Calendar;

public class HayvanDuzenle extends AppCompatActivity {

    EditText sahipet, koyet, tohumet, esgalet;
    Button kaydet, cb;
    String currentDate;
    int id;
    Hayvan h;
    ImageView sahipimg, koyimg, tohumimg, esgalimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hayvan_duzenle);

        sahipet = findViewById(R.id.sahipdet);
        koyet = findViewById(R.id.koydet);
        tohumet = findViewById(R.id.tohumdet);
        esgalet = findViewById(R.id.esgaldet);

        sahipimg = findViewById(R.id.sahip_imgview1);
        esgalimg = findViewById(R.id.esgal_imgview1);
        tohumimg = findViewById(R.id.tohum_imgview1);
        koyimg = findViewById(R.id.koy_imgview1);

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
                        currentDate = new Utils().putZeros(dayOfMonth) + "." + new Utils().putZeros(month) + "." + year;
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

        sahipet.addTextChangedListener(new Utils().watcher(sahipimg, R.drawable.ic_account_circle_black_24dp, R.drawable.account_colorful));
        koyet.addTextChangedListener(new Utils().watcher(koyimg, R.drawable.ic_location_on_black_24dp, R.drawable.location_colorful));
        esgalet.addTextChangedListener(new Utils().watcher(esgalimg, R.drawable.ic_info_black_24dp, R.drawable.info_colorful));
        tohumet.addTextChangedListener(new Utils().watcher(tohumimg, R.drawable.ic_bubble_chart_black_24dp, R.drawable.bubble_colorful));
    }

    private void setItems(){
        sahipet.setText(h.getSahip());
        koyet.setText(h.getKoy());
        esgalet.setText(h.getEsgal());
        tohumet.setText(h.getTohum());
    }


}
