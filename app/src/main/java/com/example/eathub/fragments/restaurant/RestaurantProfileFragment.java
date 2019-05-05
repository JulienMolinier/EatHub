package com.example.eathub.fragments.restaurant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.eathub.R;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.RestaurantModel;

public class RestaurantProfileFragment extends Fragment {
    private View view;
    private ProfileModel profileModel;
    private RestaurantModel restaurantModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.restaurantprofile, container, false);
        ImageView imageRestaurant = view.findViewById(R.id.imageRestaurant);
        imageRestaurant.setImageResource(this.restaurantModel.);
        Button sharedButton = view.findViewById(R.id.sharedButton);
        TextView restaurantName = view.findViewById(R.id.restaurantName);
        restaurantName.setText(this.restaurantModel.getName());
        TextView restaurantTel = view.findViewById(R.id.restaurantTel);
        restaurantTel.setText(this.restaurantModel.getPhoneNumber());
        TextView restaurantAdress  = view.findViewById(R.id.restaurantAdress);
        restaurantAdress.setText(this.restaurantModel.getAddress());
        TextView restaurantCost = view.findViewById(R.id.restaurantCost);
        switch ((int)this.restaurantModel.getPrice()){
            case 1:
                restaurantCost.setText("$");
                break;
            case 2:
                restaurantCost.setText("$$");
                break;
            case 3:
                restaurantCost.setText("$$$");
                break;
            default:
                restaurantCost.setText("$");
                break;
        }
        RatingBar restaurantRate = view.findViewById(R.id.restaurantRate);
        restaurantRate.setRating((int)this.restaurantModel.getRating());
        return view;
    }

    public void setProfileModel(ProfileModel profileModel) {
        this.profileModel = profileModel;
    }
    public void setRestaurantModel(RestaurantModel restaurantModel){
        this.restaurantModel=restaurantModel;
    }
}
