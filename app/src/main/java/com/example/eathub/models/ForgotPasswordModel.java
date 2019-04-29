package com.example.eathub.models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lydia BARAUKOVA
 */
public class ForgotPasswordModel {

    private String email;
    private List<String> allEmails;

    public ForgotPasswordModel(String email) {
        this.email = email;
        getEmails();
    }

    private void getEmails() {
        allEmails = new ArrayList<>();
        String jsonFile = "src/program/resources/jsons/profiles.json";
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(jsonFile));
            JSONArray profileArray = (JSONArray) jsonObject.get("Profiles");
            for (Object o: profileArray) {
                JSONObject jo = (JSONObject) o;
                String userEmail = (String) jo.get("email");
                allEmails.add(userEmail);
            }
        } catch (IOException | ParseException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public boolean emailIsValid() {
        return allEmails.contains(email);
    }
}
