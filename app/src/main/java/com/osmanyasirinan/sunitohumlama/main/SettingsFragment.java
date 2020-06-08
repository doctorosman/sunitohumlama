package com.osmanyasirinan.sunitohumlama.main;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.Database;
import com.osmanyasirinan.sunitohumlama.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment {

    private static final int FILE_PICKER_REQUEST_CODE = 1;
    private View v;
    private Button importb, exportb;
    private Context context;

    public SettingsFragment(Context context){ this.context = context; }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_settings, container, false);
        // STATS

        ListView today = v.findViewById(R.id.today);
        ListView total = v.findViewById(R.id.total);

        Calendar calendar = Calendar.getInstance();
        int gun = calendar.get(Calendar.DAY_OF_MONTH);
        int ay = calendar.get(Calendar.MONTH) + 1;
        int yil = calendar.get(Calendar.YEAR);

        String currentDate = new Utils().putZeros(gun) + "." + new Utils().putZeros(ay) + "." + yil;
        final Database db = new Database(context);
        String iToday = db.getTarihKayit(currentDate) + "";

        String[] tod = {iToday};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.today, R.id.todaykayit, tod);
        today.setAdapter(adapter);

        String iTotal = db.getTotalCount() + "";
        String[] tot = {iTotal};
        ArrayAdapter<String> xadapter = new ArrayAdapter<>(context, R.layout.total, R.id.totalkayit, tot);
        total.setAdapter(xadapter);

        Refresh();

        // SETTINGS

        importb = v.findViewById(R.id.importbutton);

        importb.setOnClickListener(v -> new MaterialFilePicker()
                .withSupportFragment(SettingsFragment.this)
                .withCloseMenu(true)
                .withFilter(Pattern.compile(".*\\.(txt|tohumlama)$"))
                .withFilterDirectories(false)
                .withTitle("İçe aktar")
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .start());

        exportb = v.findViewById(R.id.exportbutton);

        exportb.setOnClickListener(v -> db.exportr());

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

    public void Refresh(){
        new myTask().execute();
    }

    public class myTask extends AsyncTask<Void, Void, Void> {

        ListView stats;
        KayitListAdapter adapter;
        Calendar c = Calendar.getInstance();

        @Override
        protected Void doInBackground(Void... voids) {
            Database db = new Database(context);
            final ArrayList<Kayit> klist = new ArrayList<>();
            for (int i = 1; i <= c.get(Calendar.MONTH) + 1; i++){
                int number = db.getAyKayit(i);

                if (number > 0){
                    Kayit r = new Kayit(new Utils().getAy(i), i, number + "");
                    klist.add(r);
                }
            }

            adapter = new KayitListAdapter(context, R.layout.month, klist);
            return null;
        }

        @Override
        protected void onPreExecute() {
            stats = v.findViewById(R.id.statslist);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            stats.setAdapter(adapter);
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
