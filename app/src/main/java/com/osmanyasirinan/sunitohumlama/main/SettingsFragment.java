package com.osmanyasirinan.sunitohumlama.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.database.Database;
import com.osmanyasirinan.sunitohumlama.database.Utils;

import java.io.File;
import java.util.Set;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment {

    private View v;
    private Button importb, exportb;
    private Context context;
    private Button yardim;

    public SettingsFragment(Context context){ this.context = context; }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_settings, container, false);

        final Database db = new Database(context);

        importb = v.findViewById(R.id.importbutton);

        importb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                new MaterialFilePicker()
                        .withSupportFragment(SettingsFragment.this)
                        .withCloseMenu(true)
                        .withFilter(Pattern.compile(".*\\.(txt|tohumlama)$"))
                        .withFilterDirectories(false)
                        .withTitle("İçe aktar")
                        .start();
            }
        });

        exportb = v.findViewById(R.id.exportbutton);

        exportb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.exportr();
            }
        });

        yardim = v.findViewById(R.id.yardimb);
        yardim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(context).create();
                dialog.setTitle("YARDIM");
                dialog.setMessage("İçe aktarma yapmak için dahili depolamanızın Documents (Belgeler) klasörüne, kayıtlarınızın bulunduğu dosyayı import.tohumlama ismiyle kaydedin.");
                dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "TAMAM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            Database db = new Database(context);
            db.importr(new File(path));
        }
    }
}
