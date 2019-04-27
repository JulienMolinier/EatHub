package com.example.eathub.models.databases;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.RestaurantModel;
import com.example.eathub.models.VisitModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VisitsFactory {

    public static void createVisitList(InputStream jsonFile) {
        List<VisitModel> visits = new ArrayList<>();
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
            JSONArray array = (JSONArray) jsonObject.get("Visits");

            for (Object o : array) {
                JSONObject obj = (JSONObject) o;
                VisitModel visitModel = createVisit(obj);
                visits.add(visitModel);
            }

        } catch (IOException | ParseException | NullPointerException e) {
            e.printStackTrace();
        }

        VisitDatabase.getVisits().addAll(visits);
    }

    private static VisitModel createVisit(JSONObject object) {
        double calories = (double) object.get("calories");
        LocalDate date = createLocalDate((JSONObject) object.get("date"));
        double price = (double) object.get("price");
        String commentary = (String) object.get("commentary");
        double mark = (double) object.get("mark");

        ProfileModel profile = ProfilesFactory.createProfile((JSONObject) object.get("profileModel"));
        RestaurantModel restaurant = RestaurantsFactory.createRestaurant((JSONObject) object.get("restaurant"));

        return new VisitModel(profile, restaurant, date, calories, price, commentary, mark);
    }

    private static LocalDate createLocalDate(JSONObject object) {

        long year = (long) object.get("year");
        long month = (long) object.get("monthValue");
        long day = (long) object.get("dayOfMonth");

        return LocalDate.of((int) year, (int) month, (int) day);
    }
}
