package com.example.eathub.fragments.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.eathub.R;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.databases.ProfileDatabase;

public class ProfileStatsFragment extends Fragment {

    private View view;
    private ProfileModel profileModel;
    private TextView restVis;
    private ProgressBar caloriesBar;
    private ProgressBar budgetBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profilestats, container, false);
        caloriesBar = view.findViewById(R.id.caloriesBar);
        budgetBar = view.findViewById(R.id.budgetBar);
        restVis = view.findViewById(R.id.restVis);

        this.profileModel = ProfileDatabase.getProfile("mm.durand@gmail.com");
        this.profileModel.computeValues(31);
        caloriesBar.setProgress((int) this.profileModel.caloriesPercentageProperty());
        budgetBar.setProgress((int) this.profileModel.spendPercentageProperty());
        restVis.setText(Integer.toString(this.profileModel.visitNumberProperty()));

        return view;
    }
}
