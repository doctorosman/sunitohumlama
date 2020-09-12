package com.osmanyasirinan.sunitohumlama.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.osmanyasirinan.sunitohumlama.Database;
import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.Utils;
import com.osmanyasirinan.sunitohumlama.tohum.Tohum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

    Button button;
    ImageView sahipimg, esgalimg, tohumimg, koyimg;
    EditText sahipet, esgalet, koyet;
    Spinner tohumsp;

    Button baslangicb, bitisb;
    Switch baslangics, bitiss;
    LinearLayout tarihbuttons;

    long baslangic = -1, bitis = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        button = findViewById(R.id.filtrele);

        sahipet = findViewById(R.id.sahipfet);
        esgalet = findViewById(R.id.esgalfet);
        koyet = findViewById(R.id.koyfet);

        sahipimg = findViewById(R.id.sahip_imgview3);
        esgalimg = findViewById(R.id.esgal_imgview3);
        tohumimg = findViewById(R.id.tohum_imgview3);
        koyimg = findViewById(R.id.koy_imgview3);

        baslangicb = findViewById(R.id.baslangicbutton);
        bitisb = findViewById(R.id.bitisbutton);
        baslangics = findViewById(R.id.baslangicswitch);
        bitiss = findViewById(R.id.bitisswitch);
        tarihbuttons = findViewById(R.id.tarihbuttons);

        baslangicb.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatePickerDialog dpd = new DatePickerDialog(this, (datePicker, yil, ay, gun) -> {
                ay = ay + 1;
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, yil);
                c.set(Calendar.MONTH, ay);
                c.set(Calendar.DAY_OF_MONTH, gun);


                baslangic = Utils.zeroizeTime(new Date(c.getTimeInMillis() / 1000L)).getTime();
            }, year, month, dayOfMonth);
            dpd.show();
        });

        bitisb.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatePickerDialog dpd = new DatePickerDialog(this, (datePicker, yil, ay, gun) -> {
                ay = ay + 1;
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, yil);
                c.set(Calendar.MONTH, ay);
                c.set(Calendar.DAY_OF_MONTH, gun);

                bitis = Utils.zeroizeTime(new Date(c.getTimeInMillis() / 1000L)).getTime();
            }, year, month, dayOfMonth);
            dpd.show();
        });

        sahipet.addTextChangedListener(Utils.watcher(sahipimg, R.drawable.ic_account_circle_black_24dp, R.drawable.account_colorful));
        esgalet.addTextChangedListener(Utils.watcher(esgalimg, R.drawable.ic_info_black_24dp, R.drawable.info_colorful));
        koyet.addTextChangedListener(Utils.watcher(koyimg, R.drawable.ic_location_on_black_24dp, R.drawable.location_colorful));

        baslangics.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b)
                openBaslangic();
            else
                closeBaslangic();
        });

        bitiss.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b)
                openBitis();
            else
                closeBitis();
        });

        // FILTER SPINNER
        tohumsp = findViewById(R.id.filterspinner);

        Database db = new Database(this);
        List<String> strlist = new ArrayList<>();

        strlist.add(getString(R.string.spinitem_herhangi));

        for (Tohum t : db.tohumListele()) {
            strlist.add(t.getIsim());
        }

        String[] list = new String[strlist.size()];
        list = strlist.toArray(list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tohumsp.setAdapter(adapter);

        tohumsp.setOnItemSelectedListener(Utils.itemSelectedListener(tohumimg, R.drawable.ic_bubble_chart_black_24dp, R.drawable.bubble_colorful));

        button.setOnClickListener(v -> {
            String sahip = sahipet.getText().toString();
            String tohum = (tohumsp.getSelectedItem().toString().equals(getString(R.string.spinitem_herhangi)) ? "" : tohumsp.getSelectedItem().toString());
            String esgal = esgalet.getText().toString();
            String koy = koyet.getText().toString();

            if (baslangic > bitis) {
                Toast.makeText(this, getString(R.string.toast_basbuyukbitis), Toast.LENGTH_SHORT).show();
            }else if (!sahip.equals("") || !tohum.equals("") || !esgal.equals("") || !koy.equals("") || baslangic != -1) {
                Intent i = new Intent(FilterActivity.this, MainActivity.class);
                i.putExtra("params", new String[]{sahip, esgal, tohum, koy});

                if (baslangic != -1) {
                    i.putExtra("baslangic", baslangic);

                    if (bitis != -1) {
                        i.putExtra("bitis", bitis);
                    }
                }
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
    }

    private void openBaslangic() {
        tarihbuttons.setVisibility(View.VISIBLE);
        baslangicb.setVisibility(View.VISIBLE);
        baslangicb.setText(getString(R.string.button_tarih));
        bitisb.setVisibility(View.GONE);
        bitiss.setVisibility(View.VISIBLE);
    }

    private void closeBaslangic() {
        tarihbuttons.setVisibility(View.GONE);
        baslangicb.setVisibility(View.GONE);
        bitisb.setVisibility(View.GONE);
        bitiss.setVisibility(View.GONE);
    }

    private void openBitis() {
        bitisb.setVisibility(View.VISIBLE);
        baslangicb.setText(getString(R.string.button_baslangic));
    }

    private void closeBitis() {
        bitisb.setVisibility(View.GONE);
        baslangicb.setText(getString(R.string.button_tarih));
    }

}