package com.osmanyasirinan.begsanvet.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.osmanyasirinan.begsanvet.R;
import com.osmanyasirinan.begsanvet.database.Database;
import com.osmanyasirinan.begsanvet.database.HayvanDetay;
import com.osmanyasirinan.begsanvet.database.YeniHayvan;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FilteredList extends AppCompatActivity {

    private ListView lv;
    private TextView kayitbulundu;
    private ArrayAdapter adapter;
    private CardView dpBaslangic, dpBitis;
    private String dateBaslangic, dateBitis;
    private List<Integer> idList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_list);

        lv = findViewById(R.id.listfiltered);
        kayitbulundu = findViewById(R.id.tvkayitbulundu);
        dpBaslangic = findViewById(R.id.dpBaslangic);
        dpBitis = findViewById(R.id.dpBitis);

        getSupportActionBar().setTitle("Filtrele");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        int gun = calendar.get(Calendar.DAY_OF_MONTH);
        int ay = calendar.get(Calendar.MONTH) + 1;
        int yil = calendar.get(Calendar.YEAR);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        int gun2 = calendar.get(Calendar.DAY_OF_MONTH);

        if (getIntent().getStringExtra("ay") == null) {
            dateBaslangic = putZeros(gun) + "." + putZeros(ay) + "." + yil;
        }else{
            dateBaslangic = "01." + putZeros(ayToInt(getIntent().getStringExtra("ay"))) + "." + yil;
        }

        if (getIntent().getStringExtra("ay") == null) {
            dateBitis = putZeros(gun2) + "." + putZeros(ay) + "." + yil;
        }else{
            dateBitis = "31." + putZeros(ayToInt(getIntent().getStringExtra("ay"))) + "." + yil;
        }

        Log.d("DATE", dateBitis);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int did = idList.get(position);
                Intent i = new Intent(FilteredList.this, HayvanDetay.class);
                i.putExtra("id", did);
                startActivity(i);
            }
        });

        dpBaslangic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar takvim = Calendar.getInstance();
                int gun = takvim.get(Calendar.DAY_OF_MONTH);
                int ay = takvim.get(Calendar.MONTH);
                int yil = takvim.get(Calendar.YEAR);

                DatePickerDialog dpd = new DatePickerDialog(FilteredList.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month += 1;
                        YeniHayvan y = new YeniHayvan();
                        dateBaslangic = y.putZeros(dayOfMonth) + "." + y.putZeros(month) + "." + year;
                        Listele(dateBaslangic, dateBitis);
                    }
                }, yil, ay, gun);

                dpd.show();
            }
        });

        dpBitis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar takvim = Calendar.getInstance();
                int gun = takvim.get(Calendar.DAY_OF_MONTH);
                int ay = takvim.get(Calendar.MONTH);
                int yil = takvim.get(Calendar.YEAR);

                DatePickerDialog dpd = new DatePickerDialog(FilteredList.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month += 1;
                        YeniHayvan y = new YeniHayvan();
                        dateBitis = y.putZeros(dayOfMonth) + "." + y.putZeros(month) + "." + year;
                        Listele(dateBaslangic, dateBitis);
                    }
                }, yil, ay, gun);

                dpd.show();
            }
        });

        Listele(dateBaslangic, dateBitis);
    }

    private void Listele(String baslangic, String bitis){
        Database vt = new Database(FilteredList.this);
        int baslangicGun = Integer.parseInt(baslangic.charAt(0) + "" + baslangic.charAt(1));
        int baslangicAy = Integer.parseInt(baslangic.charAt(3) + "" + baslangic.charAt(4));
        int baslangicYil = Integer.parseInt(baslangic.charAt(6) + "" + baslangic.charAt(7) + "" + baslangic.charAt(8) + "" + baslangic.charAt(9));

        int bitisGun = Integer.parseInt(bitis.charAt(0) + "" + bitis.charAt(1));
        int bitisAy = Integer.parseInt(bitis.charAt(3) + "" + bitis.charAt(4));
        int bitisYil = Integer.parseInt(bitis.charAt(6) + "" + bitis.charAt(7) + "" + bitis.charAt(8) + "" + bitis.charAt(9));

        Calendar cBaslangic = Calendar.getInstance();
        cBaslangic.set(baslangicYil, baslangicAy, baslangicGun);
        Calendar cBitis = Calendar.getInstance();
        cBitis.set(bitisYil, bitisAy, bitisGun);
        List<String> list = new ArrayList<>();

        idList.clear();

        while (cBaslangic.getTimeInMillis() <= cBitis.getTimeInMillis()){
            int gun = cBaslangic.get(Calendar.DAY_OF_MONTH);
            int ay = cBaslangic.get(Calendar.MONTH);
            int yil = cBaslangic.get(Calendar.YEAR);

            String dAlinacak = putZeros(gun) + "." + putZeros(ay) + "." + yil;
            list.addAll(vt.filterbyTarih(dAlinacak));
            idList.addAll(vt.filterIdsbyTarih(dAlinacak));
            cBaslangic.add(Calendar.DAY_OF_MONTH, 1);
        }

        adapter = new ArrayAdapter<>(FilteredList.this, R.layout.card, R.id.tvad, list);
        lv.setAdapter(adapter);
        kayitbulundu.setText(lv.getCount() + " kayıt bulundu.");
    }

    private int ayToInt(String s){
        switch (s){
            case "Ocak":
                return 1;
            case "Şubat":
                return 2;
            case "Mart":
                return 3;
            case "Nisan":
                return 4;
            case "Mayıs":
                return 5;
            case "Haziran":
                return 6;
            case "Temmuz":
                return 7;
            case "Ağustos":
                return 8;
            case "Eylül":
                return 9;
            case "Ekim":
                return 10;
            case "Kasım":
                return 11;
            case "Aralık":
                return 12;
            default:
                return 0;
        }
    }

    private String putZeros(int a){
        if (String.valueOf(a).length() == 1){
            return "0" + a;
        }else {
            return a + "";
        }
    }
}
