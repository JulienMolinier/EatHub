package com.example.eathub.models;
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
        this.specialDiet = Diet.fromName(specialDiet);
        this.favoriteCuisine = CulinaryFence.fromName(favoriteCuisine);
        this.budget = budget;
        this.objective = objective;
        this.birthdate = birthdate;
        this.privacyPolicy = privacyPolicy;
        this.notifications =notifications;
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
        String defaultImage = "/program/resources/images/user.png";

        double averageHeight;
        if (height.contains("<") || height.contains(">")) {
            averageHeight = Double.parseDouble(height.split("[<>]")[1]);
        } else {
            String[] heightRange = height.split("-");
            averageHeight = (Double.parseDouble(heightRange[0]) + Double.parseDouble(heightRange[1])) / 2;
        }

        double averageWeight;
        if (weight.contains("<") || weight.contains(">")) {
            averageWeight = Double.parseDouble(weight.split("[<>]")[1]);
        } else {
            String[] weightRange = weight.split("-");
            averageWeight = (Double.parseDouble(weightRange[0]) + Double.parseDouble(weightRange[1])) / 2;
        }

        double averageBudget;
        if (budget.contains("<") || budget.contains(">")) {
            averageBudget = Double.parseDouble(budget.split("[<>]")[1]);
        } else {
            String[] budgetRange = budget.split("-");
            averageBudget = (Double.parseDouble(budgetRange[0]) + Double.parseDouble(budgetRange[1])) / 2;
        }

        ProfileModel newProfile = new ProfileModel(email,password,defaultImage,firstName,lastName,birthdate,
                averageHeight,averageWeight,averageBudget,specialDiet,favoriteCuisine);

        ProfileDatabase.addNewProfile(newProfile);
    }
}
