package com.example.eathub.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.eathub.R;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.RestaurantModel;
import com.example.eathub.models.VisitModel;
import com.example.eathub.models.databases.DatabaseHandler;
import com.example.eathub.models.databases.VisitDatabase;

import java.time.LocalDate;

public class CommentActivity extends AppCompatActivity {
    private RestaurantModel restaurantModel;
    private ProfileModel profileModel;

    private EditText rateInput;
    private EditText commentInput;
    private EditText dateInput;
    private EditText priceInput;
    private EditText caloriesInput;
    private Button buttonAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcomment);
        Intent myIntent = getIntent();
        profileModel = myIntent.getParcelableExtra("currentProfile");
        restaurantModel = myIntent.getParcelableExtra("currentRestaurant");

        rateInput = findViewById(R.id.rateInput);
        commentInput = findViewById(R.id.commentInput);
        dateInput = findViewById(R.id.dateInput);
        caloriesInput = findViewById(R.id.caloriesInput);
        priceInput = findViewById(R.id.priceInput);
        buttonAdd = findViewById(R.id.commentButton);

        buttonAdd.setOnClickListener((View v) -> {
            if (rateInput.getText() != null && commentInput.getText() != null
                    && dateInput.getText() != null && caloriesInput.getText() != null) {
                VisitModel visitToAdd = new VisitModel(profileModel, restaurantModel,
                        LocalDate.parse(dateInput.getText().toString()),
                        Double.valueOf(caloriesInput.getText().toString()),
                        Double.valueOf(priceInput.getText().toString()),
                        commentInput.getText().toString(),
                        Double.valueOf(rateInput.getText().toString()));

                if (!DatabaseHandler.addVisitToDB(visitToAdd)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(R.string.error);
                    builder.setNeutralButton(R.string.ok, (dialog, which) -> {
                        finish();
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    VisitDatabase.getVisits().add(visitToAdd);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(R.string.done);
                    builder.setNeutralButton(R.string.ok, (dialog, which) -> {
                        finish();
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state
        savedInstanceState.putParcelable("currentRestaurant", restaurantModel);
        savedInstanceState.putParcelable("currentProfile", profileModel);
        super.onSaveInstanceState(savedInstanceState);

    }
}
