package com.example.eathub.activities;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.eathub.R;
import com.example.eathub.fragments.restaurant.RestaurantCommentsFragment;
import com.example.eathub.fragments.restaurant.RestaurantMapFragment;
import com.example.eathub.fragments.restaurant.RestaurantProfileFragment;
import com.example.eathub.models.RestaurantModel;

public class RestaurantActivity extends AppCompatActivity {

    private RestaurantModel theRestaurant;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigationRestaurant_comments:
                    RestaurantCommentsFragment restaurantComments = new RestaurantCommentsFragment();
                    restaurantComments.setTheRestaurant(theRestaurant);
                    showFragment(restaurantComments);
                    return true;
                case R.id.navigationRestaurant_map:
                    RestaurantMapFragment restaurantMap = new RestaurantMapFragment();
                    restaurantMap.setTheRestaurant(theRestaurant);
                    showFragment(restaurantMap);
                    return true;
                case R.id.navigationRestaurant_profile:
                    RestaurantProfileFragment restaurantProfile = new RestaurantProfileFragment();
                    restaurantProfile.setTheRestaurant(theRestaurant);
                    showFragment(restaurantProfile);
                    return true;
            }
            return false;
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET}, 1);


        Intent intent = getIntent();
        theRestaurant = intent.getParcelableExtra("restaurantpicked");

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        RestaurantProfileFragment restaurantProfile = new RestaurantProfileFragment();
        restaurantProfile.setTheRestaurant(theRestaurant);
        showFragment(restaurantProfile);

    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contain, fragment)
                .commit();
    }
}
