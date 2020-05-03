package com.osmanyasirinan.begsanvet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class YeniHayvan extends AppCompatActivity {

    DatabaseReference ref;
    FirebaseAuth auth;
    EditText sahipet, koyet, tohumet, esgalet;
    Button kaydet, cb;
    String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_hayvan);

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("hayvanlar");

        sahipet = findViewById(R.id.sahipet);
        koyet = findViewById(R.id.koyet);
        tohumet = findViewById(R.id.tohumet);
        esgalet = findViewById(R.id.esgalet);

        kaydet = findViewById(R.id.kaydet);
        cb = findViewById(R.id.cb);

        Calendar calendar = Calendar.getInstance();
        int gun = calendar.get(Calendar.DAY_OF_MONTH);
        int ay = calendar.get(Calendar.MONTH);
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

    private void hayvanYaz(Hayvan h, FirebaseUser user) {
        DatabaseReference mref = ref.child(user.getUid()).push();
        mref.child("sahip").setValue(h.getSahip());
        mref.child("esgal").setValue(h.getEsgal());
        mref.child("tohum").setValue(h.getTohum());
        mref.child("koy").setValue(h.getKoy());
        mref.child("tarih").setValue(h.getTarih());
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
