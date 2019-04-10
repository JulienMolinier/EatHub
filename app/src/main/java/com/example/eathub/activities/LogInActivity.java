package com.example.eathub.activities;

import com.example.eathub.R;
import com.example.eathub.models.LogInModel;

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
        setContentView(R.layout.login);



        Button loginButton = findViewById(R.id.login);
        TextView signupLink = findViewById(R.id.signup);
        TextView forgotPasswordLink = findViewById(R.id.forgotPassword);



        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                //startActivity(intent);
            }
        });



        forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                //startActivity(intent);
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                EditText emailField = findViewById(R.id.email);
                String email = emailField.getText().toString();

                EditText passwordField = findViewById(R.id.password);
                String password = passwordField.getText().toString();

                TextView errorMessage = findViewById(R.id.errorMessage);

                LogInModel model = new LogInModel(email, password);

                if (model.correctEmail()) {
                    if (model.correctPassword()) {
                        errorMessage.setVisibility(View.INVISIBLE);
                        //Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
                        //startActivity(intent);
                    } else {
                        errorMessage.setText("Incorrect password");
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                } else  {
                    errorMessage.setText("Incorrect e-mail");
                    errorMessage.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
