package com.example.eathub.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.eathub.R;

class HistoryHolder extends RecyclerView.ViewHolder {
    private ImageView ImgRestaurant;
    private TextView RestaurantName;
    private TextView Date;
    private TextView Price;
    private TextView Calories;
    private RatingBar ratingBar;

    HistoryHolder(@NonNull View itemView) {
        super(itemView);
        ImgRestaurant = itemView.findViewById(R.id.ImgRestaurant);
        RestaurantName = itemView.findViewById(R.id.RestaurantName);
        Date = itemView.findViewById(R.id.Date);
        Price = itemView.findViewById(R.id.Price);
        Calories = itemView.findViewById(R.id.Calories);
        ratingBar = itemView.findViewById(R.id.ratingBar);
    }

    ImageView getImgRestaurant() {
        return ImgRestaurant;
    }

    TextView getRestaurantName() {
        return RestaurantName;
    }

    RatingBar getRatingBar() {
        return ratingBar;
    }

    TextView getDate() {
        return Date;
    }

    TextView getPrice() {
        return Price;
    }

    TextView getCalories() {
        return Calories;
    }
}
