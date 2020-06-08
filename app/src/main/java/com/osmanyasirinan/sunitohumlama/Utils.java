package com.osmanyasirinan.sunitohumlama;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;

public class Utils {

    public String putZeros(int a){
        if (String.valueOf(a).length() == 1){
            return "0" + a;
        }else {
            return a + "";
        }
    }

    public TextWatcher watcher(final ImageView image, final int oldID, final int newID){
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

    public String getFrom(String from, int part){
        switch (part) {
            case 0:
                return from.charAt(0) + "" + from.charAt(1);

            case 1:
                return from.charAt(3) + "" + from.charAt(4);

            default:
                return from.charAt(6) + "" + from.charAt(7) + from.charAt(8) + from.charAt(9);
        }
    }

    public String getAy(int b) {
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

}
