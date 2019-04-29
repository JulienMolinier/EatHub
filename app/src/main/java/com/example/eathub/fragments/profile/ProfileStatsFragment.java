package com.example.eathub.fragments.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.eathub.R;
import com.example.eathub.models.ProfileModel;

import java.time.LocalDate;

public class ProfileStatsFragment extends Fragment {

    private View view;
    private ProfileModel profileModel;
    private TextView restVis;
    private ProgressBar caloriesBar;
    private ProgressBar budgetBar;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private int numberOfDay;
    private int spinnerChoice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profilestats, container, false);
        caloriesBar = view.findViewById(R.id.caloriesBar);
        budgetBar = view.findViewById(R.id.budgetBar);
        restVis = view.findViewById(R.id.restVis);
        spinner = view.findViewById(R.id.spinner);

        adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.choice_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LocalDate today = LocalDate.now();
                switch (position) {
                    case 0:
                        numberOfDay = 1;
                        spinnerChoice = position;
                        break;
                    case 1:
                        numberOfDay = 7;
                        spinnerChoice = position;
                        break;
                    case 2:
                        numberOfDay = today.lengthOfMonth();
                        spinnerChoice = position;
                        break;
                    case 3:
                        numberOfDay = today.lengthOfYear();
                        spinnerChoice = position;
                        break;
                }
                profileModel.computeValues(numberOfDay, spinnerChoice);
                update();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                numberOfDay = 1;
                spinnerChoice = 0;
                profileModel.computeValues(numberOfDay, spinnerChoice);
                update();
            }
        });

        update();

        return view;
    }

    private void update() {
        restVis.setText(String.valueOf(this.profileModel.getVisitNumber()));
        caloriesBar.setProgress((int) (this.profileModel.getCaloriesConsumed() /
                (this.profileModel.getRequired() * this.numberOfDay) * 100));
        budgetBar.setProgress((int) (this.profileModel.getSpend() /
                (this.profileModel.getBudget() * this.numberOfDay) * 100));

    }

    public void setProfile(ProfileModel profile) {
        this.profileModel = profile;
    }

}
