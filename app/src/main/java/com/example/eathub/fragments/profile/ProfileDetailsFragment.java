package com.example.eathub.fragments.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eathub.R;
import com.example.eathub.models.ProfileModel;
import com.github.abdularis.civ.CircleImageView;

public class ProfileDetailsFragment extends Fragment {

    private ProfileModel profileModel;
    private View view;
    private TextView profileName;
    private TextView valueAge;
    private TextView valueHeight;
    private TextView valueWeight;
    private TextView budgetvalue;
    private TextView dietvalue;
    private TextView fencevalue;
    private CircleImageView profilePic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profiledetails, container, false);
        profileName = view.findViewById(R.id.profileName);
        valueAge = view.findViewById(R.id.valueAge);
        valueHeight = view.findViewById(R.id.valueHeight);
        valueWeight = view.findViewById(R.id.valueWeight);
        budgetvalue = view.findViewById(R.id.budgetvalue);
        dietvalue = view.findViewById(R.id.dietvalue);
        fencevalue = view.findViewById(R.id.fencevalue);
        profilePic = view.findViewById(R.id.profilePic);

        if (savedInstanceState != null)
            profileModel = savedInstanceState.getParcelable("connectedProfile");

        profileName.setText(profileModel.getName());
        valueAge.setText(String.valueOf(profileModel.getAge()));
        valueHeight.setText(String.valueOf(profileModel.getHeight())+"m");
        valueWeight.setText(String.valueOf(profileModel.getWeight())+"kg");
        budgetvalue.setText(String.valueOf(profileModel.getBudget())+"€");
        dietvalue.setText(profileModel.getDiet().toString());
        fencevalue.setText((profileModel.getCulinaryFence() != null) ? profileModel.getCulinaryFence().toString() : "None");



        profilePic.setImageResource(view.getResources()
                .getIdentifier(this.profileModel.getName().replaceAll(" ", "")
                                .replaceAll("-", "").toLowerCase(), "drawable",
                        view.getContext().getPackageName()));

        return view;
    }

    public void setProfile(ProfileModel profile) {
        this.profileModel = profile;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state
        savedInstanceState.putParcelable("connectedProfile", profileModel);
        super.onSaveInstanceState(savedInstanceState);


    }
}
