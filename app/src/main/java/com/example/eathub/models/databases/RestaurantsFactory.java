package com.example.eathub.models.databases;

import com.example.eathub.models.CulinaryFence;
import com.example.eathub.models.RestaurantModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
        String address = (String) object.get("address");
        String phoneNumber = (String) object.get("phoneNumber");

        return new RestaurantModel(name, price, culinaryFence, address, phoneNumber);
    }
}
