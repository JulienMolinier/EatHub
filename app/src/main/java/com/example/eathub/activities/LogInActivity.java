package com.example.eathub.activities;

import com.example.eathub.R;
import com.example.eathub.models.LogInModel;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.databases.ProfileDatabase;
import com.example.eathub.models.databases.ProfilesFactory;
import com.example.eathub.models.databases.RestaurantDatabase;
import com.example.eathub.models.databases.RestaurantsFactory;
import com.example.eathub.models.databases.VisitDatabase;
import com.example.eathub.models.databases.VisitsFactory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



/**
 * @author Lydia BARAUKOVA
 */
public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        RestaurantDatabase DbResto = new RestaurantDatabase();
        ProfileDatabase DbProfile = new ProfileDatabase();
        VisitDatabase DbVisit = new VisitDatabase();
        RestaurantsFactory.createRestaurantsList(getResources().openRawResource(R.raw.restaurants));
        ProfilesFactory.createProfilesList(getResources().openRawResource(R.raw.profiles));
        VisitsFactory.createVisitList(getResources().openRawResource(R.raw.visits));
        setContentView(R.layout.login);



        Button loginButton = findViewById(R.id.login);
        TextView signupLink = findViewById(R.id.signup);
        TextView forgotPasswordLink = findViewById(R.id.forgotPassword);



        signupLink.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        });



        forgotPasswordLink.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
            startActivity(intent);
        });



        loginButton.setOnClickListener(view -> {
            EditText emailField = findViewById(R.id.email);
            String email = emailField.getText().toString();

            EditText passwordField = findViewById(R.id.password);
            String password = passwordField.getText().toString();

            TextView errorMessage = findViewById(R.id.errorMessage);

            LogInModel model = new LogInModel(email, password);

            if (model.correctEmail()) {
                if (model.correctPassword()) {
                    errorMessage.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    ProfileModel profile = ProfileDatabase.getProfile(email);
                    System.out.println("Le profil renvoyé est"+profile.getName());
                    intent.putExtra("userprofile",profile);
                    startActivity(intent);
                } else {
                    errorMessage.setText(getString(R.string.logInErrorBadPassword));
                    errorMessage.setVisibility(View.VISIBLE);
                }
            } else  {
                errorMessage.setText(getString(R.string.logInErrorBadEmail));
                errorMessage.setVisibility(View.VISIBLE);
            }
        });
    }
}
