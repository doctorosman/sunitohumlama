package com.osmanyasirinan.sunitohumlama;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {

    // Rows for filter method in Database class
    public static String[] rFFM = {"sahip", "esgal", "tohum", "koy", "tarih"};

    public static String putZeros(int a){
        if (String.valueOf(a).length() == 1){
            return "0" + a;
        }else {
            return a + "";
        }
    }

    public static TextWatcher watcher(final ImageView image, final int oldID, final int newID){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                image.setImageResource(newID);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(""))
                    image.setImageResource(oldID);
                else
                    image.setImageResource(newID);
            }
        };
    }

    public static AdapterView.OnItemSelectedListener itemSelectedListener(final ImageView img, final int oldID, final int newID){
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

                if (position > 0)
                    img.setImageResource(newID);
                else
                    img.setImageResource(oldID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                img.setImageResource(oldID);
            }
        };
    }

    public static String getFrom(String from, int part){
        switch (part) {
            case 0:
                return from.charAt(0) + "" + from.charAt(1);

            case 1:
                return from.charAt(3) + "" + from.charAt(4);

            default:
                return from.charAt(6) + "" + from.charAt(7) + from.charAt(8) + from.charAt(9);
        }
    }

    public static String getAy(int b) {
        switch (b){
            case 1:
                return Resources.getSystem().getString(R.string.m1);

            case 2:
                return Resources.getSystem().getString(R.string.m2);

            case 3:
                return Resources.getSystem().getString(R.string.m3);

            case 4:
                return Resources.getSystem().getString(R.string.m4);

            case 5:
                return Resources.getSystem().getString(R.string.m5);

            case 6:
                return Resources.getSystem().getString(R.string.m6);

            case 7:
                return Resources.getSystem().getString(R.string.m7);

            case 8:
                return Resources.getSystem().getString(R.string.m8);

            case 9:
                return Resources.getSystem().getString(R.string.m9);

            case 10:
                return Resources.getSystem().getString(R.string.m10);

            case 11:
                return Resources.getSystem().getString(R.string.m11);

            case 12:
                return Resources.getSystem().getString(R.string.m12);

            default:
                return null;
        }
    }

    public static void cleanPrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    public static Date zeroizeTime(Date given) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(given.getTime() * 1000L);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return new Date(c.getTimeInMillis() / 1000L);
    }

}
