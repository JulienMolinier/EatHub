package com.example.eathub.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.eathub.models.databases.ProfileDatabase;
import com.example.eathub.models.databases.RestaurantDatabase;
import com.example.eathub.models.databases.VisitDatabase;

import java.time.LocalDate;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String VISIT_TABLE_NAME = "Visits";
    private final static String NAME = "database.db";

    private static final String PROFILE_KEY = "id";
    private static final String PROFILE_EMAIL = "email";
    private static final String PROFILE_PASSWORD = "password";
    private static final String PROFILE_FIRSTNAME = "firstname";
    private static final String PROFILE_SURNAME = "surname";
    private static final String PROFILE_BIRTHDATE = "birthdate";
    private static final String PROFILE_HEIGHT = "height";
    private static final String PROFILE_WEIGHT = "weight";
    private static final String PROFILE_BUDGET = "budget";
    private static final String PROFILE_DIET = "diet";
    private static final String PROFILE_CULINARYFENCE = "culinaryFence";

    private static final String FRIEND_KEY = "id";
    private static final String FRIENDONE = "friendone";
    private static final String FRIENDTWO = "friendtwo";

    private static final String RESTAURANT_KEY = "id";
    private static final String RESTAURANT_NAME = "name";
    private static final String RESTAURANT_PRICE = "price";
    private static final String RESTAURANT_CULYN = "culinaryFence";
    private static final String RESTAURANT_ADDRESS = "address";
    private static final String RESTAURANT_PHONE = "phoneNumber";
    private static final String RESTAURANT_LAT = "latitude";
    private static final String RESTAURANT_LONG = "longitude";

    private static final String VISIT_KEY = "id";
    private static final String VISIT_REST = "restaurant";
    private static final String VISIT_PROFILE = "profile";
    private static final String VISIT_DATE = "date";
    private static final String VISIT_CALORIES = "calories";
    private static final String VISIT_PRICE = "price";
    private static final String VISIT_COMMENT = "commentary";
    private static final String VISIT_MARK = "mark";
    private static SQLiteDatabase db;
    private static final String VISIT_TABLE_CREATE =
            "CREATE TABLE " + VISIT_TABLE_NAME + " (" +
                    VISIT_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    VISIT_REST + " TEXT, " +
                    VISIT_PROFILE + " TEXT, " +
                    VISIT_DATE + " TEXT, " +
                    VISIT_CALORIES + " TEXT, " +
                    VISIT_PRICE + " TEXT, " +
                    VISIT_COMMENT + " DOUBLE, " +
                    VISIT_MARK + " DOUBLE" + ");";

    private static final String VISIT_TABLE_DROP = "DROP TABLE IF EXISTS " + VISIT_TABLE_NAME + ";";

    private static final String PROFILE_TABLE_NAME = "Profiles";
    private static final String PROFILE_TABLE_CREATE =
            "CREATE TABLE " + PROFILE_TABLE_NAME + " (" +
                    PROFILE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PROFILE_EMAIL + " TEXT, " +
                    PROFILE_PASSWORD + " TEXT, " +
                    PROFILE_FIRSTNAME + " TEXT, " +
                    PROFILE_SURNAME + " TEXT, " +
                    PROFILE_BIRTHDATE + " TEXT, " +
                    PROFILE_HEIGHT + " DOUBLE, " +
                    PROFILE_WEIGHT + " DOUBLE, " +
                    PROFILE_BUDGET + " DOUBLE, " +
                    PROFILE_DIET + " TEXT, " +
                    PROFILE_CULINARYFENCE + " TEXT" + ");";

    private static final String PROFILE_TABLE_DROP = "DROP TABLE IF EXISTS " + PROFILE_TABLE_NAME + ";";

    private static final String FRIEND_TABLE_NAME = "Friends";
    private static final String FRIEND_TABLE_CREATE =
            "CREATE TABLE " + FRIEND_TABLE_NAME + " (" +
                    FRIEND_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FRIENDONE + " INTEGER, " +
                    FRIENDTWO + " INTEGER);";

    private static final String FRIEND_TABLE_DROP = "DROP TABLE IF EXISTS " + FRIEND_TABLE_NAME + ";";

    private static final String RESTAURANT_TABLE_NAME = "Restaurants";
    private static final String RESTAURANT_TABLE_CREATE =
            "CREATE TABLE " + RESTAURANT_TABLE_NAME + " (" +
                    RESTAURANT_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    RESTAURANT_NAME + " TEXT, " +
                    RESTAURANT_PRICE + " TEXT, " +
                    RESTAURANT_CULYN + " TEXT, " +
                    RESTAURANT_ADDRESS + " TEXT, " +
                    RESTAURANT_PHONE + " TEXT, " +
                    RESTAURANT_LAT + " DOUBLE, " +
                    RESTAURANT_LONG + " DOUBLE " + ");";

    private static final String RESTAURANT_TABLE_DROP = "DROP TABLE IF EXISTS " + RESTAURANT_TABLE_NAME + ";";


    public DatabaseHandler(Context context) {
        super(context, NAME, null, 1);
        new RestaurantDatabase();
        new ProfileDatabase();
        new VisitDatabase();
    }

    public static boolean addVisitToDB(VisitModel visitModel) {
        long res = db.insert(DatabaseHandler.VISIT_TABLE_NAME, null, createVisit(visitModel));
        return res != -1;
    }

    private static ContentValues createVisit(VisitModel visit) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(VISIT_REST, visit.getRestaurant().getId());
        contentValues.put(VISIT_PROFILE, visit.getProfileModel().getId());
        contentValues.put(VISIT_DATE, visit.getDate().toString());
        contentValues.put(VISIT_CALORIES, visit.getCalories());
        contentValues.put(VISIT_PRICE, visit.getPrice());
        contentValues.put(VISIT_COMMENT, visit.getCommentary());
        contentValues.put(VISIT_MARK, visit.getMark());
        return contentValues;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        initiateData(db);
    }

    private void initiateData(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + PROFILE_TABLE_NAME, null);
        Cursor cursor2 = db.rawQuery("SELECT * FROM " + RESTAURANT_TABLE_NAME, null);
        Cursor cursor3 = db.rawQuery("SELECT * FROM " + FRIEND_TABLE_NAME, null);
        Cursor cursor4 = db.rawQuery("SELECT * FROM " + VISIT_TABLE_NAME, null);

        while (cursor.moveToNext()) {
            ProfileModel profileModel = createProfileFromRaw(cursor);
            ProfileDatabase.addNewProfile(profileModel);
        }
        cursor.close();
        while (cursor2.moveToNext()) {
            RestaurantModel restaurantModel = createRestaurantFromRaw(cursor2);
            RestaurantDatabase.getRestaurants().add(restaurantModel);
        }
        cursor2.close();
        while (cursor3.moveToNext()) {
            int friend1 = cursor3.getInt(1);
            int friend2 = cursor3.getInt(2);
            ProfileDatabase.getAllProfiles().get(friend1)
                    .addFriend(ProfileDatabase.getAllProfiles().get(friend2));
        }
        cursor3.close();
        while (cursor4.moveToNext()) {
            VisitModel visitModel = createVisitFromRaw(cursor4);
            VisitDatabase.getVisits().add(visitModel);
        }
        cursor4.close();

    }

    private RestaurantModel createRestaurantFromRaw(Cursor cursor) {
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        double price = cursor.getDouble(2);
        CulinaryFence culinaryFence = CulinaryFence.fromName(cursor.getString(3));
        String address = cursor.getString(4);
        String phone = cursor.getString(5);
        double lat = cursor.getDouble(6);
        double longitude = cursor.getDouble(7);

        return new RestaurantModel(id, name, price, culinaryFence,
                address, phone, lat, longitude);
    }

    private VisitModel createVisitFromRaw(Cursor cursor) {
        int restaurantId = cursor.getInt(1);
        int profileId = cursor.getInt(2);
        LocalDate date = LocalDate.parse(cursor.getString(3));
        double calories = cursor.getDouble(4);
        double price = cursor.getDouble(5);
        String commentary = cursor.getString(6);
        double mark = cursor.getDouble(7);

        return new VisitModel(ProfileDatabase.getAllProfiles().get(profileId),
                RestaurantDatabase.getRestaurants().get(restaurantId), date, calories, price,
                commentary, mark);
    }

    public void openDB() {
        try {
            db = getWritableDatabase();
        } catch (SQLiteException ex) {
            db = getReadableDatabase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PROFILE_TABLE_CREATE);
        db.execSQL(FRIEND_TABLE_CREATE);
        db.execSQL(RESTAURANT_TABLE_CREATE);
        db.execSQL(VISIT_TABLE_CREATE);

        ProfileModel mmdurand = new ProfileModel(0, "mm.durand@gmail.com", "0",
                "Marie-Madeleine", "Durand", "1947-08-08", 1.6,
                55, 18.0, Diet.vegan, CulinaryFence.vegan);
        ContentValues contentValues = createProfile(mmdurand);

        ProfileModel jpot = new ProfileModel(1, "jordan.pot@gmail.com", "0",
                "Jordan", "Pot", "1996-04-08", 1.7,
                65, 8.0, Diet.none, CulinaryFence.italian);
        ContentValues contentValues2 = createProfile(jpot);

        ProfileModel fleca = new ProfileModel(2, "fabrice.leca@gmail.com", "0",
                "Fabrice", "Leca", "1972-06-05", 1.8,
                90, 30.0, Diet.none, CulinaryFence.asian);
        ContentValues contentValues3 = createProfile(fleca);

        ContentValues contentValues4 = new ContentValues();
        contentValues4.put(FRIENDONE, 0);
        contentValues4.put(FRIENDTWO, 1);

        ContentValues contentValues5 = new ContentValues();
        contentValues5.put(FRIENDONE, 0);
        contentValues5.put(FRIENDTWO, 2);

        RestaurantModel pizzaCorsica = new RestaurantModel(0, "Pizza Corsica", 15.0,
                CulinaryFence.italian, "5895 rue des arbres", "0102030405",
                7.07197, 43.6295);
        ContentValues contentValues6 = createRestaurant(pizzaCorsica);

        RestaurantModel lebelagio = new RestaurantModel(1, "Le Belagio", 25.0,
                CulinaryFence.italian, "20 rue des arbres", "0102030405",
                7.05796, 43.6184);
        ContentValues contentValues7 = createRestaurant(lebelagio);


        RestaurantModel lebarbusse = new RestaurantModel(2, "Le Barbusse", 18.0,
                CulinaryFence.vegan, "18 rue des arbres", "0102030405",
                7.06452, 43.6166);
        ContentValues contentValues8 = createRestaurant(lebarbusse);

        db.insert(PROFILE_TABLE_NAME, null, contentValues);
        db.insert(PROFILE_TABLE_NAME, null, contentValues2);
        db.insert(PROFILE_TABLE_NAME, null, contentValues3);

        db.insert(FRIEND_TABLE_NAME, null, contentValues4);
        db.insert(FRIEND_TABLE_NAME, null, contentValues5);

        db.insert(RESTAURANT_TABLE_NAME, null, contentValues6);
        db.insert(RESTAURANT_TABLE_NAME, null, contentValues7);
        db.insert(RESTAURANT_TABLE_NAME, null, contentValues8);

        mmdurand.addFriend(jpot);
        mmdurand.addFriend(fleca);

        ProfileDatabase.addNewProfile(mmdurand);
        ProfileDatabase.addNewProfile(jpot);
        ProfileDatabase.addNewProfile(fleca);

        RestaurantDatabase.getRestaurants().add(pizzaCorsica);
        RestaurantDatabase.getRestaurants().add(lebelagio);
        RestaurantDatabase.getRestaurants().add(lebarbusse);

        VisitModel visit0 = new VisitModel(mmdurand, pizzaCorsica,
                LocalDate.of(2019, 5, 2), 800, 16,
                "Pas mal !", 3);
        ContentValues contentValues9 = createVisit(visit0);

        VisitModel visit1 = new VisitModel(mmdurand, lebarbusse,
                LocalDate.of(2019, 4, 2), 1100, 15,
                "Excellent !", 4);
        ContentValues contentValues10 = createVisit(visit1);

        db.insert(VISIT_TABLE_NAME, null, contentValues9);
        db.insert(VISIT_TABLE_NAME, null, contentValues10);

        VisitDatabase.getVisits().add(visit0);
        VisitDatabase.getVisits().add(visit1);

    }

    private ProfileModel createProfileFromRaw(Cursor cursor) {
        int id = cursor.getInt(0);
        String email = cursor.getString(1);
        String password = cursor.getString(2);
        String firstname = cursor.getString(3);
        String surname = cursor.getString(4);
        String birthdate = cursor.getString(5);
        double height = cursor.getDouble(6);
        double weight = cursor.getDouble(7);
        double budget = cursor.getDouble(8);
        Diet diet = Diet.fromName(cursor.getString(9));
        CulinaryFence culinaryFence = CulinaryFence.fromName(cursor.getString(10));

        return new ProfileModel(id, email, password, firstname,
                surname, birthdate, height, weight, budget, diet, culinaryFence);
    }

    private ContentValues createProfile(ProfileModel profileModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_EMAIL, profileModel.getEmail());
        contentValues.put(PROFILE_PASSWORD, profileModel.getPassword());
        contentValues.put(PROFILE_FIRSTNAME, profileModel.getFirstName());
        contentValues.put(PROFILE_SURNAME, profileModel.getSurname());
        contentValues.put(PROFILE_BIRTHDATE, profileModel.getBirthdate());
        contentValues.put(PROFILE_HEIGHT, profileModel.getHeight());
        contentValues.put(PROFILE_WEIGHT, profileModel.getWeight());
        contentValues.put(PROFILE_BUDGET, profileModel.getBudget());
        contentValues.put(PROFILE_DIET, profileModel.getDiet().toString());
        contentValues.put(PROFILE_CULINARYFENCE, profileModel.getCulinaryFence().toString());
        return contentValues;
    }

    private ContentValues createRestaurant(RestaurantModel restaurantModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RESTAURANT_NAME, restaurantModel.getName());
        contentValues.put(RESTAURANT_PRICE, restaurantModel.getPrice());
        contentValues.put(RESTAURANT_CULYN, restaurantModel.getCulinaryFence().toString());
        contentValues.put(RESTAURANT_ADDRESS, restaurantModel.getAddress());
        contentValues.put(RESTAURANT_PHONE, restaurantModel.getPhoneNumber());
        contentValues.put(RESTAURANT_LAT, restaurantModel.getLatitude());
        contentValues.put(RESTAURANT_LONG, restaurantModel.getLongitude());
        return contentValues;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PROFILE_TABLE_DROP);
        db.execSQL(FRIEND_TABLE_DROP);
        db.execSQL(RESTAURANT_TABLE_DROP);
        db.execSQL(VISIT_TABLE_DROP);
        onCreate(db);
    }
}