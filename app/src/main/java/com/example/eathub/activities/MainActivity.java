package com.example.eathub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SearchView;

import com.example.eathub.R;
import com.example.eathub.adapters.FragmentAdapter;
import com.example.eathub.fragments.FeedFragment;
import com.example.eathub.fragments.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    private FragmentAdapter fragAdapter;
    private ViewPager vwPager;
    private SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragAdapter = new FragmentAdapter(getSupportFragmentManager());
        //ActivityList.buildList(getFilesDir());

        vwPager = findViewById(R.id.container);
        setupViewPager(vwPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vwPager);


        search = findViewById(R.id.search);
        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), SearchPageActivity.class);
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
        adapter.addFragment(new FeedFragment(), "Feed");
        adapter.addFragment(new ProfileFragment(), "Profile");
        viewPager.setAdapter(adapter);
    }
}
