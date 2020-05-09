package com.osmanyasirinan.sunitohumlama;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Objects;

/**
 * Created by ankititjunkies on 20/03/18.
 */

public class MonthYearPickerDialog extends DialogFragment {

    private static final int MAX_YEAR = 2099;
    private DatePickerDialog.OnDateSetListener listener;

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.month_year_picker_dialog, null);
        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);


        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setDisplayedValues(new String[]{"Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran", "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"});
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(2000);
        yearPicker.setMaxValue(year);
        yearPicker.setValue(year);

        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), 0);
                    }
                })
                .setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Objects.requireNonNull(MonthYearPickerDialog.this.getDialog()).cancel();
                    }
                });
        return builder.create();
    }
}