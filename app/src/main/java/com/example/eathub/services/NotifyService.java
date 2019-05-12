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

    // notification data
    public static final int NOTIFY_ID = 888888;

    // actions
    public static final String SEND_NOTIFICATION = "send notification",
            STOP_NOTIFY_SERVICE = "stop notify service";

    private NotificationManager notificationManager;
    private NotifyServiceReceiver receiver;
    private ProfileModel connectedProfile;
    private Timer timer;
    private boolean notifiedThatYearlyBudgetSurpassed,
            notifiedThatMonthlyBudgetSurpassed,
            notifiedThatDailyBudgetSurpassed;

    public static class NotifyServiceReceiver extends BroadcastReceiver {
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
                    sendNotification(message, context);
            }
        }

        public void sendNotification(String message, Context context) {
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
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
        }
    }

    @Override
    public void onCreate() { // called once the service is created
        super.onCreate();
        timer = new Timer();
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        createNotificationChannel();
        receiver = new NotifyServiceReceiver(); // static, registered in the manifest
        System.out.println("CREATED NOTIFY SERVICE");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) { // called with every pending intent
        final int UPDATE_INTERVAL = 60 * 1000;
        System.out.println("STARTED NOTIFY SERVICE");
        connectedProfile = intent.getParcelableExtra("currentProfile");

        // boolean variables to avoid notifying several times about the same thing
        notifiedThatYearlyBudgetSurpassed = false;
        notifiedThatMonthlyBudgetSurpassed = false;
        notifiedThatDailyBudgetSurpassed = false;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Check if there are updates here and notify if true
                connectedProfile = ProfileDatabase.getProfile(connectedProfile.getEmail()); // was ProfileDatabase updated though?
                checkBudget();
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
        if (receiver != null) unregisterReceiver(receiver);
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = notificationManager.getNotificationChannel(CHANNEL_ID);
            if (channel == null) {
                channel = new NotificationChannel(CHANNEL_ID, CHANNEL_TITLE, importance);
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public void createNotification(String message, Context context) {
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
        notificationManager.notify(NOTIFY_ID, notification);
    }

    private void checkBudget() {
        LocalDate today = LocalDate.now();
        double dailyBudget = connectedProfile.getBudget();
        double monthlyBudget = dailyBudget * today.lengthOfMonth();
        double yearlyBudget = dailyBudget * today.lengthOfYear();
        List<VisitModel> visits = connectedProfile.getHistory();
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
            createNotification(getString(R.string.surpassedYearlyBudgetNotification), getApplicationContext());
            notifiedThatYearlyBudgetSurpassed = true;
        }
        if (spentThisMonth > monthlyBudget && !notifiedThatMonthlyBudgetSurpassed
                && !notifiedThatYearlyBudgetSurpassed) {
            createNotification(getString(R.string.surpassedMonthlyBudgetNotification), getApplicationContext());
            notifiedThatMonthlyBudgetSurpassed = true;
        }
        if (spentToday > dailyBudget && !notifiedThatDailyBudgetSurpassed
                && !notifiedThatMonthlyBudgetSurpassed && !notifiedThatYearlyBudgetSurpassed) {
            createNotification(getString(R.string.surpassedDailyBudgetNotification), getApplicationContext());
            notifiedThatDailyBudgetSurpassed = true;
        }
    }
}
