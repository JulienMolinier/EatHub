package com.example.eathub.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.eathub.R;
import com.example.eathub.adapters.FragmentAdapter;
import com.example.eathub.fragments.FeedFragment;

public class MainActivity extends AppCompatActivity {
    private FragmentAdapter fragAdapter;
    private ViewPager vwPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragAdapter = new FragmentAdapter(getSupportFragmentManager());
        //ActivityList.buildList(getFilesDir());

        vwPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(vwPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vwPager);
    }


    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new FeedFragment(), "Feed");
        //adapter.addFragment(new ProfileFragment(), "Profile");
        viewPager.setAdapter(adapter);
    }
}
