package com.example.eathub.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eathub.R;
import com.example.eathub.activities.RestaurantActivity;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.RestaurantModel;

import java.util.List;

public class RestaurantRVAdapter extends RecyclerView.Adapter<RestaurantFeedHolder> {

    private ProfileModel profile;
    private List<RestaurantModel> restaurantList;
    private Context context;

    public RestaurantRVAdapter(Context context, List<RestaurantModel> restaurants, ProfileModel profile){
        this.context = context;
        this.restaurantList = restaurants;
        this.profile = profile;
    }
    @NonNull
    @Override
    public RestaurantFeedHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.restaurant_feed_holder, viewGroup, false);



        return new RestaurantFeedHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantFeedHolder restaurantFeedHolder, int i) {
        RestaurantModel restaurant = restaurantList.get(i);
        restaurantFeedHolder.getCVImgRestaurant().setImageResource( context.getResources().getIdentifier(restaurant.getName()
                .replaceAll(" ", "").replaceAll("-", "").toLowerCase(), "drawable", context.getPackageName()));
        setupIndicator(restaurant,restaurantFeedHolder.getCVindicator());
        restaurantFeedHolder.getCVRestaurantName().setText(restaurant.getName());
        restaurantFeedHolder.getCVRestaurantDescription().setText(restaurant.getAddress());
        restaurantFeedHolder.getCVRestaurantPrice().setText("~"+String.valueOf(restaurant.getPrice())+"€");
        restaurantFeedHolder.getCVTypeRestaurant().setText(restaurant.getCulinaryFence().toString());

        restaurantFeedHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, RestaurantActivity.class);
                myIntent.putExtra("restaurantpicked", restaurantList.get(i));
                context.startActivity(myIntent);
            }
        });

        restaurantFeedHolder.getCVCall().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + restaurant.getPhoneNumber()));
                if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //no permission
                } else {
                    context.startActivity(callIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    private void setupIndicator(RestaurantModel restaurant, View indic){
        if(profile != null){
            if(restaurant.getPrice() <= profile.getBudget() && restaurant.getCulinaryFence() == profile.getCulinaryFence())
                indic.setBackgroundColor(this.context.getResources().getColor(R.color.indicatorGreen));
            else if(restaurant.getPrice() <= profile.getBudget())
                indic.setBackgroundColor(this.context.getResources().getColor(R.color.colorAccent));
            else
                indic.setBackgroundColor(this.context.getResources().getColor(R.color.indicatorRed));
        }
    }
}
