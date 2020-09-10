package com.osmanyasirinan.sunitohumlama.hayvan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.osmanyasirinan.sunitohumlama.Database;
import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.Utils;
import com.osmanyasirinan.sunitohumlama.main.MainActivity;
import com.osmanyasirinan.sunitohumlama.tohum.Tohum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class YeniHayvan extends AppCompatActivity {

    ImageView sahipimg, koyimg, tohumimg, esgalimg;
    EditText sahipet, koyet, esgalet;
    Button kaydet, cb;
    Date currentDate;
    Spinner spinner;
    List<Integer> intlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_hayvan);

        sahipet = findViewById(R.id.sahipet);
        koyet = findViewById(R.id.koyet);
        esgalet = findViewById(R.id.esgalet);

        sahipimg = findViewById(R.id.sahip_imgview2);
        koyimg = findViewById(R.id.koy_imgview2);
        esgalimg = findViewById(R.id.esgal_imgview2);
        tohumimg = findViewById(R.id.tohum_imgview2);

        kaydet = findViewById(R.id.kaydet);
        cb = findViewById(R.id.cb);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        currentDate = new Date(calendar.getTimeInMillis() / 1000L);

        cb.setOnClickListener(v -> {
            final Calendar takvim = Calendar.getInstance();
            int gun = takvim.get(Calendar.DAY_OF_MONTH);
            int ay = takvim.get(Calendar.MONTH);
            int yil = takvim.get(Calendar.YEAR);

            DatePickerDialog dpd = new DatePickerDialog(YeniHayvan.this, (view, year, month, dayOfMonth) -> {
                month += 1;

                Calendar c = Calendar.getInstance();

                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                currentDate = new Date(c.getTimeInMillis() / 1000L);
            }, yil, ay, gun);

            dpd.show();
        });

        sahipet.addTextChangedListener(Utils.watcher(sahipimg, R.drawable.ic_account_circle_black_24dp, R.drawable.account_colorful));
        koyet.addTextChangedListener(Utils.watcher(koyimg, R.drawable.ic_location_on_black_24dp, R.drawable.location_colorful));
        esgalet.addTextChangedListener(Utils.watcher(esgalimg, R.drawable.ic_info_black_24dp, R.drawable.info_colorful));

        // TOHUM SPINNER
        spinner = findViewById(R.id.spinner);

        Database db = new Database(this);
        List<String> strlist = new ArrayList<>();
        List<String> newlist = new ArrayList<>();
        intlist = new ArrayList<>();

        boolean key = false;
        for (Tohum t : db.tohumListele()) {
            if (t.getMiktar() > 0) {
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

        spinner.setOnItemSelectedListener(Utils.itemSelectedListener(tohumimg, R.drawable.ic_bubble_chart_black_24dp, R.drawable.bubble_colorful));
     }

    @Override
    protected void onStart() {
        super.onStart();

        kaydet.setOnClickListener(v -> {
            String sahip = sahipet.getText().toString();
            String koy = koyet.getText().toString();
            String esgal = esgalet.getText().toString();
            String tohum;

            if (spinner.getSelectedItem().toString().equals("Stokta tohum yok") || spinner.getSelectedItem().toString().equals("Tohum seçin")) {
                tohum = "";
            }else {
                tohum = spinner.getSelectedItem().toString();
            }

            Database vt = new Database(YeniHayvan.this);
            vt.hayvanEkle(sahip, esgal, tohum, koy, currentDate);
            if (!tohum.equals(""))
                vt.tohumAzalt(intlist.get(spinner.getSelectedItemPosition() - 1));
            bitir();
        });
    }

    private void bitir(){
        Toast.makeText(getApplicationContext(), "Hayvan eklendi.",Toast.LENGTH_SHORT).show();
        finish();
    }

}
