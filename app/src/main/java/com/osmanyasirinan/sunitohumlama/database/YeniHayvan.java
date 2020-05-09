package com.osmanyasirinan.sunitohumlama.database;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.main.MainActivity;

import java.util.Calendar;

public class YeniHayvan extends AppCompatActivity {

    EditText sahipet, koyet, tohumet, esgalet;
    Button kaydet, cb;
    String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_hayvan);

        sahipet = findViewById(R.id.sahipet);
        koyet = findViewById(R.id.koyet);
        tohumet = findViewById(R.id.tohumet);
        esgalet = findViewById(R.id.esgalet);

        kaydet = findViewById(R.id.kaydet);
        cb = findViewById(R.id.cb);

        Calendar calendar = Calendar.getInstance();
        int gun = calendar.get(Calendar.DAY_OF_MONTH);
        int ay = calendar.get(Calendar.MONTH) + 1;
        int yil = calendar.get(Calendar.YEAR);

        currentDate = putZeros(gun) + "." + putZeros(ay) + "." + yil;

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar takvim = Calendar.getInstance();
                int gun = takvim.get(Calendar.DAY_OF_MONTH);
                int ay = takvim.get(Calendar.MONTH);
                int yil = takvim.get(Calendar.YEAR);

                DatePickerDialog dpd = new DatePickerDialog(YeniHayvan.this, new DatePickerDialog.OnDateSetListener() {
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
    }

    @Override
    protected void onStart() {
        super.onStart();

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hayvan h = new Hayvan(sahipet.getText().toString(), esgalet.getText().toString(), tohumet.getText().toString(), koyet.getText().toString(), currentDate);
                Database vt = new Database(YeniHayvan.this);
                vt.veriEkle(sahipet.getText().toString(), esgalet.getText().toString(), tohumet.getText().toString(), koyet.getText().toString(), currentDate);
                bitir();
            }
        });
    }

    private void bitir(){
        Toast.makeText(getApplicationContext(), "Hayvan eklendi.",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(YeniHayvan.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public String putZeros(int a){
        if (String.valueOf(a).length() == 1){
            return "0" + a;
        }else {
            return a + "";
        }
    }

}
