package com.example.eathub.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.eathub.R;
import com.example.eathub.activities.MainActivity;

/**
 * @author Lydia BARAUKOVA
 */
public class NotifyService extends Service {

    // channel data
    public static final String CHANNEL_ID = "Channel EatHub", CHANNEL_TITLE = "Warning";

    // notification data
    public static final int NOTIFY_ID = 888888;

    // actions
    public final static String REGISTER_RECEIVER = "register receiver",
            STOP_SERVICE = "stop service",
            SURPASSED_BUDGET_LIMIT = "surpassed budget limit",
            SURPASSED_CALORIES_LIMIT = "surpassed calories limit";

    // broadcast key and values
    public final static String SERVICE_BROADCAST_KEY = "EatHubNotifyService";
    public final static int RQS_STOP_SERVICE = 1, RQS_SEND_SERVICE = 2;

    private NotifyServiceReceiver receiver;
    private NotificationManager notificationManager;

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(receiver);
    }

    @Override
    public void onCreate() { // called once the service is created
        super.onCreate();
        if (notificationManager == null) {
            notificationManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        createNotificationChannel();
        receiver = new NotifyServiceReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(REGISTER_RECEIVER);
        intentFilter.addAction(STOP_SERVICE);
        intentFilter.addAction(SURPASSED_BUDGET_LIMIT);
        intentFilter.addAction(SURPASSED_CALORIES_LIMIT);
        registerReceiver(receiver, intentFilter);
        System.out.println("CREATED NOTIFY SERVICE");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) { // called with every pending intent
        System.out.println("STARTED NOTIFY SERVICE");
        createNotification("Message1",getApplicationContext());
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
                .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
                .setContentText(context.getString(R.string.app_name)) // required
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setTicker(message)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        Notification notification = builder.build();
        notificationManager.notify(NOTIFY_ID, notification);
    }

    public class NotifyServiceReceiver extends BroadcastReceiver {

        public NotifyServiceReceiver() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("NOTIFY SERVICE RECEIVED AN INTENT");
            int rqs = intent.getIntExtra(SERVICE_BROADCAST_KEY,0);
            switch (rqs) {
                case RQS_STOP_SERVICE:
                    notificationManager.cancelAll(); // we erase all the notifications
                    stopSelf(); // and stop the service
                    break;
                case RQS_SEND_SERVICE:
                    String message = "empty message";
                    switch (intent.getAction()) {
                        case SURPASSED_BUDGET_LIMIT:
                            message = getString(R.string.surpassedBudgetNotification);
                            break;
                        case SURPASSED_CALORIES_LIMIT:
                            message = getString(R.string.surpassedCaloriesNotification);
                            break;
                        default:
                            break;
                    }
                    createNotification(message,getApplicationContext());
                default:
                    break;
            }
        }
    }
}
