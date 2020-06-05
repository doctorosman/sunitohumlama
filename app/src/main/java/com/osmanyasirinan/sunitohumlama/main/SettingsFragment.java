package com.osmanyasirinan.sunitohumlama.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.database.Database;

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
            public void onClick(View v) {
                db.importrecords();
            }
        });

        exportb = v.findViewById(R.id.exportbutton);

        exportb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.export();
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
}
