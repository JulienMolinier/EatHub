package com.example.eathub.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.eathub.R;
import com.example.eathub.adapters.FragmentAdapter;
import com.example.eathub.fragments.SearchPageFragment;

public class SearchPageActivity extends AppCompatActivity {

    private FragmentAdapter fragAdapter;
    private ViewPager vwPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragAdapter = new FragmentAdapter(getSupportFragmentManager());

        vwPager = findViewById(R.id.container);
        fragAdapter.addFragment(new SearchPageFragment(), "SearchPage");
        vwPager.setAdapter(fragAdapter);

    }
}
