package com.example.eathub.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.eathub.R;
import com.example.eathub.adapters.RestaurantListAdapter;
import com.example.eathub.models.databases.RestaurantDatabase;

public class FeedFragment extends Fragment {
    private View view;
    private ListView feedLV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.feed,container,false);
        feedLV = view.findViewById(R.id.feedLV);
        feedLV.setAdapter(new RestaurantListAdapter(this.getContext(), RestaurantDatabase.getRestaurants()));
        return view;
    }

    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    */
}
