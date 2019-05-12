package com.example.eathub.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.example.eathub.R;
import com.example.eathub.adapters.FriendRVAdapter;
import com.example.eathub.adapters.FriendSpinnerAdapter;
import com.example.eathub.adapters.RestaurantRVAdapter;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.RestaurantModel;
import com.example.eathub.models.VisitModel;
import com.example.eathub.models.databases.DatabaseHandler;
import com.example.eathub.models.databases.ProfileDatabase;
import com.example.eathub.models.databases.VisitDatabase;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {
    private View view;
    private RecyclerView friendRV;
    private RecyclerView feedRV;
    private ProfileModel profile;
    private List<RestaurantModel> restaurantList;
    private List<ProfileModel> friends;
    private boolean shared;
    private boolean visited;
    private Button filterFeed;
    private Button addFriend;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.feed, container, false);
        filterFeed = view.findViewById(R.id.filterFeed);
        addFriend = view.findViewById(R.id.addFriend);
        friendRV = view.findViewById(R.id.friendRV);
        feedRV = view.findViewById(R.id.feedRV);

        shared = true;
        visited = true;

        if (savedInstanceState != null){
            profile = savedInstanceState.getParcelable("currentProfile");
            visited = savedInstanceState.getBoolean("visitedbool");
            shared = savedInstanceState.getBoolean("sharedbool");
            filterFeed.setText(savedInstanceState.getString("filterFeed"));
        }

        restaurantList = new ArrayList<>();
        friends = new ArrayList<>();
        buildFriendsList();
        buildFeedList();
        FriendRVAdapter friendRVAdapter = new FriendRVAdapter(this.getContext(), friends);
        friendRV.setAdapter(friendRVAdapter);
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
        Dialog popup = new Dialog(getContext());
        addFriend.setOnClickListener((View v) -> {
            popup.setContentView(R.layout.add_friend_popup);
            Spinner addFriendSpinner = popup.findViewById(R.id.addFriendSpinner);
            Button addFriendButton = popup.findViewById(R.id.addFriendButton);
            Button cancelButton = popup.findViewById(R.id.cancelButton);
            addFriendSpinner.setAdapter(new FriendSpinnerAdapter(getContext(),
                    R.layout.add_friend_spinner, buildUserList()));
            cancelButton.setOnClickListener((View v1) -> popup.dismiss());
            addFriendButton.setOnClickListener((View v2) -> {
                profile.addFriend(((ProfileModel) addFriendSpinner.getSelectedItem()).getId());
                DatabaseHandler.addFriendToDB(profile.getId(),
                        ((ProfileModel) addFriendSpinner.getSelectedItem()).getId());
                buildFriendsList();
                buildFeedList();
                friendRVAdapter.notifyDataSetChanged();
                feedadapter.notifyDataSetChanged();
                popup.dismiss();
            });
            popup.show();
        });
        return view;
    }

    private void buildFriendsList() {
        friends.clear();
        this.friends.addAll(profile.getFriendsProfiles());
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state
        savedInstanceState.putBoolean("visitedbool",visited);
        savedInstanceState.putBoolean("sharedbool",shared);
        savedInstanceState.putString("filterFeed", filterFeed.getText().toString());
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

    private List<ProfileModel> buildUserList() {
        List<ProfileModel> otherUsers = new ArrayList<>();
        ProfileDatabase.getAllProfiles().forEach(pM -> {
            if (profile.getId() != pM.getId() && !profile.getFriendList().contains(pM.getId() - 1)) {
                otherUsers.add(pM);
            }
        });
        return otherUsers;
    }


    public void setProfile(ProfileModel profile) {
        this.profile = profile;
    }

}
