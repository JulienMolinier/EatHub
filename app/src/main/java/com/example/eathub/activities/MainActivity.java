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

public class MainActivity extends AppCompatActivity {
    private FragmentAdapter fragAdapter;
    private ViewPager vwPager;
    private SearchView search;
    private ProfileModel connectedProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent myIntent = getIntent();
        connectedProfile = myIntent.getParcelableExtra("userprofile");
        System.out.println("L'user connecté est" + connectedProfile);

        fragAdapter = new FragmentAdapter(getSupportFragmentManager());

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

    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());

        FeedFragment feedFragment = new FeedFragment();
        feedFragment.setProfile(connectedProfile);
        adapter.addFragment(feedFragment, "Feed");

        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setProfile(connectedProfile);
        adapter.addFragment(profileFragment, "Profile");

        viewPager.setAdapter(adapter);
    }
}
