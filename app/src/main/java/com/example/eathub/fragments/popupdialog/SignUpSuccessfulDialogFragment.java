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
public class SignUpSuccessfulDialogFragment extends DialogFragment {

    public interface SignUpSuccessfulDialogListener {
        void onSignUpSuccessfulDialogPositiveClick(DialogFragment dialog);
    }

    private SignUpSuccessfulDialogListener listener;

    @Override
    public void onAttach(Context context) { // on surcharge cette méthode pour instancier le listener
        super.onAttach(context);
        try {
            listener = (SignUpSuccessfulDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " doesn't implement SignUpSuccessfulDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.signUpSuccessfulMessage)
                .setTitle(R.string.signUpSuccessfulTitle)
                .setPositiveButton(android.R.string.ok, (dialog, id) ->
                        listener.onSignUpSuccessfulDialogPositiveClick(SignUpSuccessfulDialogFragment.this));
        return builder.create();
    }
}
