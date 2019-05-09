package com.example.eathub.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.eathub.R;

class RestaurantFeedHolder extends RecyclerView.ViewHolder {
    private ImageView CVImgRestaurant;
    private View CVindicator;
    private TextView CVRestaurantName;
    private TextView CVRestaurantDescription;
    private TextView CVTypeRestaurant;
    private TextView CVRestaurantPrice;
    private Button CVCall;
    private RatingBar ratingBar;


    RestaurantFeedHolder(@NonNull View itemView) {
        super(itemView);
        CVImgRestaurant = itemView.findViewById(R.id.CVimgRestaurant);
        CVindicator = itemView.findViewById(R.id.CVindicator);
        CVRestaurantName = itemView.findViewById(R.id.CVRestaurantName);
        CVRestaurantDescription = itemView.findViewById(R.id.CVRestaurantDescription);
        CVTypeRestaurant = itemView.findViewById(R.id.CVTypeRestaurant);
        CVRestaurantPrice = itemView.findViewById(R.id.CVRestaurantPrice);
        CVCall = itemView.findViewById(R.id.CVCall);
        ratingBar = itemView.findViewById(R.id.ratingBar2);
    }

    RatingBar getRatingBar() {
        return ratingBar;
    }

    ImageView getCVImgRestaurant() {
        return CVImgRestaurant;
    }

    View getCVindicator() {
        return CVindicator;
    }

    TextView getCVRestaurantName() {
        return CVRestaurantName;
    }

    TextView getCVRestaurantDescription() {
        return CVRestaurantDescription;
    }

    TextView getCVTypeRestaurant() {
        return CVTypeRestaurant;
    }

    TextView getCVRestaurantPrice() {
        return CVRestaurantPrice;
    }

    Button getCVCall() {
        return CVCall;
    }
}
