package com.example.eathub.fragments.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.eathub.R;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.databases.ProfileDatabase;

public class ProfileDetailsFragment extends Fragment {

    private ProfileModel profileModel;
    private View view;
    private ListView detailsListView;
    private ImageView profilePic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profiledetails, container, false);
        detailsListView = view.findViewById(R.id.detailsListView);
        profilePic = view.findViewById(R.id.profilePic);

        this.profileModel = ProfileDatabase.getProfile("mm.durand@gmail.com");

        profilePic.setImageResource(view.getResources()
                .getIdentifier(this.profileModel.getName().replaceAll(" ", "")
                                .replaceAll("-", "").toLowerCase(), "drawable",
                        view.getContext().getPackageName()));

        this.detailsListView.setAdapter(new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, this.profileModel.getDetails()));
        return view;
    }
}
