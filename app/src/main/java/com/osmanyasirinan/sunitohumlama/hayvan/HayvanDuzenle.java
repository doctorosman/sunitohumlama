package com.osmanyasirinan.sunitohumlama.hayvan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.osmanyasirinan.sunitohumlama.Database;
import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.Utils;
import com.osmanyasirinan.sunitohumlama.tohum.Tohum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HayvanDuzenle extends AppCompatActivity {

    EditText sahipet, koyet, esgalet;
    Spinner spinner;
    Button kaydet, cb;
    Date currentDate;
    int id;
    Hayvan h;
    ImageView sahipimg, koyimg, tohumimg, esgalimg;
    List<Integer> intlist;
    List<String> newlist = new ArrayList<>(), strlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hayvan_duzenle);

        sahipet = findViewById(R.id.sahipdet);
        koyet = findViewById(R.id.koydet);
        esgalet = findViewById(R.id.esgaldet);

        sahipimg = findViewById(R.id.sahip_imgview1);
        esgalimg = findViewById(R.id.esgal_imgview1);
        tohumimg = findViewById(R.id.tohum_imgview1);
        koyimg = findViewById(R.id.koy_imgview1);

        kaydet = findViewById(R.id.kayddet);
        cb = findViewById(R.id.cdb);

        id = getIntent().getIntExtra("id", 0);
        Database db = new Database(this);
        h = db.getHayvan(id);

        currentDate = h.getTarih();

        cb.setOnClickListener(v -> {
            final Calendar takvim = Calendar.getInstance();
            int gun = takvim.get(Calendar.DAY_OF_MONTH);
            int ay = takvim.get(Calendar.MONTH);
            int yil = takvim.get(Calendar.YEAR);

            DatePickerDialog dpd = new DatePickerDialog(HayvanDuzenle.this, (view, year, month, dayOfMonth) -> {
                month += 1;

                Calendar c = Calendar.getInstance();

                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                currentDate = new Date(c.getTimeInMillis() / 1000L);
            }, yil, ay, gun);

            dpd.show();
        });

        setItems();

        sahipet.addTextChangedListener(new Utils().watcher(sahipimg, R.drawable.ic_account_circle_black_24dp, R.drawable.account_colorful));
        koyet.addTextChangedListener(new Utils().watcher(koyimg, R.drawable.ic_location_on_black_24dp, R.drawable.location_colorful));
        esgalet.addTextChangedListener(new Utils().watcher(esgalimg, R.drawable.ic_info_black_24dp, R.drawable.info_colorful));

        // SPINNER
        spinner = findViewById(R.id.hayvaneditspinner);

        intlist = new ArrayList<>();

        boolean key = false;
        for (Tohum t : db.tohumListele()) {
            if (t.getMiktar() >= 0) { // Son tohumun gösterilmesi için
                strlist.add(t.getIsim());
                intlist.add(t.getId());
                key = true;
            }
        }

        if (!key)
            newlist.add("Stokta tohum yok");
        else
            newlist.add("Tohum seçin");

        newlist.addAll(strlist);

        String[] list = new String[newlist.size()];
        list = newlist.toArray(list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        int j = 0;
        for (String s : newlist) {
            if (s.equals(h.getTohum())) {
                spinner.setSelection(j);
            }
            j++;
        }

        spinner.setOnItemSelectedListener(new Utils().itemSelectedListener(tohumimg, R.drawable.ic_bubble_chart_black_24dp, R.drawable.bubble_colorful));

        kaydet.setOnClickListener(v -> {
            Database vt = new Database(HayvanDuzenle.this);

            String tohum;

            if (spinner.getSelectedItem().toString().equals("Stokta tohum yok") || spinner.getSelectedItem().toString().equals("Tohum seçin")) {
                tohum = "";
            }else {
                tohum = spinner.getSelectedItem().toString();
            }

            vt.hayvanGuncelle(id, sahipet.getText().toString(), esgalet.getText().toString(), tohum, koyet.getText().toString(), currentDate);

            if (!tohum.equals(h.getTohum())){
                for (Tohum t : db.tohumListele()) {
                    if (t.getIsim().equals(tohum)) {
                        db.tohumAzalt(t.getId());
                    }

                    if (t.getIsim().equals(h.getTohum())) {
                        db.tohumArttir(t.getId());
                    }
                }
            }

            finish();
        });
    }

    private void setItems(){
        sahipet.setText(h.getSahip());
        koyet.setText(h.getKoy());
        esgalet.setText(h.getEsgal());
    }


}
