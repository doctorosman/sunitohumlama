package com.osmanyasirinan.sunitohumlama.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.osmanyasirinan.sunitohumlama.R;

import java.util.ArrayList;

public class KayitListAdapter extends ArrayAdapter<Kayit> {

    private Context mContext;
    int mResource;

    /**
     * Default constructor for KayitListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public KayitListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Kayit> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String ay = getItem(position).getAy();
        String sayi = getItem(position).getSayi();

        Kayit kayit = new Kayit(ay, sayi);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvAy = convertView.findViewById(R.id.kayit_ay);
        TextView tvSayi = convertView.findViewById(R.id.kayit_tv);

        tvAy.setText(ay);
        tvSayi.setText(sayi);

        return convertView;
    }
}
