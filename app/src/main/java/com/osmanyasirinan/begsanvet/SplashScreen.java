package com.osmanyasirinan.begsanvet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth auth;
    private Thread thread;
    private EditText eposta, sifre;
    private Button giris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        auth = FirebaseAuth.getInstance();

        eposta = findViewById(R.id.eposta);
        sifre = findViewById(R.id.sifre);
        giris = findViewById(R.id.giris);

        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(eposta.getText().toString(), sifre.getText().toString());
            }
        });

        thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }

    private void signIn(String eposta, String sifre){
        auth.signInWithEmailAndPassword(eposta, sifre).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    updateUI(user);
                }else {
                    Toast.makeText(SplashScreen.this, "Giriş başarısız.", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser user){
        if (user != null){
            eposta.setVisibility(View.GONE);
            sifre.setVisibility(View.GONE);
            giris.setVisibility(View.GONE);
            thread.start();
        }else {
            eposta.setVisibility(View.VISIBLE);
            sifre.setVisibility(View.VISIBLE);
            giris.setVisibility(View.VISIBLE);
        }
    }
}
