package org.d3ifcool.cubeacon;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class NotificationManagaer {

    private Context context;
    private NotificationManager notificationManager;
    private Notification helloNotificationBlueberry;
    private Notification helloNotificationCoconut;
    private Notification goodbyeNotificationBlueberry;
    private int notificationId = 1;

    public NotificationManagaer(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        this.goodbyeNotificationBlueberry = buildNotification("Blueberry out", "You're outside the beacon area");

        this.helloNotificationBlueberry = buildNotification("Mip Map", "check out the events around you");
        this.helloNotificationCoconut= buildNotification("Mip Map", "check out the events around you");
    }

    private Notification buildNotification(String title, String text) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel contentChannel = new NotificationChannel(
                    "content_channel", "Things near you", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(contentChannel);
        }

        return new NotificationCompat.Builder(context, "content_channel")
//                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setSmallIcon(R.drawable.icon_mipmap)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(PendingIntent.getActivity(context, 0,
                        new Intent(context, EventActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
    }

    public void enterBlueberry(){
        notificationManager.notify(notificationId, helloNotificationBlueberry);
    }

    public void enterCoconut(){
        notificationManager.notify(notificationId, helloNotificationCoconut);
    }

    public void exitBlueberry(){
        notificationManager.notify(notificationId, goodbyeNotificationBlueberry);
    }

}
