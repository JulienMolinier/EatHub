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
import com.example.eathub.models.databases.ProfileDatabase;

import java.time.LocalDate;

public class ProfileStatsFragment extends Fragment {

    private View view;
    private ProfileModel profileModel;
    private TextView restVis;
    private ProgressBar caloriesBar;
    private ProgressBar budgetBar;
    private Spinner spinner;

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

        this.profileModel = ProfileDatabase.getProfile("mm.durand@gmail.com");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.choice_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LocalDate today = LocalDate.now();
                spinnerChoice = position;
                switch (position) {
                    case 0:
                        numberOfDay = 1;
                        break;
                    case 1:
                        numberOfDay = 7;
                        break;
                    case 2:
                        numberOfDay = today.lengthOfMonth();
                        break;
                    case 3:
                        numberOfDay = today.lengthOfYear();
                        break;
                    default:
                        numberOfDay = 1;
                        break;
                }
                profileModel.computeValues(numberOfDay);
                profileModel.setHistory(spinnerChoice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(0);
                spinnerChoice = 0;
                profileModel.computeValues(numberOfDay);
                profileModel.setHistory(spinnerChoice);
            }

        });

        caloriesBar.setProgress((int) this.profileModel.caloriesPercentageProperty());
        budgetBar.setProgress((int) this.profileModel.spendPercentageProperty());
        restVis.setText(Integer.toString(this.profileModel.visitNumberProperty()));

        return view;
    }

}
