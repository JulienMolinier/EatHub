package com.example.eathub.fragments.profile;

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
import com.example.eathub.models.ProfileModel;

public class ProfileFragment extends Fragment {
    private View view;
    private ProfileModel profileModel;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile, container, false);

        BottomNavigationView navigation = view.findViewById(R.id.navigation);

        if (savedInstanceState != null)
            profileModel = savedInstanceState.getParcelable("connectedProfile");
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ProfileDetailsFragment defaultFragment = new ProfileDetailsFragment();
        defaultFragment.setProfile(profileModel);
        showFragment(defaultFragment);

        return view;
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contain, fragment)
                .commit();
    }

    public void setProfile(ProfileModel profile) {
        this.profileModel = profile;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state
        savedInstanceState.putParcelable("connectedProfile", profileModel);
        super.onSaveInstanceState(savedInstanceState);

    }
}
