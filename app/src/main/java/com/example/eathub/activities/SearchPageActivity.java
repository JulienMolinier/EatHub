package com.example.eathub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eathub.R;
import com.example.eathub.adapters.RestaurantListAdapter;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.RestaurantModel;
import com.example.eathub.models.databases.RestaurantDatabase;

import java.util.Comparator;
import java.util.List;

public class SearchPageActivity extends AppCompatActivity {

    public String search;
    public TextView text;
    public ListView listRestaurant;

    public CheckBox hightestRate;
    public CheckBox price0to10;
    public CheckBox price10to20;
    public CheckBox price20;

    public boolean rate;
    public boolean isPrice10;
    public boolean isPrice20;
    public boolean isPrice30;

    public List<RestaurantModel> filterRestaurants;
    public List<RestaurantModel> restaurantsListBySearch;

    public RestaurantListAdapter restaurantAdapter;

    private ProfileModel profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page_layout);

        Intent intent = getIntent();
        search = intent.getStringExtra("data");
        Bundle bundle = new Bundle();
        bundle.putString("params", search);

        profile = (ProfileModel) intent.getParcelableExtra("userprofile");

        // initialisation bouton retour
        Button button = findViewById(R.id.buttonBackSearch);
        // initialisation checkbox
        hightestRate = findViewById(R.id.checkBoxRate);
        price0to10 = findViewById(R.id.checkBox10);
        price10to20 = findViewById(R.id.checkBox20);
        price20 = findViewById(R.id.checkBox30);

        // recuperation liste des restaurants
        listRestaurant = findViewById(R.id.listRestaurant);
        if (search != null) {
            restaurantsListBySearch = RestaurantDatabase.getRestaurantsBySearch(search);
            filterRestaurants = RestaurantDatabase.getRestaurantsBySearch(search);
            restaurantAdapter = new RestaurantListAdapter(getApplicationContext(), filterRestaurants, profile);
            listRestaurant.setAdapter(restaurantAdapter);
        }

        // retour en arriere avec le bouton back
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
}
