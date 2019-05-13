package com.example.eathub.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class VisitModel implements Parcelable {
    private RestaurantModel restaurant;
    private ProfileModel profileModel;
    private LocalDate date;
    private double calories;
    private double price;
    private String commentary;
    private double mark;
    private String image;

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

    protected VisitModel(Parcel in) {
        restaurant = in.readParcelable(RestaurantModel.class.getClassLoader());
        profileModel = in.readParcelable(ProfileModel.class.getClassLoader());
        Date theDate = new Date(in.readLong());
        date = theDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        calories = in.readDouble();
        price = in.readDouble();
        commentary = in.readString();
        mark = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(restaurant, flags);
        dest.writeParcelable(profileModel, flags);
        Date theDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dest.writeLong(theDate.getTime());
        dest.writeDouble(calories);
        dest.writeDouble(price);
        dest.writeString(commentary);
        dest.writeDouble(mark);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VisitModel> CREATOR = new Creator<VisitModel>() {
        @Override
        public VisitModel createFromParcel(Parcel in) {
            return new VisitModel(in);
        }

        @Override
        public VisitModel[] newArray(int size) {
            return new VisitModel[size];
        }
    };

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
        return profileModel.getName() + " visited " + restaurant +
                " the " + date;
    }

    public void setImagePath(String image) {
        this.image = image;
    }

    public String getImage(){
        return this.image;
    }
}
