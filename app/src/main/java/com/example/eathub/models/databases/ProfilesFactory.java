package com.example.eathub.models.databases;

import com.example.eathub.models.CulinaryFence;
import com.example.eathub.models.Diet;
import com.example.eathub.models.ProfileModel;
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

public class ProfilesFactory {

    public static void createProfilesList(InputStream jsonFile) {
        List<ProfileModel> profiles = new ArrayList<>();
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
            JSONArray array = (JSONArray) jsonObject.get("Profiles");

            for (Object o : array) {
                JSONObject objectJson = (JSONObject) o;
                ProfileModel profile = createProfile(objectJson);
                profiles.add(profile);
            }

        } catch (IOException | ParseException | NullPointerException e) {
            e.printStackTrace();
        }

        ProfileDatabase.getAllProfiles().addAll(profiles);
    }

    static ProfileModel createProfile(JSONObject object) {
        String email = (String) object.get("email");
        String password = (String) object.get("password");
        String firstName = (String) object.get("firstName");
        String surname = (String) object.get("surname");
        String birthdate = (String) object.get("birthdate");
        double height = (double) object.get("height");
        double weight = (double) object.get("weight");
        double budget = (double) object.get("budget");
        Diet diet = Diet.fromName((String) object.get("diet"));
        CulinaryFence culinaryFence = CulinaryFence.fromName((String) object.get("culinaryFence"));

        ProfileModel profile = new ProfileModel(email, password, firstName, surname,
                birthdate, height, weight, budget, diet, culinaryFence);

        JSONArray array = (JSONArray) object.get("sharedRestaurants");
        profile.setFriendList((ArrayList)createFriendList(object));
        profile.setSharedRestaurants(createSharedList(array));

        return profile;
    }

    private static List<RestaurantModel> createSharedList(JSONArray array2) {
        List<RestaurantModel> sharedList = new ArrayList<>();

        for (Object obj : array2) {
            JSONObject objectJson = (JSONObject) obj;
            RestaurantModel restaurantModel = RestaurantsFactory.createRestaurant(objectJson);
            sharedList.add(restaurantModel);
        }

        return sharedList;
    }

    private static List<ProfileModel> createFriendList(JSONObject object) {
        List<ProfileModel> friendList = new ArrayList<>();
        JSONArray array = (JSONArray) object.get("friendList");

        for (Object obj : array) {
            JSONObject objectJson = (JSONObject) obj;
            ProfileModel friend = createProfile(objectJson);
            friendList.add(friend);
        }

        return friendList;
    }

}
