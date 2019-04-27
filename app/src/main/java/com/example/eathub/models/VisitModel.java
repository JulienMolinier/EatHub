package com.example.eathub.models;
import java.time.LocalDate;

public class VisitModel {
    private RestaurantModel restaurant;
    private ProfileModel profileModel;
    private LocalDate date;
    private double calories;
    private double price;
    private String commentary;
    private double mark;

    public VisitModel(ProfileModel profileModel, RestaurantModel restaurant, LocalDate date, double calories,
                      double price, String commentary, double mark) {
        this.profileModel = profileModel;
        this.restaurant = restaurant;
        this.date = date;
        this.calories = calories;
        this.price = price;
        this.mark = mark;
        this.commentary = commentary;
    }

    public ProfileModel getProfileModel() {
        return profileModel;
    }

    public String getCommentary() {
        return commentary;
    }

    public double getMark() {
        return mark;
    }

    public RestaurantModel getRestaurant() {
        return restaurant;
    }

    public double getCalories() {
        return calories;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Visited " + restaurant +
                " the " + date;
    }
}
