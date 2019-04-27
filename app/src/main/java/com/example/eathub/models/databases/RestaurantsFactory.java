package com.example.eathub.models.databases;

import android.util.JsonReader;
import android.util.Log;

import org.json.JSONTokener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.example.eathub.models.CulinaryFence;
import com.example.eathub.models.RestaurantModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestaurantsFactory {

    public static void createRestaurantsList(InputStream jsonFile) {
        List<RestaurantModel> restaurants = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(jsonFile));
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = null;
            StringBuilder myString = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                myString.append(line);
            }
            jsonObject = (JSONObject) jsonParser.parse(myString.toString());
            JSONArray array = (JSONArray) jsonObject.get("Restaurants");

            for (Object o : array) {
                JSONObject obj = (JSONObject) o;
                RestaurantModel restaurantModel = createRestaurant(obj);
                restaurants.add(restaurantModel);
            }

        } catch (IOException | ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        RestaurantDatabase.getRestaurants().addAll(restaurants);
    }

    static RestaurantModel createRestaurant(JSONObject object) {

        String name = (String) object.get("name");
        double price = (double) object.get("price");
        CulinaryFence culinaryFence = CulinaryFence.valueOf((String) object.get("culinaryFence"));
        String imagePath = (String) object.get("imagePath");
        String address = (String) object.get("address");
        String phoneNumber = (String) object.get("phoneNumber");

        return new RestaurantModel(name, imagePath, price, culinaryFence, address, phoneNumber);
    }
}
