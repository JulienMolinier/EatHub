package com.example.eathub.models;

import com.example.eathub.models.databases.VisitDatabase;

import java.util.List;

public class RestaurantModel {
    private String name;
    private double price;
    private CulinaryFence culinaryFence;
    private String address;
    private String phoneNumber;

    public RestaurantModel(String name, double price, CulinaryFence culinaryFence,
                           String address, String phoneNumber) {
        this.name = name;
        this.price = price;
        this.culinaryFence = culinaryFence;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Double getRating() {
        double sum = 0;
        List<VisitModel> visits = VisitDatabase.getVisitsByRestaurant(this);
        if (visits.size() == 0) {
            return 0.0;
        } else {
            for (VisitModel v : visits) {
                sum += v.getMark();
            }
            return sum / visits.size();
        }
    }

    @Override
    public String toString() {
        return name;
    }

    /*public Image getPic() {
        return pic;
    }*/

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public CulinaryFence getCulinaryFence() {
        return culinaryFence;
    }

    public void addVisit(VisitModel visit) {
        VisitDatabase.getVisits().add(visit);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            RestaurantModel restaurant = (RestaurantModel) obj;
            return this.getName().equals(restaurant.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
