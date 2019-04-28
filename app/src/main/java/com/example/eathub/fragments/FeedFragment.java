package com.example.eathub.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eathub.R;
import com.example.eathub.adapters.RestaurantListAdapter;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.databases.ProfileDatabase;
import com.example.eathub.models.databases.RestaurantDatabase;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class FeedFragment extends Fragment {
    private View view;
    private ListView feedLV;
    private LinearLayout friendLL;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.feed,container,false);
        feedLV = view.findViewById(R.id.feedLV);
        feedLV.setAdapter(new RestaurantListAdapter(this.getContext(), RestaurantDatabase.getRestaurants()));
        buildFriendList();
        return view;
    }

    private void buildFriendList(){
        friendLL = view.findViewById(R.id.friendLL);
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        for(ProfileModel profile : ProfileDatabase.getAllProfiles()){
            RelativeLayout rL = (RelativeLayout) inflater.inflate(R.layout.friend_list_item, null);
            ImageView avatarFriend = rL.findViewById(R.id.avatarFriend);
            TextView nameFriend = rL.findViewById(R.id.nameFriend);
            avatarFriend.setImageResource(view.getResources().getIdentifier(profile.getName().replaceAll(" ", "").replaceAll("-","").toLowerCase(),"drawable", view.getContext().getPackageName()));
            nameFriend.setText(profile.getName());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 40, 0);
            friendLL.addView(rL, layoutParams);
        }
    }
    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    */
}
