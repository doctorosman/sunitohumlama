package com.osmanyasirinan.sunitohumlama.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.osmanyasirinan.sunitohumlama.R;

public class StocksFragment extends Fragment {

    private View v;
    private Context context;

    public StocksFragment(Context context){ this.context = context; }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_stocks, container, null);

        return v;
    }
}
