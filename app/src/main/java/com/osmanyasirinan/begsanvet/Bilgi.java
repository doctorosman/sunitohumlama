package com.osmanyasirinan.begsanvet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class Bilgi extends AppCompatActivity {

    TextView topkayit, gunkayit, aykayit;
    String currentDate;
    ImageButton tarihkayit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilgi);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        topkayit = findViewById(R.id.topkayit);
        gunkayit = findViewById(R.id.gunkayit);
        aykayit = findViewById(R.id.aykayit);

        tarihkayit = findViewById(R.id.tarihkayit);

        Calendar calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());

        Database db = new Database(Bilgi.this);
        String mesaj = "";
        for (int j = 1; j <= 12; j++){
            int kayit = db.getAyKayit(j);
            if (kayit > 0){
                mesaj += getAy(j) + " Ayı: " + kayit + "\n";
            }
        }
        aykayit.setText(mesaj);
        String topkayitt = "Toplam kayıt: " + db.idListele().size() + "";
        topkayit.setText(topkayitt);

        String gunkayitt = "Bugün yapılan tohumlama: " + db.getTarihKayit(currentDate) + "\n";
        gunkayit.setText(gunkayitt);

        tarihkayit.setOnClickListener(new View.OnClickListener() {
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
