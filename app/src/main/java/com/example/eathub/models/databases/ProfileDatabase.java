package com.example.eathub.models.databases;
import com.example.eathub.models.ProfileModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileDatabase {
    private static List<ProfileModel> profiles;

    public ProfileDatabase() {
        profiles = new ArrayList<>();
    }

    public static List<ProfileModel> getAllProfiles() {
        return profiles;
    }

    public static ProfileModel getProfile(String email) {
        for (ProfileModel profile : profiles) {
            if (profile.getEmail().equals(email)) {
                return profile;
            }
        }
        return null;
    }

    public static ProfileModel getProfileByName(String personToFind) {
        for (ProfileModel profile : profiles) {
            if (profile.getName().equals(personToFind)) {
                return profile;
            }
        }
        return null;
    }

    public static void addNewProfile(ProfileModel m) {
        profiles.add(m);
    }
}
