package com.example.eathub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.eathub.R;
import com.example.eathub.fragments.popupdialog.DatePickerDialogFragment;
import com.example.eathub.fragments.popupdialog.PrivacyPolicyDialogFragment;
import com.example.eathub.fragments.popupdialog.SignUpSuccessfulDialogFragment;
import com.example.eathub.models.SignUpModel;

/**
 * @author Lydia BARAUKOVA
 */
public class SignUpActivity extends AppCompatActivity implements PrivacyPolicyDialogFragment.PrivacyPolicyDialogListener,
        SignUpSuccessfulDialogFragment.SignUpSuccessfulDialogListener, DatePickerDialogFragment.DatePickerDialogListener {

    @Override
    public void onDatePicked(DatePickerDialogFragment dialog) {
        TextView birthDateField = findViewById(R.id.birthDatePickerOnSignUpPage);
        birthDateField.setText(dialog.getDate());
        dialog.dismiss();
    }

    @Override
    public void onPrivacyPolicyDialogPositiveClick(DialogFragment dialog) {
        CheckBox acceptPrivacyPolicy = findViewById(R.id.privacyPolicyCheckBoxOnSignUpPage);
        acceptPrivacyPolicy.post(() -> acceptPrivacyPolicy.setChecked(true));
    }

    @Override
    public void onPrivacyPolicyDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public void onSignUpSuccessfulDialogPositiveClick(DialogFragment dialog) {
        dialog.dismiss();
        Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        // filling the choice boxes

        Spinner genderChoiceBox = findViewById(R.id.genderChoiceBoxOnSignUpPage);
        ArrayAdapter<CharSequence> genderChoiceBoxAdapter = ArrayAdapter
                .createFromResource(this, R.array.genderArray, android.R.layout.simple_spinner_item);
        genderChoiceBoxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderChoiceBox.setAdapter(genderChoiceBoxAdapter);

        Spinner dietChoiceBox = findViewById(R.id.specialDietChoiceBoxOnSignUpPage);
        ArrayAdapter<CharSequence> dietChoiceBoxAdapter = ArrayAdapter
                .createFromResource(this, R.array.dietArray, android.R.layout.simple_spinner_item);
        dietChoiceBoxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dietChoiceBox.setAdapter(dietChoiceBoxAdapter);

        Spinner cuisineChoiceBox = findViewById(R.id.favoriteCuisineChoiceBoxOnSignUpPage);
        ArrayAdapter<CharSequence> cuisineChoiceBoxAdapter = ArrayAdapter
                .createFromResource(this, R.array.cuisineArray, android.R.layout.simple_spinner_item);
        cuisineChoiceBoxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisineChoiceBox.setAdapter(cuisineChoiceBoxAdapter);

        Spinner objectiveChoiceBox = findViewById(R.id.objectiveChoiceBoxOnSignUpPage);
        ArrayAdapter<CharSequence> objectiveChoiceBoxAdapter = ArrayAdapter
                .createFromResource(this, R.array.objectiveArray, android.R.layout.simple_spinner_item);
        objectiveChoiceBoxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        objectiveChoiceBox.setAdapter(objectiveChoiceBoxAdapter);

        // handling further events

        ImageView goBackIcon = findViewById(R.id.goBackArrowOnSignUpPage);
        TextView privacyPolicyLink = findViewById(R.id.privacyPolicyLinkOnSignUpPage);
        Button signUpButton = findViewById(R.id.signUpButton);
        TextView birthDateField = findViewById(R.id.birthDatePickerOnSignUpPage);

        birthDateField.setOnClickListener(view -> {
            DialogFragment newFragment = new DatePickerDialogFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");
        });

        goBackIcon.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
            startActivity(intent);
        });

        privacyPolicyLink.setOnClickListener(view -> {
             PrivacyPolicyDialogFragment privacyPolicyDialog = new PrivacyPolicyDialogFragment();
             privacyPolicyDialog.show(getSupportFragmentManager(), "Privacy policy");
        });

        signUpButton.setOnClickListener(view -> {

            // getting components
            EditText firstNameField = findViewById(R.id.firstNameFieldOnSignUpPage);
            EditText lastNameField = findViewById(R.id.lastNameFieldOnSignUpPage);
            EditText emailField = findViewById(R.id.emailFieldOnSignUpPage);
            EditText passwordField = findViewById(R.id.passwordFieldOnSignUpPage);
            EditText repeatPasswordField = findViewById(R.id.repeatPasswordFieldOnSignUpPage);
            EditText weightField = findViewById(R.id.weightFieldOnSignUpPage);
            EditText heightField = findViewById(R.id.heightFieldOnSignUpPage);
            EditText budgetField = findViewById(R.id.budgetFieldOnSignUpPage);
            CheckBox privacyPolicyCheckBox = findViewById(R.id.privacyPolicyCheckBoxOnSignUpPage);
            CheckBox notificationsCheckBox = findViewById(R.id.notificationsCheckBoxOnSignUpPage);

            // getting values from components
            String firstName = firstNameField.getText().toString();
            String lastName = lastNameField.getText().toString();
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();
            String passwordAgain = repeatPasswordField.getText().toString();
            String weight = weightField.getText().toString();
            String height = heightField.getText().toString();
            String budget = budgetField.getText().toString();
            boolean privacyPolicy = privacyPolicyCheckBox.isChecked();
            boolean notifications = notificationsCheckBox.isChecked();
            String gender = genderChoiceBox.getSelectedItem().toString();
            switch (gender) {
                case "male": gender = "M"; break;
                case "female": gender = "F"; break;
                default: break;
            }
            String specialDiet = dietChoiceBox.getSelectedItem().toString();
            String favoriteCuisine = cuisineChoiceBox.getSelectedItem().toString();
            String objective = objectiveChoiceBox.getSelectedItem().toString();
            String birthDate = birthDateField.getText().toString();

            // checking if the values correspond to the model
            TextView errorMessage = findViewById(R.id.errorMessageOnSignupPage);
            SignUpModel model = new SignUpModel(firstName, lastName, email, password, passwordAgain,
                    gender, weight, height, specialDiet, favoriteCuisine, budget, objective,
                    birthDate, privacyPolicy, notifications);
            if (model.obligatoryFieldsFilled()) {
                if (model.passwordsMatch()) {
                    if (model.privacyPolicyAccepted()) {
                        errorMessage.setText("");
                        errorMessage.setVisibility(View.INVISIBLE);
                        model.addUserToDatabase();
                        SignUpSuccessfulDialogFragment signUpSuccessful = new SignUpSuccessfulDialogFragment();
                        signUpSuccessful.show(getSupportFragmentManager(), "Sign up successful");
                    } else {
                        errorMessage.setText(getString(R.string.signUpErrorAcceptPrivacyPolicy));
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                } else {
                    errorMessage.setText(getString(R.string.signUpErrorPasswordsNotMatching));
                    errorMessage.setVisibility(View.VISIBLE);
                }
            } else {
                errorMessage.setText(getString(R.string.signUpErrorFillInObligatoryFields));
                errorMessage.setVisibility(View.VISIBLE);
            }
        });
    }
}
