package com.example.eathub.models;

import com.example.eathub.models.databases.DatabaseHandler;
import com.example.eathub.models.databases.ProfileDatabase;

/**
 * @author Lydia BARAUKOVA
 */
public class SignUpModel {

    private String firstName, lastName, email, password, passwordAgain, gender, weight, height,
            budget, objective, birthdate;
    private Diet specialDiet;
    private CulinaryFence favoriteCuisine;
    private boolean privacyPolicy, notifications;

    public SignUpModel(String firstName, String lastName, String email, String password, String passwordAgain,
                       String gender, String weight, String height, String specialDiet, String favoriteCuisine,
                       String budget, String objective, String birthdate, boolean privacyPolicy, boolean notifications) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.passwordAgain = passwordAgain;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        if (specialDiet.equals("special diet")) specialDiet = "none";
        this.specialDiet = Diet.fromName(specialDiet);
        if (favoriteCuisine.equals("favorite cuisine")) favoriteCuisine = "none";
        this.favoriteCuisine = CulinaryFence.fromName(favoriteCuisine);
        this.budget = budget;
        this.objective = objective;
        this.birthdate = birthdate;
        this.privacyPolicy = privacyPolicy;
        this.notifications = notifications;
    }

    public boolean obligatoryFieldsFilled() {
        if (firstName.equals("")) return false;
        if (email.equals("")) return false;
        if (birthdate.equals("")) return false;
        if (height.equals("")) return false;
        if (weight.equals("")) return false;
        if (budget.equals("")) return false;
        if (password.equals("")) return false;
        if (passwordAgain.equals("")) return false;
        return true;
    }

    public boolean passwordsMatch() {
        return password.equals(passwordAgain);
    }

    public boolean privacyPolicyAccepted() {
        return privacyPolicy;
    }

    public void addUserToDatabase() {

        ProfileModel newProfile = new ProfileModel(email, password, firstName, lastName, birthdate,
                Double.parseDouble(height), Double.parseDouble(weight), Double.parseDouble(budget),
                specialDiet, favoriteCuisine);
        DatabaseHandler.addProfileToDB(newProfile);
        ProfileDatabase.addNewProfile(newProfile);
    }
}
