package com.example.eathub.models;
import com.example.eathub.models.databases.VisitDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileModel {

    private String email;
    private String password;
    private String imagePath;
    private String firstName;
    private String surname;
    private String birthdate;
    private int age;
    private double height;
    private double weight;
    private double budget;
    private Diet diet;
    private CulinaryFence culinaryFence;
    //private Image profilePic;
    private List<ProfileModel> friendList;
    private List<RestaurantModel> sharedRestaurants;

    private ArrayList<String> profileDetailsList;
    private ArrayList<VisitModel> history;
    private int visitNumber;
    private double spendPercentage;
    private double caloriesPercentage;
    private String spendString;
    private String caloriesString;

    public ProfileModel(String email, String password, String imagePath, String firstName, String surname,
                        String birthdate, double height, double weight, double budget, Diet diet,
                        CulinaryFence culinaryFence) {
        this.imagePath = imagePath;
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
        //this.profilePic = new Image(imagePath);
        this.sharedRestaurants = new ArrayList<>();
        this.history = new ArrayList<>();
        this.profileDetailsList = new ArrayList<>();

    }

    public String getImagePath() {
        return imagePath;
    }

    public void updateProfileList() {
        profileDetailsList.clear();
        profileDetailsList.addAll(Arrays.asList(this.firstName,
                this.surname,
                this.age + " years old",
                this.height + " m",
                this.weight + " kg",
                this.budget + " €",
                "Diet: " + this.diet,
                "Fence: " + this.culinaryFence));
    }
    /*
    public void setHistory(Object comboBoxChoice, ArrayList choices) {
        LocalDate today = LocalDate.now();
        this.history.clear();
        if (comboBoxChoice.equals(choices.get(0))) {
            this.history.addAll(VisitDatabase.getVisitsByProfile(this)
                    .stream()
                    .filter(visitModel -> visitModel.getDate().getMonth().equals(today.getMonth()))
                    .collect(Collectors.toList()));
        } else if (comboBoxChoice.equals(choices.get(1))) {
            this.history.addAll(VisitDatabase.getVisitsByProfile(this)
                    .stream()
                    .filter(visitModel -> visitModel.getDate().isAfter(today.minusWeeks(1)))
                    .collect(Collectors.toList()));
        } else {
            this.history.addAll(VisitDatabase.getVisitsByProfile(this)
                    .stream()
                    .filter(visitModel -> visitModel.getDate().equals(today))
                    .collect(Collectors.toList()));
        }
        history.sort(Comparator.comparing(VisitModel::getDate).reversed());

    }

    public void computeValues(int numberOfDay) {
        double required = this.computeRequired();
        double spend = this.history.stream().mapToDouble(value -> value.getPrice()).sum();
        double caloriesConsumed = this.history.stream().mapToDouble(value -> value.getCalories()).sum();
        spendString = spend + "/" + (this.budget * numberOfDay);
        caloriesString = caloriesConsumed + " / " + (required * numberOfDay);
        spendPercentage = spend / (this.budget * numberOfDay);
        caloriesPercentage = caloriesConsumed / (required * numberOfDay);
        visitNumber = this.history.size();
    }*/

    @Override
    public String toString() {
        return "FirstName: " + firstName + '\n' +
                "Surname: " + surname + '\n' +
                "birthdate: " + birthdate + '\n' +
                "height: " + height + '\n' +
                "Weight: " + weight + '\n' +
                "Budget: " + budget + '\n' +
                "Diet: " + diet + '\n' +
                "CulinaryFence: " + culinaryFence;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
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

    public CulinaryFence getCulinaryFence() {
        return culinaryFence;
    }

    public double getBudget() {
        return budget;
    }

    public String getName() {
        return this.firstName + " " + this.surname;
    }

    /*public Image getProfilePic() {
        return profilePic;
    }*/

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

    public void addFriend(ProfileModel person) {
        if (!friendList.contains(person) && !person.equals(this))
            friendList.add(person);
    }

    public ArrayList<String> getProfileDetailsList() {
        return profileDetailsList;
    }

    public ArrayList<VisitModel> getHistory() {
        return history;
    }

    public int visitNumberProperty() {
        return visitNumber;
    }

    public double spendPercentageProperty() {
        return spendPercentage;
    }

    public double caloriesPercentageProperty() {
        return caloriesPercentage;
    }

    public String spendStringProperty() {
        return spendString;
    }

    public String caloriesStringProperty() {
        return caloriesString;
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
            return this.toString().equals(person.toString());
        }
        return false;
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
