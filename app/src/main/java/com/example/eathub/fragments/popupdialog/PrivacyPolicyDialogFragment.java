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
public class PrivacyPolicyDialogFragment extends DialogFragment {

    public interface PrivacyPolicyDialogListener {
        void onPrivacyPolicyDialogPositiveClick(DialogFragment dialog);
        void onPrivacyPolicyDialogNegativeClick(DialogFragment dialog);
    }

    PrivacyPolicyDialogListener listener;

    @Override
    public void onAttach(Context context) { // on surcharge cette méthode pour instancier le listener
        super.onAttach(context);
        try {
            listener = (PrivacyPolicyDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " doesn't implement PrivacyPolicyDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.privacyPolicyText)
                .setTitle(R.string.privacyPolicyTitle)
                .setPositiveButton(R.string.accept, (dialog, id) ->
                        listener.onPrivacyPolicyDialogPositiveClick(PrivacyPolicyDialogFragment.this))
                .setNegativeButton(android.R.string.cancel, (dialog, id) ->
                        listener.onPrivacyPolicyDialogNegativeClick(PrivacyPolicyDialogFragment.this));
        return builder.create();
    }
}