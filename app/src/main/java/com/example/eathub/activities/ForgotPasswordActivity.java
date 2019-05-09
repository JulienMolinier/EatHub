package com.example.eathub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eathub.R;
import com.example.eathub.fragments.popupdialog.PasswordRestoredDialogFragment;
import com.example.eathub.models.ForgotPasswordModel;

public class ForgotPasswordActivity extends AppCompatActivity
        implements PasswordRestoredDialogFragment.PasswordRestoredDialogListener {

    @Override
    public void onPasswordRestoredDialogPositiveClick(DialogFragment dialog) {
        dialog.dismiss();
        Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        Button enterEmailButton = findViewById(R.id.enterButtonOnForgotPasswordPage);

        enterEmailButton.setOnClickListener(view -> {

            TextView errorMessage = findViewById(R.id.errorMessageOnForgotPasswordPage);

            EditText emailField = findViewById(R.id.emailFieldOnForgotPasswordPage);
            String email = emailField.getText().toString();

            ForgotPasswordModel model = new ForgotPasswordModel(email);
            if (model.emailIsValid()) {
                errorMessage.setVisibility(View.INVISIBLE);
                PasswordRestoredDialogFragment passwordRestoredDialog = new PasswordRestoredDialogFragment();
                passwordRestoredDialog.show(getSupportFragmentManager(), "Restoring password");
            } else {
                errorMessage.setText(getString(R.string.forgotPasswordError));
                errorMessage.setVisibility(View.VISIBLE);
            }
        });
    }
}
