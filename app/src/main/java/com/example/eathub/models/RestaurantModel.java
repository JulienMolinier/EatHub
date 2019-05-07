package com.example.eathub.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.eathub.models.databases.VisitDatabase;

import java.util.List;

public class RestaurantModel implements Parcelable {
    private int id;
    private String name;
    private double price;
    private CulinaryFence culinaryFence;
    private String address;
    private String phoneNumber;
    private double latitude;
    private double longitude;

    public RestaurantModel(int id, String name, double price, CulinaryFence culinaryFence,
                           String address, String phoneNumber, double longitude, double latitude) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.culinaryFence = culinaryFence;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    protected RestaurantModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readDouble();
        culinaryFence = CulinaryFence.values()[in.readInt()];
        address = in.readString();
        phoneNumber = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeInt(culinaryFence.ordinal());
        dest.writeString(address);
        dest.writeString(phoneNumber);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RestaurantModel> CREATOR = new Creator<RestaurantModel>() {
        @Override
        public RestaurantModel createFromParcel(Parcel in) {
            return new RestaurantModel(in);
        }

        @Override
        public RestaurantModel[] newArray(int size) {
            return new RestaurantModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getRating() {
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
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
