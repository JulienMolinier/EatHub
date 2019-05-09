package com.example.eathub.fragments.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.eathub.R;
import com.example.eathub.adapters.HistoryRVAdapter;
import com.example.eathub.models.ProfileModel;


public class ProfileHistoryFragment extends Fragment {

    private ProfileModel profileModel;
    private View view;
    private RecyclerView historyView;
    private HistoryRVAdapter historyAdapter;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private int spinnerChoice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profilehistory, container, false);
        historyView = view.findViewById(R.id.historyView);

        spinner = view.findViewById(R.id.spinner);

        if (savedInstanceState != null)
            profileModel = savedInstanceState.getParcelable("currentProfile");

        adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.choice_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        spinnerChoice = position;
                        break;
                    case 1:
                        spinnerChoice = position;
                        break;
                    case 2:
                        spinnerChoice = position;
                        break;
                    case 3:
                        spinnerChoice = position;
                        break;
                }
                profileModel.computeValues(spinnerChoice);
                historyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerChoice = 0;
                profileModel.computeValues(spinnerChoice);
                historyAdapter.notifyDataSetChanged();
            }
        });

        historyAdapter = new HistoryRVAdapter(this.getContext(), this.profileModel.getHistory());
        historyView.setAdapter(historyAdapter);

        return view;
    }

    public void setProfile(ProfileModel profile) {
        this.profileModel = profile;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state
        savedInstanceState.putParcelable("currentProfile", profileModel);
        super.onSaveInstanceState(savedInstanceState);


    }
}
