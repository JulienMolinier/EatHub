package com.example.eathub.fragments.popupdialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.eathub.R;

/**
 * @author Lydia BARAUKOVA
 */
public class DatePickerDialogFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public interface DatePickerDialogListener {
        void onDatePicked(DatePickerDialogFragment dialog);
    }

    private DatePickerDialogListener listener;
    private String date;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DatePickerDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " doesn't implement DatePickerDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        date = "";
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        date = year + "-" + month + "-" + day;
        listener.onDatePicked(DatePickerDialogFragment.this);
    }

    public String getDate() {
        return date;
    }
}

