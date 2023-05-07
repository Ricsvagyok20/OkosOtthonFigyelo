package com.example.okosotthonfigyelo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {
    private NotificationManager manager;
    private Context context;
    private static final String CHANNEL_ID = "smart_home_watcher_notification_channel";
    private final int NOTIFICATION_ID = 0;

    public NotificationHandler(Context context) {
        this.context = context;
        this.manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createChannel();
    }

    private void createChannel(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            return;
        }
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Smart Home Watcher", NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(Color.BLUE);
        channel.setDescription("Notifications from Smart Home Watcher.");
        this.manager.createNotificationChannel(channel);
    }

    public void send(String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Smart Home Watcher")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notification_icon);
        this.manager.notify(NOTIFICATION_ID, builder.build());
    }
}
