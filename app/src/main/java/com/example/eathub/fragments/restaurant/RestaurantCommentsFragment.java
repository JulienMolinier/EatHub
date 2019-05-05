package com.example.eathub.fragments.restaurant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eathub.R;
import com.example.eathub.models.RestaurantModel;

public class RestaurantCommentsFragment extends Fragment {
    private View view;
    private RestaurantModel theRestaurant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.restaurantcomments, container, false);

        return view;
    }

    public void setTheRestaurant(RestaurantModel theRestaurant) {
        this.theRestaurant = theRestaurant;
    }
}
