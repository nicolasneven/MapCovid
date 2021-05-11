package com.example.mapcovid;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String CHANNEL_ID = "234";

        //PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, new Intent(), 0);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("10AM MapCovid Notification")
                .setContentText("3552 cases at your location!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                //.setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(soundUri);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context.getApplicationContext());

        int notificationId = 3;
        // notificationId is a unique int for each notification that you must define

        notificationManager.notify(notificationId, builder.build());
    }
}
