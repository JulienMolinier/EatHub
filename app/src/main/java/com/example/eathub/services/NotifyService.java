package com.example.eathub.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.eathub.R;
import com.example.eathub.activities.MainActivity;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.VisitModel;
import com.example.eathub.models.databases.ProfileDatabase;
import com.example.eathub.models.databases.VisitDatabase;

import java.time.LocalDate;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Lydia BARAUKOVA
 */
public class NotifyService extends Service {

    // channel data
    public static final String CHANNEL_ID = "Channel EatHub", CHANNEL_TITLE = "Warning";

    // possible actions for the receiver declared in the manifest
    public static final String SEND_NOTIFICATION = "send notification",
            STOP_NOTIFY_SERVICE = "stop notify service";

    private NotificationManager notificationManager;
    private ProfileModel connectedProfile;
    private Timer timer;
    private boolean notifiedThatYearlyBudgetSurpassed,
            notifiedThatMonthlyBudgetSurpassed,
            notifiedThatDailyBudgetSurpassed,
            notifiedThatDailyCaloriesSurpassed;

    public static class NotifyServiceReceiver extends BroadcastReceiver {
        private int NOTIFY_ID = 1;
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case STOP_NOTIFY_SERVICE:
                    NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    nm.cancelAll();
                    context.stopService(new Intent(context, NotifyService.class));
                    break;
                case SEND_NOTIFICATION:
                    String message = intent.getStringExtra("message");
                    createNotification(message, context);
            }
        }

        public void createNotification(String message, Context context) {
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intent;
            PendingIntent pendingIntent;
            NotificationCompat.Builder builder;
            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(message)                            // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(context.getString(R.string.app_name))                           // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(message)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);           // for older versions
            Notification notification = builder.build();
            nm.notify(NOTIFY_ID, notification);
            NOTIFY_ID++;
        }
    }

    @Override
    public void onCreate() { // called once the service is created
        super.onCreate();
        timer = new Timer();
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) { // called with every pending intent
        createNotificationChannel();
        connectedProfile = intent.getParcelableExtra("currentProfile");
        // boolean variables to avoid notifying several times about the same thing
        notifiedThatYearlyBudgetSurpassed = false;
        notifiedThatMonthlyBudgetSurpassed = false;
        notifiedThatDailyBudgetSurpassed = false;
        notifiedThatDailyCaloriesSurpassed = false;
        final int UPDATE_INTERVAL = 10 * 1000; // check for updates every 10 seconds
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() { // checks if there are updates here and notifies if true
                connectedProfile = ProfileDatabase.getProfile(connectedProfile.getEmail());
                checkBudget();
                checkCalories();
            }
        }, 0, UPDATE_INTERVAL);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
    }

    public void createNotificationChannel() {
        NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = nm.getNotificationChannel(CHANNEL_ID);
            if (channel == null) {
                channel = new NotificationChannel(CHANNEL_ID, CHANNEL_TITLE, importance);
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                nm.createNotificationChannel(channel);
            }
        }
    }

    private void sendNotification(String message) {
        Intent intent = new Intent(new Intent(this, NotifyService.NotifyServiceReceiver.class));
        intent.putExtra("message", message);
        intent.setAction(NotifyService.SEND_NOTIFICATION);
        sendBroadcast(intent);
    }

    private void checkBudget() {
        LocalDate today = LocalDate.now();
        double dailyBudget = connectedProfile.getBudget();
        double monthlyBudget = dailyBudget * today.lengthOfMonth();
        double yearlyBudget = dailyBudget * today.lengthOfYear();
        List<VisitModel> visits = VisitDatabase.getVisitsByProfile(connectedProfile);
        double spentThisYear = 0, spentThisMonth = 0, spentToday = 0;
        for (VisitModel visit: visits) {
            LocalDate visitDate = visit.getDate();
            double visitPrice = visit.getPrice();
            if (visitDate.getYear() == today.getYear()) {
                spentThisYear += visitPrice;
                if (visitDate.getMonth() == today.getMonth()) {
                    spentThisMonth += visitPrice;
                    if (visitDate.getDayOfMonth() == today.getDayOfMonth()) {
                        spentToday += visitPrice;
                    }
                }
            }
        }
        if (spentThisYear > yearlyBudget && !notifiedThatYearlyBudgetSurpassed) {
            sendNotification(getString(R.string.surpassedYearlyBudgetNotification));
            notifiedThatYearlyBudgetSurpassed = true;
            try { Thread.sleep(7 * 1000); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
        if (spentThisMonth > monthlyBudget && !notifiedThatMonthlyBudgetSurpassed) {
            sendNotification(getString(R.string.surpassedMonthlyBudgetNotification));
            notifiedThatMonthlyBudgetSurpassed = true;
            try { Thread.sleep(7 * 1000); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
        if (spentToday > dailyBudget && !notifiedThatDailyBudgetSurpassed) {
            sendNotification(getString(R.string.surpassedDailyBudgetNotification));
            notifiedThatDailyBudgetSurpassed = true;
            try { Thread.sleep(7 * 1000); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private void checkCalories() {

        /*
        double dailyCalorieNeeds;
        double weight = connectedProfile.getWeight();
        double height = connectedProfile.getHeight();
        // String gender = connectedProfile.getGender();
        int age = connectedProfile.getAge();
        // To calculate calorie needs for basic metabolism we:
        // - multiply the weight in kg by 10
        dailyCalorieNeeds = weight * 10;
        // - multiply the height in cm by 6.25
        // - add the results of two previous operations
        dailyCalorieNeeds += (height * 6.25);
        // - multiply the age by 5 and substract it from the previous result
        dailyCalorieNeeds -= (age * 5);
        // - finally, we substract 161 for a woman and add 5 for a man
        switch (gender) {
            case "F": dailyCalorieNeeds -= 161; break;
            case "M": dailyCalorieNeeds += 5; break;
            default: System.out.println(gender); break;
        }
        // source: https://www.passionsante.be/index.cfm?fuseaction=art&art_id=13657
        */

        LocalDate today = LocalDate.now();
        List<VisitModel> visits = VisitDatabase.getVisitsByProfile(connectedProfile);
        double dailyNeed = connectedProfile.getWeight() * 24 * 1.5; // same formula as in profile model
        double consumedToday = 0;
        for (VisitModel visit: visits) {
            if (visit.getDate().equals(today)) {
                consumedToday += visit.getCalories();
            }
        }
        if (consumedToday > dailyNeed && !notifiedThatDailyCaloriesSurpassed) {
            sendNotification(getString(R.string.surpassedDailyCaloriesNotification));
            notifiedThatDailyCaloriesSurpassed = true;
            try { Thread.sleep(7 * 1000); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
