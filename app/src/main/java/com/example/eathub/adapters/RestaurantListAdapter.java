package com.example.eathub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eathub.R;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.RestaurantModel;

import java.util.List;

public class RestaurantListAdapter extends BaseAdapter {
    private Context context;
    private List<RestaurantModel> restaurantList;
    private ProfileModel profile;

    public RestaurantListAdapter(Context context, List<RestaurantModel> restaurantList, ProfileModel profile){
        this.context = context;
        this.restaurantList = restaurantList;
        this.profile = profile;
    }

    @Override
    public int getCount() {
        return this.restaurantList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.restaurantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null){
            LayoutInflater inflater =(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.restaurant_list_item, null);
        }
        RestaurantModel theRestaurant = (RestaurantModel)this.getItem(position);
        TextView nameRestaurant = view.findViewById(R.id.nameRestaurant);
        nameRestaurant.setText(theRestaurant.getName());

        ImageView avatarRestaurant = view.findViewById(R.id.avatarRestaurant);
        int avatar = (view.getResources().getIdentifier(theRestaurant.getName().replaceAll(" ", "").toLowerCase(),"drawable", context.getPackageName()));
        avatarRestaurant.setImageResource(avatar);

        RelativeLayout indicator = view.findViewById(R.id.CVindicator);

        TextView descriptionRestaurant = view.findViewById(R.id.descriptionRestaurant);
        descriptionRestaurant.setText(theRestaurant.getAddress() + " " + theRestaurant.getPhoneNumber());
        return view;
    }

    private void setupIndicator(RestaurantModel restaurant, RelativeLayout rl){
        if(profile != null){
            if(restaurant.getPrice() <= profile.getBudget() && restaurant.getCulinaryFence() == profile.getCulinaryFence())
                rl.setBackgroundColor(this.context.getResources().getColor(R.color.indicatorGreen));
            else if(restaurant.getPrice() <= profile.getBudget())
                rl.setBackgroundColor(this.context.getResources().getColor(R.color.colorAccent));
            else
                rl.setBackgroundColor(this.context.getResources().getColor(R.color.indicatorRed));
        }
    }
}
