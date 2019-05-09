package com.example.eathub.fragments.popupdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.eathub.R;

/**
 * @author Lydia BARAUKOVA
 */
public class PasswordRestoredDialogFragment extends DialogFragment {

    public interface PasswordRestoredDialogListener {
        void onPasswordRestoredDialogPositiveClick(DialogFragment dialog);
    }

    private PasswordRestoredDialogListener listener;

    @Override
    public void onAttach(Context context) { // on surcharge cette méthode pour instancier le listener
        super.onAttach(context);
        try {
            listener = (PasswordRestoredDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " doesn't implement PasswordRestoredDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.passwordRestoredMessage)
                .setTitle(R.string.passwordRestoredTitle)
                .setPositiveButton(android.R.string.ok, (dialog, id) ->
                        listener.onPasswordRestoredDialogPositiveClick(PasswordRestoredDialogFragment.this));
        return builder.create();
    }
}
