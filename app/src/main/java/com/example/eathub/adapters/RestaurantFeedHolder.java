package com.example.eathub.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.eathub.R;

public class RestaurantFeedHolder extends RecyclerView.ViewHolder {
    private ImageView CVImgRestaurant;
    private View CVindicator;
    private TextView CVRestaurantName;
    private TextView CVRestaurantDescription;
    private TextView CVTypeRestaurant;
    private TextView CVRestaurantPrice;
    private Button CVCall;



    public RestaurantFeedHolder(@NonNull View itemView) {
        super(itemView);
        CVImgRestaurant = itemView.findViewById(R.id.CVimgRestaurant);
        CVindicator = itemView.findViewById(R.id.CVindicator);
        CVRestaurantName = itemView.findViewById(R.id.CVRestaurantName);
        CVRestaurantDescription = itemView.findViewById(R.id.CVRestaurantDescription);
        CVTypeRestaurant = itemView.findViewById(R.id.CVTypeRestaurant);
        CVRestaurantPrice = itemView.findViewById(R.id.CVRestaurantPrice);
        CVCall = itemView.findViewById(R.id.CVCall);
    }

    public ImageView getCVImgRestaurant() {
        return CVImgRestaurant;
    }

    public View getCVindicator() {
        return CVindicator;
    }

    public TextView getCVRestaurantName() {
        return CVRestaurantName;
    }

    public TextView getCVRestaurantDescription() {
        return CVRestaurantDescription;
    }

    public TextView getCVTypeRestaurant() {
        return CVTypeRestaurant;
    }

    public TextView getCVRestaurantPrice() {
        return CVRestaurantPrice;
    }

    public Button getCVCall() {
        return CVCall;
    }
}
