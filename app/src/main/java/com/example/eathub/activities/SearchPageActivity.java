package com.example.eathub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.eathub.R;
import com.example.eathub.adapters.RestaurantRVAdapter;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.RestaurantModel;
import com.example.eathub.models.databases.RestaurantDatabase;

import java.util.ArrayList;
import java.util.List;

public class SearchPageActivity extends AppCompatActivity {

    public String search;
    public TextView text;
    public RecyclerView listRestaurant;
    public Button buttonPrice;

    public boolean isPrice10;
    public boolean isPrice20;
    public boolean isPrice30;

    SearchView searchView;

    public List<RestaurantModel> filterRestaurants;

    public RestaurantRVAdapter restaurantAdapter;

    private ProfileModel profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        Intent intent = getIntent();
        search = intent.getStringExtra("data");
        profile = intent.getParcelableExtra("currentProfile");
        if (savedInstanceState != null)
            search = savedInstanceState.getString("currentSearch");
        Bundle bundle = new Bundle();
        bundle.putString("params", search);

        searchView = findViewById(R.id.search);

        if (savedInstanceState != null)
            profile = savedInstanceState.getParcelable("currentProfile");

        getRestaurantList(savedInstanceState);

        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search = query;
                getRestaurantList(savedInstanceState);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search = newText;
                getRestaurantList(savedInstanceState);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        filterList();


    }

    public void getRestaurantList(Bundle savedInstanceState) {
        listRestaurant = findViewById(R.id.listRestaurant);
        if (search != null) {
            filterRestaurants = RestaurantDatabase.getRestaurantsBySearch(search);
            if (savedInstanceState != null)
                filterRestaurants = savedInstanceState.getParcelableArrayList("filterRestaurants");
            restaurantAdapter = new RestaurantRVAdapter(this, filterRestaurants, profile);
            listRestaurant.setAdapter(restaurantAdapter);
        }
    }

    public void filterList() {
        buttonPrice = findViewById(R.id.filterPrice);
        buttonPrice.setOnClickListener((View v) -> {
            switch (buttonPrice.getText().toString()) {
                case "All":
                    isPrice10 = true;
                    buttonPrice.setText("0-10€");
                    break;
                case "0-10€":
                    isPrice10 = false;
                    isPrice20 = true;
                    buttonPrice.setText("10-20€");
                    break;
                case "10-20€":
                    isPrice20 = false;
                    isPrice30 = true;
                    buttonPrice.setText("20€ et plus");
                    break;
                case "20€ et plus":
                    isPrice10 = false;
                    isPrice20 = false;
                    isPrice30 = false;
                    buttonPrice.setText("All");
                    break;
            }
            buildRestaurantList(search, isPrice10, isPrice20, isPrice30);
            restaurantAdapter.notifyDataSetChanged();

        });

    }

    public void buildRestaurantList(String searchQuery, boolean button10, boolean button10To20, boolean button20) {

        filterRestaurants.clear();
        if (button10) {
            for (RestaurantModel restaurant : RestaurantDatabase.getRestaurantsBySearch(searchQuery)) {
                if (restaurant.getPrice() <= 10) {
                    if (!filterRestaurants.contains(restaurant)) {
                        filterRestaurants.add(restaurant);
                    }
                }
            }
        }
        if (button10To20) {
            for (RestaurantModel restaurant : RestaurantDatabase.getRestaurantsBySearch(searchQuery)) {
                if (restaurant.getPrice() > 10 && restaurant.getPrice() < 20) {
                    if (!filterRestaurants.contains(restaurant)) {
                        filterRestaurants.add(restaurant);
                    }
                }
            }
        }
        if (button20) {
            for (RestaurantModel restaurant : RestaurantDatabase.getRestaurantsBySearch(searchQuery)) {
                if (restaurant.getPrice() >= 20) {
                    if (!filterRestaurants.contains(restaurant)) {
                        filterRestaurants.add(restaurant);
                    }
                }
            }
        }

        if (!button10 && !button10To20 && !button20) {
            for (RestaurantModel restaurant : RestaurantDatabase.getRestaurantsBySearch(searchQuery)) {
                filterRestaurants.add(restaurant);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state
        savedInstanceState.putString("currentSearch", search);
        savedInstanceState.putParcelable("currentProfile", profile);
        savedInstanceState.putParcelableArrayList("filterRestaurants", (ArrayList) filterRestaurants);

        super.onSaveInstanceState(savedInstanceState);

    }
}
