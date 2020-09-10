package com.osmanyasirinan.sunitohumlama.tohum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.osmanyasirinan.sunitohumlama.Database;
import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.Utils;

public class TohumDuzenle extends AppCompatActivity {

    private ImageView img;
    private EditText tohum, miktar;
    private ImageButton arttir, azalt;
    private Button kaydet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tohum_duzenle);

        img = findViewById(R.id.tohumeditimgview);
        tohum = findViewById(R.id.tohumeditet);
        miktar = findViewById(R.id.tohumeditmiktar);
        arttir = findViewById(R.id.tohumeditarttir);
        azalt = findViewById(R.id.tohumeditazalt);
        kaydet = findViewById(R.id.tohumeditb);

        int id = getIntent().getIntExtra("id", 0);

        Database db = new Database(this);

        String tohumstr = db.tohumAra(id).getIsim();
        int miktarint = db.tohumAra(id).getMiktar();

        tohum.setText(tohumstr);
        miktar.setText(String.valueOf(miktarint));

        tohum.addTextChangedListener(Utils.watcher(img, R.drawable.ic_bubble_chart_black_24dp, R.drawable.bubble_colorful));

        arttir.setOnClickListener(v -> {
            int newValue = Integer.parseInt(miktar.getText().toString()) + 20;
            miktar.setText(String.valueOf(newValue));
        });

        azalt.setOnClickListener(v -> {
            int newValue = Integer.parseInt(miktar.getText().toString()) - 20;
            if (newValue >= 0)
                miktar.setText(String.valueOf(newValue));
        });

        kaydet.setOnClickListener(v -> {
            db.tohumDuzenle(id, tohum.getText().toString(), Integer.parseInt(miktar.getText().toString()));
            finish();
        });
    }
}
