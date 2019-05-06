package com.example.eathub.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
        shared = view.findViewById(R.id.shared);
        shared.setChecked(true);
        visited = view.findViewById(R.id.visited);
        visited.setChecked(true);
        restaurantList = new ArrayList<>();
        buildFeedList();
        myRestaurantListAdapter = new RestaurantListAdapter(this.getContext(), restaurantList, profile);
        feedLV.setAdapter(myRestaurantListAdapter);

        buildFriendList();
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

    private void buildFriendList() {
        friendLL = view.findViewById(R.id.friendLL);
        final Intent myIntent = new Intent(view.getContext(), FriendProfileActivity.class);
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        for (ProfileModel profile : profile.getFriendList()) {
            RelativeLayout rL = (RelativeLayout) inflater.inflate(R.layout.friend_list_item, null);
            rL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myIntent.putExtra("profilepicked", profile);
                    startActivity(myIntent);
                }
            });
            ImageView avatarFriend = rL.findViewById(R.id.avatarFriend);
            TextView nameFriend = rL.findViewById(R.id.nameFriend);
            avatarFriend.setImageResource(view.getResources().getIdentifier(profile.getName()
                    .replaceAll(" ", "").replaceAll("-", "").toLowerCase(), "drawable", view.getContext().getPackageName()));
            nameFriend.setText(profile.getName());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 40, 0);
            friendLL.addView(rL, layoutParams);
        }

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
