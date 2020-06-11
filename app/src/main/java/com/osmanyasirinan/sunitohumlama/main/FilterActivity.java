package com.osmanyasirinan.sunitohumlama.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.osmanyasirinan.sunitohumlama.Database;
import com.osmanyasirinan.sunitohumlama.MonthYearPickerDialog;
import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.Utils;

public class FilterActivity extends AppCompatActivity {

    CardView pickay;
    Button button;
    ImageView sahipimg, esgalimg, tohumimg, koyimg;
    EditText sahipet, esgalet, tohumet, koyet;
    int selectedAy = 0, selectedYil = new Database(this).YIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        pickay = findViewById(R.id.pickay);
        button = findViewById(R.id.filtrele);

        sahipet = findViewById(R.id.sahipfet);
        esgalet = findViewById(R.id.esgalfet);
        tohumet = findViewById(R.id.tohumfet);
        koyet = findViewById(R.id.koyfet);

        sahipimg = findViewById(R.id.sahip_imgview3);
        esgalimg = findViewById(R.id.esgal_imgview3);
        tohumimg = findViewById(R.id.tohum_imgview3);
        koyimg = findViewById(R.id.koy_imgview3);

        pickay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthYearPickerDialog pickerDialog = new MonthYearPickerDialog();
                pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedAy = month;
                        selectedYil = year;
                    }
                });
                pickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
            }
        });

        sahipet.addTextChangedListener(new Utils().watcher(sahipimg, R.drawable.ic_account_circle_black_24dp, R.drawable.account_colorful));
        esgalet.addTextChangedListener(new Utils().watcher(esgalimg, R.drawable.ic_info_black_24dp, R.drawable.info_colorful));
        tohumet.addTextChangedListener(new Utils().watcher(tohumimg, R.drawable.ic_bubble_chart_black_24dp, R.drawable.bubble_colorful));
        koyet.addTextChangedListener(new Utils().watcher(koyimg, R.drawable.ic_location_on_black_24dp, R.drawable.location_colorful));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sahipet.getText().toString().equals("") && esgalet.getText().toString().equals("") && tohumet.getText().toString().equals("") && koyet.getText().toString().equals("") && selectedAy == 0){

                }else {
                    Intent i = new Intent(FilterActivity.this, MainActivity.class);
                    i.putExtra("strings", new String[]{sahipet.getText().toString(), esgalet.getText().toString(), tohumet.getText().toString(), koyet.getText().toString()});
                    i.putExtra("parts", new int[]{selectedAy, selectedYil});
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}
