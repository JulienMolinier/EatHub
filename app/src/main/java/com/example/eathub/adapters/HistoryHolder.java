package com.example.eathub.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eathub.R;

public class HistoryHolder extends RecyclerView.ViewHolder {
    private ImageView ImgRestaurant;
    private TextView RestaurantName;
    private TextView Date;
    private TextView Price;
    private TextView Calories;


    public HistoryHolder(@NonNull View itemView) {
        super(itemView);
        ImgRestaurant = itemView.findViewById(R.id.ImgRestaurant);
        RestaurantName = itemView.findViewById(R.id.RestaurantName);
        Date = itemView.findViewById(R.id.Date);
        Price = itemView.findViewById(R.id.Price);
        Calories = itemView.findViewById(R.id.Calories);
    }

    public ImageView getImgRestaurant() {
        return ImgRestaurant;
    }

    public TextView getRestaurantName() {
        return RestaurantName;
    }

    public TextView getDate() {
        return Date;
    }

    public TextView getPrice() {
        return Price;
    }

    public TextView getCalories() {
        return Calories;
    }
}
