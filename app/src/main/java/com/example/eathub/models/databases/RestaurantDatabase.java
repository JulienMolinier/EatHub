package com.example.eathub.models.databases;

import com.example.eathub.models.CulinaryFence;
import com.example.eathub.models.RestaurantModel;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDatabase {
    private static List<RestaurantModel> restaurants;

    public RestaurantDatabase() {
        restaurants = new ArrayList<>();
    }

    public static RestaurantModel getRestaurant(String name) {
        for (RestaurantModel restaurant : restaurants) {
            if (restaurant.getName().equals(name)) {
                return restaurant;
            }
        }
        return null;
    }

    public static List<RestaurantModel> getRestaurants() {
        return restaurants;
    }

    public static List<RestaurantModel> getRestaurantsBySearch(String name){
        if (!searchByName(name) && !searchInCulinary(name)){
            return restaurants;
        }
        else if (searchByName(name)){
            return getRestaurantsByName(name);
        }
        else return getRestaurantsByCulinary(name);
    }

    public static List<RestaurantModel> getRestaurantsByCulinary(String name) {
        List<RestaurantModel> list = new ArrayList<>();
        for (RestaurantModel restaurantModel : restaurants) {
            if (restaurantModel.getCulinaryFence().toString().equals(name)) {
                list.add(restaurantModel);
            }
        }
        return list;
    }

    public static List<RestaurantModel> getRestaurantsByName(String name) {
        List<RestaurantModel> list = new ArrayList<>();
        for (RestaurantModel restaurantModel : restaurants) {
            if (restaurantModel.getName().toLowerCase().contains(name.toLowerCase())) {
                list.add(restaurantModel);
            }
        }
        return list;
    }


    private static boolean searchInCulinary(String name) {
        for (CulinaryFence culinary : CulinaryFence.values()) {
            if (culinary.toString().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static boolean searchByName(String name)
    {
        for (RestaurantModel restaurantModel : restaurants) {
            if (restaurantModel.getName().toLowerCase().contains(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
