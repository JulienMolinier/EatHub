package com.example.eathub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.eathub.R;
import com.example.eathub.fragments.profile.ProfileChartsFragment;
import com.example.eathub.fragments.profile.ProfileDetailsFragment;
import com.example.eathub.fragments.profile.ProfileHistoryFragment;
import com.example.eathub.fragments.profile.ProfileStatsFragment;
import com.example.eathub.models.ProfileModel;

public class FriendProfileActivity extends AppCompatActivity {
    private ProfileModel profileModel;
    private BottomNavigationView navigation;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_details:
                    ProfileDetailsFragment profileDetailsFragment = new ProfileDetailsFragment();
                    profileDetailsFragment.setProfile(profileModel);
                    showFragment(profileDetailsFragment);
                    return true;
                case R.id.navigation_charts:
                    ProfileChartsFragment profileChartsFragment = new ProfileChartsFragment();
                    profileChartsFragment.setProfile(profileModel);
                    showFragment(profileChartsFragment);
                    return true;
                case R.id.navigation_stats:
                    ProfileStatsFragment profileStatsFragment = new ProfileStatsFragment();
                    profileStatsFragment.setProfile(profileModel);
                    showFragment(profileStatsFragment);
                    return true;
                case R.id.navigation_history:
                    ProfileHistoryFragment profileHistoryFragment = new ProfileHistoryFragment();
                    profileHistoryFragment.setProfile(profileModel);
                    showFragment(profileHistoryFragment);
                    return true;
            }
            return false;
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        Intent intent = getIntent();
        profileModel = intent.getParcelableExtra("currentProfile");
        if (savedInstanceState != null)
            profileModel = savedInstanceState.getParcelable("currentProfile");

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState != null){
            navigation.setSelectedItemId(savedInstanceState.getInt("fragSelected"));}
        else {

        ProfileDetailsFragment defaultFragment = new ProfileDetailsFragment();
        defaultFragment.setProfile(profileModel);
        showFragment(defaultFragment);}

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
        savedInstanceState.putInt("fragSelected", navigation.getSelectedItemId());
        savedInstanceState.putParcelable("currentProfile", profileModel);
        super.onSaveInstanceState(savedInstanceState);

    }
}
