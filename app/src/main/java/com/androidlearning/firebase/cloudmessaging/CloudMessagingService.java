package com.androidlearning.firebase.cloudmessaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.androidlearning.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * https://firebase.google.com/docs/cloud-messaging/android/client
 *
 *
 */
public class CloudMessagingService extends FirebaseMessagingService {

    private static final String TAG = "CloudMessagingService";

    /**
     * 2 types of messages:
     *
     * DataMessage and NotificationMessage
     *
     * DataMessage is handled at onMessageReceived, whether the app is in foreground or background
     *
     * NotificationMessage are only received at onMessageReceived when the app is in foreground
     *
     */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        Log.v(TAG, "From: " + message.getFrom());

        //Check if message has the Data Payload
        if(!message.getData().isEmpty()) {
            Log.v(TAG, "Message Data Payload: " + message.getData());

            //If data needs to be processed by a long running job
            if(true) {
                scheduleJob();
            } else {
                handleNow();
            }
        }

        //Check if message has the Notification Payload
        if(message.getNotification() != null) {
            Log.v(TAG, "Notification Payload: " + message.getNotification().getBody());
        }
    }

    /**
     * If want to send messages to this application instance or manage app subscription on
     * the server side, send the FCM registration token to the app server
     */
    @Override
    public void onNewToken(@NonNull String token) {
        sendRegistrationTokenToServer(token);
    }

    //Schedule async work using Work Manager
    private void scheduleJob() {
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(ScheduleWorker.class).build();
        WorkManager.getInstance(this).beginWith(work).enqueue();
    }

    //Broadcast Receiver
    private void handleNow() {
        Log.d(TAG, "Short Lived Task");
    }

    private void sendRegistrationTokenToServer(String token) {
        Log.d(TAG, "onNewToken: " + token);
    }

    private void sendNotification(String msg) {
        Intent intent = new Intent(this, FCMActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                this, channelId)
                .setSmallIcon(R.drawable.cloud_messaging_bell_icon)
                .setContentTitle(getString(R.string.fcm_message))
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //SDK lvl >= 26
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(channelId, "Name of the Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }
}
