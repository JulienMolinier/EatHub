package com.example.eathub.fragments.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eathub.R;
import com.example.eathub.models.ProfileModel;

public class ProfileChartsFragment extends Fragment {
    private View view;
    private ProfileModel profileModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profilecharts, container, false);

        return view;
    }

    public void setProfile(ProfileModel profile) {
        this.profileModel = profile;
    }
}
