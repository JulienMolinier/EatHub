package com.example.eathub.fragments.restaurant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.eathub.R;
import com.example.eathub.fragments.profile.ProfileChartsFragment;
import com.example.eathub.fragments.profile.ProfileDetailsFragment;
import com.example.eathub.fragments.profile.ProfileStatsFragment;

public class RestaurantFragment extends Fragment {
    private View view;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigationRestaurant_comments:
                    showFragment(new RestaurantCommentsFragment());
                    return true;
                case R.id.navigationRestaurant_map:
                    showFragment(new RestaurantMapFragement());
                    return true;
                case R.id.navigationRestaurant_profile:
                    showFragment(new RestaurantProfileFragment());
                    return true;
            }
            return false;
        }

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.restaurant, container, false);

        BottomNavigationView navigation = view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        showFragment(new RestaurantProfileFragment());

        return view;
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contain, fragment)
                .commit();
    }
}
