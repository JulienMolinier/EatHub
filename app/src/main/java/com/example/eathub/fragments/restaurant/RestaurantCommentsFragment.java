package com.example.eathub.fragments.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.eathub.R;
import com.example.eathub.activities.CommentActivity;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.RestaurantModel;

public class RestaurantCommentsFragment extends Fragment {
    private View view;
    private ListView listComments;
    private ProfileModel profileModel;
    private RestaurantModel restaurantModel;
    private Button addACommentButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.restaurantcomments, container, false);
        listComments = view.findViewById(R.id.listComments);
        addACommentButton = view.findViewById(R.id.addACommentButton);
        if (savedInstanceState != null) {
            restaurantModel = savedInstanceState.getParcelable("currentRestaurant");
            profileModel = savedInstanceState.getParcelable("currentProfile");
        }

        addACommentButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(getContext(), CommentActivity.class);
            intent.putExtra("currentProfile", profileModel);
            intent.putExtra("currentRestaurant", restaurantModel);
            startActivity(intent);

        });

        return view;
    }

    public void setProfileModel(ProfileModel profileModel) {
        this.profileModel = profileModel;
    }

    public void setRestaurantModel(RestaurantModel restaurantModel) {
        this.restaurantModel = restaurantModel;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state
        savedInstanceState.putParcelable("currentRestaurant", restaurantModel);
        savedInstanceState.putParcelable("currentProfile", profileModel);

        super.onSaveInstanceState(savedInstanceState);

    }
}
