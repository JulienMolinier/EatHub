package com.example.eathub.models.databases;

import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.RestaurantModel;
import com.example.eathub.models.VisitModel;

import java.util.ArrayList;
import java.util.List;

public class VisitDatabase {

    private static List<VisitModel> visits;

    public VisitDatabase() {
        visits = new ArrayList<>();
    }

    public static List<VisitModel> getVisits() {
        return visits;
    }

    public static List<VisitModel> getVisitsByProfile(ProfileModel profileModel) {
        List<VisitModel> visitsByProfile = new ArrayList<>();
        for (VisitModel v : visits) {
            if (v.getProfileModel().equals(profileModel)) {
                visitsByProfile.add(v);
            }
        }
        return visitsByProfile;
    }

    public static List<VisitModel> getVisitsByRestaurant(RestaurantModel restaurantModel) {
        List<VisitModel> visitsByRestaurant = new ArrayList<>();
        for (VisitModel v : visits) {
            if (v.getRestaurant().equals(restaurantModel)) {
                visitsByRestaurant.add(v);
            }
        }
        return visitsByRestaurant;
    }
}
