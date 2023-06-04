package com.ablsv.vremia;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePicker extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle sis)
    {
        Calendar DatePicker_Calendar = Calendar.getInstance();
        int year = DatePicker_Calendar.get(Calendar.YEAR);
        int month= DatePicker_Calendar.get(Calendar.MONTH);
        int dayofmonth = DatePicker_Calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener)getActivity(), year, month, dayofmonth);

    }
}
