package com.example.eathub.models;

import com.example.eathub.models.databases.ProfileDatabase;

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
        List<ProfileModel> profiles = ProfileDatabase.getAllProfiles();
        allEmails = new ArrayList<>();
        for (ProfileModel pm: profiles) {
            allEmails.add(pm.getEmail());
        }
    }

    public boolean emailIsValid() {
        return allEmails.contains(email);
    }
}
