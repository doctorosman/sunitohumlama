package com.osmanyasirinan.sunitohumlama.tohum;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.osmanyasirinan.sunitohumlama.R;

import java.util.ArrayList;

public class TohumAdapter extends ArrayAdapter<Tohum> {

    private Context mContext;
    int mResource;

    public TohumAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Tohum> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String tohum = getItem(position).getIsim();
        int miktar = getItem(position).getMiktar();
        String miktarstr = String.valueOf(miktar);

        Tohum t = new Tohum(tohum, miktar);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvTohum = convertView.findViewById(R.id.tvtohumad);
        TextView tvMiktar = convertView.findViewById(R.id.tvtohummiktar);

        tvTohum.setText(tohum);
        tvMiktar.setText(miktarstr);

        return convertView;
    }

}
