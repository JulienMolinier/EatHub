package com.example.eathub.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.eathub.models.databases.VisitDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileModel implements Parcelable {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String surname;
    private String birthdate;
    private int age;
    private double height;
    private double weight;
    private double budget;
    private Diet diet;
    private CulinaryFence culinaryFence;
    private List<ProfileModel> friendList;
    private List<RestaurantModel> sharedRestaurants;

    private ArrayList<String> profileDetailsList;
    private ArrayList<VisitModel> history;
    private int visitNumber;
    private double required;
    private double spend;
    private double caloriesConsumed;

    public ProfileModel(int id, String email, String password, String firstName, String surname,
                        String birthdate, double height, double weight, double budget, Diet diet,
                        CulinaryFence culinaryFence) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.birthdate = birthdate;
        this.age = computeAge();
        this.height = height;
        this.weight = weight;
        this.budget = budget;
        this.diet = diet;
        this.culinaryFence = culinaryFence;
        this.email = email;
        this.password = password;
        this.friendList = new ArrayList<>();
        this.sharedRestaurants = new ArrayList<>();
        this.history = new ArrayList<>();
        this.profileDetailsList = new ArrayList<>();
        updateProfileList();

    }

    public ProfileModel(String email, String password, String firstName, String surname,
                        String birthdate, double height, double weight, double budget, Diet diet,
                        CulinaryFence culinaryFence) {
        this.firstName = firstName;
        this.surname = surname;
        this.birthdate = birthdate;
        this.age = computeAge();
        this.height = height;
        this.weight = weight;
        this.budget = budget;
        this.diet = diet;
        this.culinaryFence = culinaryFence;
        this.email = email;
        this.password = password;
        this.friendList = new ArrayList<>();
        this.sharedRestaurants = new ArrayList<>();
        this.history = new ArrayList<>();
        this.profileDetailsList = new ArrayList<>();
        updateProfileList();

    }

    protected ProfileModel(Parcel in) {
        id = in.readInt();
        email = in.readString();
        password = in.readString();
        firstName = in.readString();
        surname = in.readString();
        birthdate = in.readString();
        age = in.readInt();
        height = in.readDouble();
        weight = in.readDouble();
        budget = in.readDouble();
        diet = Diet.values()[in.readInt()];
        friendList = in.createTypedArrayList(ProfileModel.CREATOR);
        profileDetailsList = in.createStringArrayList();
        visitNumber = in.readInt();
        sharedRestaurants = in.createTypedArrayList(RestaurantModel.CREATOR);
        history = in.createTypedArrayList(VisitModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(firstName);
        dest.writeString(surname);
        dest.writeString(birthdate);
        dest.writeInt(age);
        dest.writeDouble(height);
        dest.writeDouble(weight);
        dest.writeDouble(budget);
        dest.writeInt(diet.ordinal());
        dest.writeTypedList(friendList);
        dest.writeStringList(profileDetailsList);
        dest.writeInt(visitNumber);
        dest.writeTypedList(sharedRestaurants);
        dest.writeTypedList(history);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProfileModel> CREATOR = new Creator<ProfileModel>() {
        @Override
        public ProfileModel createFromParcel(Parcel in) {
            return new ProfileModel(in);
        }

        @Override
        public ProfileModel[] newArray(int size) {
            return new ProfileModel[size];
        }
    };

    public String getSurname() {
        return surname;
    }

    public int getId() {
        return id;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public Diet getDiet() {
        return diet;
    }

    public void updateProfileList() {
        profileDetailsList.clear();
        profileDetailsList.addAll(Arrays.asList("Firstname: " + this.firstName,
                "Surname: " + this.surname,
                "Age: " + this.age + " years old",
                "Height: " + this.height + " m",
                "Weight: " + this.weight + " kg",
                "Budget: " + this.budget + " €",
                "Diet: " + this.diet,
                "Fence: " + this.culinaryFence));
    }

    public void setHistory(int spinnerChoice) {
        LocalDate today = LocalDate.now();
        this.history.clear();
        if (spinnerChoice == 2) {
            this.history.addAll(VisitDatabase.getVisitsByProfile(this)
                    .stream()
                    .filter(visitModel -> visitModel.getDate().getMonth().equals(today.getMonth()))
                    .collect(Collectors.toList()));
        } else if (spinnerChoice == 1) {
            this.history.addAll(VisitDatabase.getVisitsByProfile(this)
                    .stream()
                    .filter(visitModel -> visitModel.getDate().isAfter(today.minusWeeks(1)))
                    .collect(Collectors.toList()));
        } else if (spinnerChoice == 0) {
            this.history.addAll(VisitDatabase.getVisitsByProfile(this)
                    .stream()
                    .filter(visitModel -> visitModel.getDate().equals(today))
                    .collect(Collectors.toList()));
        } else {
            this.history.addAll(VisitDatabase.getVisitsByProfile(this)
                    .stream()
                    .filter(visitModel -> visitModel.getDate().getYear() == today.getYear())
                    .collect(Collectors.toList()));
        }
        history.sort(Comparator.comparing(VisitModel::getDate).reversed());

    }

    public ArrayList<VisitModel> getHistory() {
        return history;
    }

    public void computeValues(int spinnerChoice) {
        setHistory(spinnerChoice);
        required = this.computeRequired();
        spend = this.history.stream().mapToDouble(value -> value.getPrice()).sum();
        caloriesConsumed = this.history.stream().mapToDouble(value -> value.getCalories()).sum();
        visitNumber = this.history.size();
    }

    public String getPassword() {
        return password;
    }

    public CulinaryFence getCulinaryFence() {
        return culinaryFence;
    }

    public double getBudget() {
        return budget;
    }

    public String getName() {
        return this.firstName + " " + this.surname;
    }

    public List<RestaurantModel> getSharedRestaurants() {
        return sharedRestaurants;
    }

    public void setSharedRestaurants(List<RestaurantModel> sharedRestaurants) {
        this.sharedRestaurants = sharedRestaurants;
    }

    public List<RestaurantModel> getRestaurantsSharedByFriends() {
        List<RestaurantModel> restaurantsShared = new ArrayList<>();
        for (ProfileModel friend : friendList) {
            for (RestaurantModel restaurant : friend.getSharedRestaurants()) {
                if (!restaurantsShared.contains(restaurant))
                    restaurantsShared.add(restaurant);
            }
        }
        return restaurantsShared;
    }

    double computeRequired() {
        double minimalRequired = this.weight * 24;
        return minimalRequired * 1.5;
    }

    public String getEmail() {
        return email;
    }

    public boolean isFriendWith(ProfileModel person) {
        return friendList.contains(person);
    }

    public int getVisitNumber() {
        return visitNumber;
    }

    public double getRequired() {
        return required;
    }

    public double getSpend() {
        return spend;
    }

    public double getCaloriesConsumed() {
        return caloriesConsumed;
    }

    public void addFriend(ProfileModel person) {
        if (!friendList.contains(person) && !person.equals(this))
            friendList.add(person);
    }

    public ArrayList<String> getProfileDetailsList() {
        return profileDetailsList;
    }

    public List<ProfileModel> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<ProfileModel> friendList) {
        this.friendList = friendList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            ProfileModel person = (ProfileModel) obj;
            return this.email.equals(person.email) && this.firstName.equals(person.firstName)
                    && this.surname.equals(person.surname);
        }
        return false;
    }

    public String getFirstName() {
        return this.firstName;
    }

    private int computeAge() {
        LocalDate today = LocalDate.now();
        String[] date = birthdate.split("-");
        int year1 = Integer.parseInt(date[0]);
        int month1 = Integer.parseInt(date[1]);
        int day1 = Integer.parseInt(date[2]);

        int diffDays = today.getDayOfMonth() - day1;
        int diffMonths = today.getMonthValue() - month1;
        int diffYears = today.getYear() - year1;

        if (diffDays < 0) {
            diffMonths--;
        }
        if (diffMonths < 0) {
            diffYears--;
        }
        return diffYears;
    }
}
