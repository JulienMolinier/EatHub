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
    private TextView detailsList;
    private CircleImageView profilePic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profiledetails, container, false);
        detailsList = view.findViewById(R.id.detailsList);
        profilePic = view.findViewById(R.id.profilePic);

        if (savedInstanceState != null)
            profileModel = savedInstanceState.getParcelable("connectedProfile");

        profilePic.setImageResource(view.getResources()
                .getIdentifier(this.profileModel.getName().replaceAll(" ", "")
                                .replaceAll("-", "").toLowerCase(), "drawable",
                        view.getContext().getPackageName()));

        this.profileModel.getProfileDetailsList().forEach(s -> detailsList.append(s + "\n"));
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
