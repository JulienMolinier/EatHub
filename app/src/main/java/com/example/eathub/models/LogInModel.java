package com.example.eathub.models;

import java.util.AbstractMap;



/**
 * @author Lydia BARAUKOVA
 */
public class LogInModel {
    private AbstractMap<String, String> loginData;
    private String email, password;

    public LogInModel(String email, String password) {
        this.email = email;
        this.password = password;
        //getDatabase();
    }

    //private void getDatabase() {}

    public boolean correctEmail() {
        return loginData.containsKey(email);
    }

    public boolean correctPassword() {
        if (password == null) return false;
        return loginData.get(email).equals(password);
    }
}
