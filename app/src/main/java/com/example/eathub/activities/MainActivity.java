package com.example.eathub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;

import com.example.eathub.R;
import com.example.eathub.adapters.FragmentAdapter;
import com.example.eathub.fragments.FeedFragment;
import com.example.eathub.fragments.profile.ProfileFragment;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.services.NotifyService;

public class MainActivity extends AppCompatActivity {

    private ProfileModel connectedProfile;
    private Bundle mState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vwPager;
        SearchView search;

        if (savedInstanceState != null)
            mState = savedInstanceState;

        final Intent myIntent = getIntent();
        connectedProfile = myIntent.getParcelableExtra("currentProfile");
        if (savedInstanceState != null)
            connectedProfile = savedInstanceState.getParcelable("currentProfile");

        // starting the service after getting the current user
        startNotificationService();

        vwPager = findViewById(R.id.container);
        setupViewPager(vwPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vwPager);

        search = findViewById(R.id.search);
        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), SearchPageActivity.class);
                intent.putExtra("data", query);
                intent.putExtra("currentProfile", connectedProfile);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        };
        search.setOnQueryTextListener(queryTextListener);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state
        savedInstanceState.putParcelable("currentProfile", connectedProfile);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());

        FeedFragment feedFragment = new FeedFragment();
        if (mState != null)
            connectedProfile = mState.getParcelable("currentProfile");
        feedFragment.setProfile(connectedProfile);
        adapter.addFragment(feedFragment, "Feed");

        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setProfile(connectedProfile);
        adapter.addFragment(profileFragment, "Profile");

        viewPager.setAdapter(adapter);
    }

    private void startNotificationService() {
        Intent intent = new Intent(this, NotifyService.class);
        intent.putExtra("currentProfile", connectedProfile);
        startService(intent);
    }
    private void stopNotificationService() {
        Intent intent = new Intent(this, NotifyService.NotifyServiceReceiver.class);
        intent.setAction(NotifyService.STOP_NOTIFY_SERVICE);
        sendBroadcast(intent);
    }
    private void sendNotification(String message) {
        Intent intent = new Intent(new Intent(this, NotifyService.NotifyServiceReceiver.class));
        intent.putExtra("message", message);
        intent.setAction(NotifyService.SEND_NOTIFICATION);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopNotificationService();
    }
}
