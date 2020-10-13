package com.osmanyasirinan.sunitohumlama.main;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.osmanyasirinan.sunitohumlama.Database;
import com.osmanyasirinan.sunitohumlama.R;
import com.osmanyasirinan.sunitohumlama.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

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

        /*Calendar calendar = Calendar.getInstance();
        int gun = calendar.get(Calendar.DAY_OF_MONTH);
        int ay = calendar.get(Calendar.MONTH) + 1;
        int yil = calendar.get(Calendar.YEAR);

        String currentDate = new Utils().putZeros(gun) + "." + new Utils().putZeros(ay) + "." + yil;
        Database db = new Database(context);
        String iToday = db.getTarihKayit(currentDate) + "";

        String[] tod = {iToday};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.today, R.id.todaykayit, tod);
        today.setAdapter(adapter);

        String iTotal = db.getTotalCount() + "";
        String[] tot = {iTotal};
        ArrayAdapter<String> xadapter = new ArrayAdapter<>(context, R.layout.total, R.id.totalkayit, tot);
        total.setAdapter(xadapter);

        Refresh(); */
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
                /*int number = db.getAyKayit(i);

                if (number > 0){
                    Kayit r = new Kayit(new Utils().getAy(i), i, number + "");
                    klist.add(r);
                }*/
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