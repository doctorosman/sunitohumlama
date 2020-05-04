package com.osmanyasirinan.begsanvet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.osmanyasirinan.begsanvet.database.Database;

import java.text.DateFormat;
import java.util.Calendar;

public class Bilgi extends AppCompatActivity {

    String currentDate;
    Button aykayit, gunkayit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilgi);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());

        aykayit = findViewById(R.id.aykayit);
        gunkayit = findViewById(R.id.gunkayit);

        aykayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthYearPickerDialog pickerDialog = new MonthYearPickerDialog();
                pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
                        Database db = new Database(Bilgi.this);
                        String str = getAy(month) + " ayında yapılan tohumlama sayısı: " + db.getAyKayit(month);
                        Toast.makeText(Bilgi.this, str, Toast.LENGTH_SHORT).show();
                    }
                });
                pickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
            }
        });

        gunkayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar takvim = Calendar.getInstance();
                int gun = takvim.get(Calendar.DAY_OF_MONTH);
                int ay = takvim.get(Calendar.MONTH);
                int yil = takvim.get(Calendar.YEAR);

                DatePickerDialog dpd = new DatePickerDialog(Bilgi.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month += 1;
                        Database db = new Database(Bilgi.this);
                        String tarih = putZeros(dayOfMonth) + "." + putZeros(month) + "." + year;
                        String str = tarih + " tarihinde yapılan tohumlama sayısı: " + db.getTarihKayit(tarih);
                        Toast.makeText(Bilgi.this, str, Toast.LENGTH_SHORT).show();
                    }
                }, yil, ay, gun);

                dpd.show();
            }
        });
    }

    public String getAy(int b) {
        switch (b){
            case 1:
                return "Ocak";

            case 2:
                return "Şubat";

            case 3:
                return "Mart";

            case 4:
                return "Nisan";

            case 5:
                return "Mayıs";

            case 6:
                return "Haziran";

            case 7:
                return "Temmuz";

            case 8:
                return "Ağustos";

            case 9:
                return "Eylül";

            case 10:
                return "Ekim";

            case 11:
                return "Kasım";

            case 12:
                return "Aralık";

            default:
                return null;
        }
    }
    public String putZeros(int a){
        if (String.valueOf(a).length() == 1){
            return "0" + a;
        }else {
            return a + "";
        }
    }
}
