package com.example.eathub.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.eathub.R;
import com.example.eathub.adapters.FriendRVAdapter;
import com.example.eathub.adapters.RestaurantRVAdapter;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.RestaurantModel;
import com.example.eathub.models.VisitModel;
import com.example.eathub.models.databases.VisitDatabase;

import java.util.ArrayList;

public class FeedFragment extends Fragment {
    private View view;
    private RecyclerView friendRV;
    private RecyclerView feedRV;
    private ProfileModel profile;
    private ArrayList<RestaurantModel> restaurantList;
    private boolean shared;
    private boolean visited;
    private Button filterFeed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.feed, container, false);
        filterFeed = view.findViewById(R.id.filterFeed);
        friendRV = view.findViewById(R.id.friendRV);
        feedRV = view.findViewById(R.id.feedRV);

        if (savedInstanceState != null)
            profile = savedInstanceState.getParcelable("currentProfile");

        shared = true;
        visited = true;
        restaurantList = new ArrayList<>();
        buildFeedList();

        friendRV.setAdapter(new FriendRVAdapter(this.getContext(), profile.getFriendList()));
        RestaurantRVAdapter feedadapter = new RestaurantRVAdapter(this.getContext(), restaurantList, profile);
        feedRV.setAdapter(feedadapter);

        filterFeed.setOnClickListener((View v) -> {
            switch (filterFeed.getText().toString()) {
                case "All":
                    visited = false;
                    shared = true;
                    filterFeed.setText("Shared");
                    break;
                case "Shared":
                    visited = true;
                    shared = false;
                    filterFeed.setText("Visited");
                    break;
                case "Visited":
                    visited = false;
                    shared = false;
                    filterFeed.setText("None");
                    break;
                case "None":
                    visited = true;
                    shared = true;
                    filterFeed.setText("All");
                    break;
            }
            buildFeedList();
            feedadapter.notifyDataSetChanged();

        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state
        savedInstanceState.putParcelable("currentProfile", profile);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void buildFeedList() {
        restaurantList.clear();
        if (shared) {
            for (RestaurantModel restaurant : profile.getRestaurantsSharedByFriends()) {
                if (!restaurantList.contains(restaurant))
                    restaurantList.add(restaurant);
            }
        }
        if (visited) {
            for (VisitModel visit : VisitDatabase.getVisitsByProfile(this.profile)) {
                if (!restaurantList.contains(visit.getRestaurant()))
                    restaurantList.add(visit.getRestaurant());
            }
        }
    }


    public void setProfile(ProfileModel profile) {
        this.profile = profile;
    }

}
