package com.example.eathub.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.example.eathub.R;
import com.example.eathub.adapters.FragmentAdapter;
import com.example.eathub.fragments.FeedFragment;
import com.example.eathub.fragments.profile.ProfileFragment;
import com.example.eathub.models.databases.ProfileDatabase;
import com.example.eathub.models.databases.ProfilesFactory;
import com.example.eathub.models.databases.RestaurantDatabase;
import com.example.eathub.models.databases.RestaurantsFactory;
import com.example.eathub.models.databases.VisitDatabase;
import com.example.eathub.models.databases.VisitsFactory;

public class MainActivity extends AppCompatActivity {
    private FragmentAdapter fragAdapter;
    private ViewPager vwPager;
    private SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RestaurantDatabase DbResto = new RestaurantDatabase();
        ProfileDatabase DbProfile = new ProfileDatabase();
        VisitDatabase DbVisit = new VisitDatabase();
        RestaurantsFactory.createRestaurantsList(getResources().openRawResource(R.raw.restaurants));
        ProfilesFactory.createProfilesList(getResources().openRawResource(R.raw.profiles));
        VisitsFactory.createVisitList(getResources().openRawResource(R.raw.visits));

        setContentView(R.layout.activity_main);
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
        feedFragment.setProfile(ProfileDatabase.getAllProfiles().get(0));
        adapter.addFragment(feedFragment, "Feed");
        adapter.addFragment(new ProfileFragment(), "Profile");
        viewPager.setAdapter(adapter);
    }
}
