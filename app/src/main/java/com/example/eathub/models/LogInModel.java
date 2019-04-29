package com.example.eathub.models;

import java.util.AbstractMap;
import java.util.List;
import java.util.TreeMap;

import com.example.eathub.models.databases.ProfileDatabase;

/**
 * @author Lydia BARAUKOVA
 */
public class LogInModel {
    private AbstractMap<String, String> loginData;
    private String email, password;

    public LogInModel(String email, String password) {
        this.email = email;
        this.password = password;
        getDatabase();
    }

    private void getDatabase() {
        List<ProfileModel> profiles = ProfileDatabase.getAllProfiles();
        loginData = new TreeMap<>();
        for (ProfileModel pm: profiles) {
            loginData.put(pm.getEmail(),pm.getPassword());
        }
    }

    public boolean correctEmail() {
        return loginData.containsKey(email);
    }

    public boolean correctPassword() {
        if (password == null) return false;
        return loginData.get(email).equals(password);
    }
}
