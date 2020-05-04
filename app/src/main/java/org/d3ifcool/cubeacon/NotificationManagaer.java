package org.d3ifcool.cubeacon;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import org.d3ifcool.cubeacon.models.Event;

import androidx.core.app.NotificationCompat;

public class NotificationManagaer {

    private Context context;
    private NotificationManager notificationManager;
    private Notification helloNotificationBlueberry;
    private Notification helloNotificationCoconut;
    private Notification helloNotificationIce;
    private Notification helloNotificationMint;
    private Notification goodbyeNotificationBlueberry;
    private int notificationId = 1;

    public NotificationManagaer(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        this.helloNotificationBlueberry = buildNotification("Mip Map", "Blueberry : check out the events around you",true);
        this.helloNotificationCoconut= buildNotification("Mip Map", "Coconut : check out the events around you",true);
        this.helloNotificationIce= buildNotification("Mip Map", "Ice : check out the events around you",true);
        this.helloNotificationMint= buildNotification("Mip Map", "Mint: check out the events around you",true);
    }

    private Notification buildNotification(String title, String text, boolean in) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel contentChannel = new NotificationChannel(
                    "content_channel", "Things near you", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(contentChannel);
        }

        return new NotificationCompat.Builder(context, "content_channel")
//                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setSmallIcon(R.drawable.logo_with_text)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(context, 0,
                        new Intent(context, EventActivity.class).putExtra("beacon",in), PendingIntent.FLAG_UPDATE_CURRENT))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
    }

    public void enterBlueberry(){
        notificationManager.notify(notificationId, helloNotificationBlueberry);
    }

    public void enterCoconut(){
        notificationManager.notify(notificationId, helloNotificationCoconut);
    }

    public void enterMint(){
        notificationManager.notify(notificationId, helloNotificationMint);
    }

    public void enterIce(){
        notificationManager.notify(notificationId, helloNotificationIce);
    }

}
