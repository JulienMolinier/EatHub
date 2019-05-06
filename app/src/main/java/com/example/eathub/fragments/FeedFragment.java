package com.example.eathub.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eathub.R;
import com.example.eathub.activities.FriendProfileActivity;
import com.example.eathub.activities.RestaurantActivity;
import com.example.eathub.adapters.FriendRVAdapter;
import com.example.eathub.adapters.RestaurantListAdapter;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.RestaurantModel;
import com.example.eathub.models.VisitModel;
import com.example.eathub.models.databases.VisitDatabase;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class FeedFragment extends Fragment {
    private View view;
    private ListView feedLV;
    private LinearLayout friendLL;
    private RelativeLayout friendLayout;
    private RecyclerView friendRV;
    private ProfileModel profile;
    private ArrayList<RestaurantModel> restaurantList;
    private CheckBox shared;
    private CheckBox visited;
    private RestaurantListAdapter myRestaurantListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.feed, container, false);
        feedLV = view.findViewById(R.id.feedLV);
        friendRV = view.findViewById(R.id.friendRV);
        friendLayout = view.findViewById(R.id.friendLayout);
        shared = view.findViewById(R.id.shared);
        shared.setChecked(true);
        visited = view.findViewById(R.id.visited);
        visited.setChecked(true);
        restaurantList = new ArrayList<>();
        buildFeedList();
        myRestaurantListAdapter = new RestaurantListAdapter(this.getContext(), restaurantList, profile);
        feedLV.setAdapter(myRestaurantListAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);

        friendRV.setLayoutManager(layoutManager);
        friendRV.setAdapter(new FriendRVAdapter(this.getContext(), profile.getFriendList()));

        shared.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buildFeedList();
                myRestaurantListAdapter.notifyDataSetChanged();
            }
        });
        visited.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buildFeedList();
                myRestaurantListAdapter.notifyDataSetChanged();
            }
        });
        final Intent myIntent = new Intent(view.getContext(), RestaurantActivity.class);
        feedLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myIntent.putExtra("restaurantpicked", restaurantList.get(position));
                startActivity(myIntent);
            }
        });
        return view;
    }

    private void buildFeedList() {
        restaurantList.clear();
        if (shared.isChecked()) {
            System.out.println("On est dans le checked du shared");
            for (RestaurantModel restaurant : profile.getRestaurantsSharedByFriends()) {
                if (!restaurantList.contains(restaurant))
                    restaurantList.add(restaurant);
            }
        }
        if (visited.isChecked()) {
            for (VisitModel visit : VisitDatabase.getVisitsByProfile(this.profile)) {
                if (!restaurantList.contains(visit.getRestaurant()))
                    restaurantList.add(visit.getRestaurant());
            }
        }
        System.out.println("La liste vaut" + restaurantList.toString());

    }

    public void setProfile(ProfileModel profile) {
        this.profile = profile;
    }
    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    */
}
