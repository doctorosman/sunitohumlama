package com.osmanyasirinan.sunitohumlama.main;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.osmanyasirinan.sunitohumlama.database.Database;
import com.osmanyasirinan.sunitohumlama.R;

import java.util.ArrayList;
import java.util.Calendar;

public class StatsFragment extends Fragment {

    private View v;
    private Context context;

    public StatsFragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_stats, container, false);
        ListView today = v.findViewById(R.id.today);
        ListView total = v.findViewById(R.id.total);

        Calendar calendar = Calendar.getInstance();
        int gun = calendar.get(Calendar.DAY_OF_MONTH);
        int ay = calendar.get(Calendar.MONTH) + 1;
        int yil = calendar.get(Calendar.YEAR);

        String currentDate = putZeros(gun) + "." + putZeros(ay) + "." + yil;
        Database db = new Database(context);
        String iToday = db.getTarihKayit(currentDate) + "";

        String[] tod = {iToday};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.today, R.id.todaykayit, tod);
        today.setAdapter(adapter);

        String iTotal = db.getTotalCount() + "";
        String[] tot = {iTotal};
        ArrayAdapter<String> xadapter = new ArrayAdapter<>(context, R.layout.total, R.id.totalkayit, tot);
        total.setAdapter(xadapter);

        Refresh();
        return v;
    }

    public void Refresh(){
        new myTask().execute();
    }

    public class myTask extends AsyncTask<Void, Void, Void>{

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
                    Kayit r = new Kayit(getAy(i), number + "");
                    klist.add(r);
                }
            }

            stats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(context, FilteredList.class);
                    i.putExtra("ay", klist.get(position).getAy());
                    startActivity(i);
                }
            });

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

    private String getAy(int b) {
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

    private String putZeros(int a){
        if (String.valueOf(a).length() == 1){
            return "0" + a;
        }else {
            return a + "";
        }
    }
}
