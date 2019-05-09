package com.example.eathub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.eathub.R;
import com.example.eathub.adapters.RestaurantRVAdapter;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.RestaurantModel;
import com.example.eathub.models.databases.RestaurantDatabase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SearchPageActivity extends AppCompatActivity {

    public String search;
    public TextView text;
    public RecyclerView listRestaurant;

    public boolean rate;
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
        if (savedInstanceState != null)
            search = savedInstanceState.getString("currentSearch");
        Bundle bundle = new Bundle();
        bundle.putString("params", search);

        searchView = findViewById(R.id.search);

        profile = (ProfileModel) intent.getParcelableExtra("userprofile");
        if (savedInstanceState != null)
            profile = savedInstanceState.getParcelable("connectedProfile");

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

    }

    public void getRestaurantList(Bundle savedInstanceState) {
        // recuperation liste des restaurants
        listRestaurant = findViewById(R.id.listRestaurant);
        if (search != null) {
            filterRestaurants = RestaurantDatabase.getRestaurantsBySearch(search);
            if (savedInstanceState != null)
                filterRestaurants = savedInstanceState.getParcelableArrayList("filterRestaurants");
            restaurantAdapter = new RestaurantRVAdapter(this, filterRestaurants, profile);
            listRestaurant.setAdapter(restaurantAdapter);
        }
    }

    public void addListenerOnCheckBox(View v) {

        boolean checked = ((CheckBox) v).isChecked();

        switch (v.getId()) {
            case R.id.checkBox10:
                if (checked){
                    isPrice10 = true;
                }
                else {
                    isPrice10 = false;
                }
                break;

            case R.id.checkBox20:
                if(checked){
                    isPrice20 = true;
                }
                else {
                    isPrice20 = false;
                }
                break;

            case R.id.checkBox30:
                if(checked){
                    isPrice30 = true;
                }
                else {
                    isPrice30 = false;
                }
                break;

            case R.id.checkBoxRate:
                if(checked){
                    rate = true;
                }
                else {
                    rate = false;
                }
                break;
        }
        buildRestaurantList(search, isPrice10, isPrice20, isPrice30, rate);
        restaurantAdapter.notifyDataSetChanged();

    }

    public void buildRestaurantList(String searchQuery, boolean button10, boolean button10To20, boolean button20,
                                    boolean buttonHighestRate) {

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

        if (buttonHighestRate) {
            filterRestaurants.sort(Comparator.comparing(RestaurantModel::getRating).reversed());
        }

        if(!button10 && !button10To20 && !button20 && !buttonHighestRate){
            for(RestaurantModel restaurant : RestaurantDatabase.getRestaurantsBySearch(searchQuery)){
                filterRestaurants.add(restaurant);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state
        savedInstanceState.putString("currentSearch", search);
        savedInstanceState.putParcelable("connectedProfile", profile);
        savedInstanceState.putParcelableArrayList("filterRestaurants", (ArrayList)filterRestaurants);

        super.onSaveInstanceState(savedInstanceState);

    }
}
