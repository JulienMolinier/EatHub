package com.example.eathub.fragments.restaurant;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
        imageRestaurant.setImageResource(view.getResources()
                .getIdentifier(this.restaurantModel.getName().replaceAll(" ", "")
                                .replaceAll("-", "").toLowerCase(), "drawable",
                        view.getContext().getPackageName()));
        Button sharedButton = view.findViewById(R.id.sharedButton);
        TextView restaurantName = view.findViewById(R.id.restaurantName);
        restaurantName.setText(this.restaurantModel.getName());
        TextView restaurantTel = view.findViewById(R.id.restaurantTel);
        restaurantTel.setText(this.restaurantModel.getPhoneNumber());
        TextView restaurantAdress = view.findViewById(R.id.restaurantAdress);
        restaurantAdress.setText(this.restaurantModel.getAddress());
        TextView restaurantCost = view.findViewById(R.id.restaurantCost);
        Button buttonTel = view.findViewById(R.id.buttonTel);
        switch ((int) this.restaurantModel.getPrice()) {
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
        restaurantRate.setRating((int) this.restaurantModel.getRating());

        buttonTel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + restaurantModel.getPhoneNumber()));
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //no permission
                } else {
                    startActivity(callIntent);
                }
            }
        });

        return view;
    }

    public void setProfileModel(ProfileModel profileModel) {
        this.profileModel = profileModel;
    }

    public void setRestaurantModel(RestaurantModel restaurantModel) {
        this.restaurantModel = restaurantModel;
    }
}
