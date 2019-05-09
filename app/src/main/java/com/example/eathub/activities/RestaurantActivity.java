package com.example.eathub.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.eathub.R;
import com.example.eathub.fragments.restaurant.RestaurantCommentsFragment;
import com.example.eathub.fragments.restaurant.RestaurantMapFragment;
import com.example.eathub.fragments.restaurant.RestaurantProfileFragment;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.RestaurantModel;

public class RestaurantActivity extends AppCompatActivity {

    private RestaurantModel theRestaurant;
    private ProfileModel profileModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigationRestaurant_comments:
                    RestaurantCommentsFragment restaurantComments = new RestaurantCommentsFragment();
                    restaurantComments.setRestaurantModel(theRestaurant);
                    restaurantComments.setProfileModel(profileModel);
                    showFragment(restaurantComments);
                    return true;
                case R.id.navigationRestaurant_map:

                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // permission not accepted
                    } else {
                        RestaurantMapFragment restaurantMap = new RestaurantMapFragment();
                        restaurantMap.setTheRestaurant(theRestaurant);
                        showFragment(restaurantMap);
                        return true;
                    }

                case R.id.navigationRestaurant_profile:
                    RestaurantProfileFragment restaurantProfile = new RestaurantProfileFragment();
                    restaurantProfile.setRestaurantModel(theRestaurant);
                    restaurantProfile.setProfileModel(profileModel);
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
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET}, 1);


        Intent intent = getIntent();
        theRestaurant = intent.getParcelableExtra("currentRestaurant");
        profileModel = intent.getParcelableExtra("currentProfile");
        if (savedInstanceState != null) {
            theRestaurant = savedInstanceState.getParcelable("currentRestaurant");
            profileModel = savedInstanceState.getParcelable("currentProfile");
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        RestaurantProfileFragment restaurantProfile = new RestaurantProfileFragment();
        restaurantProfile.setRestaurantModel(theRestaurant);
        restaurantProfile.setProfileModel(profileModel);
        showFragment(restaurantProfile);

    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contain, fragment)
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state
        savedInstanceState.putParcelable("currentRestaurant", theRestaurant);
        savedInstanceState.putParcelable("currentProfile", profileModel);
        super.onSaveInstanceState(savedInstanceState);

    }
}
