package com.androidlearning.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;

//background service
//Service: define Service in AndroidManifest.xml
public class MusicPlayService extends Service {

    private MediaPlayer player;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //play the audio from default system alarm
        player = MediaPlayer.create(getApplicationContext(), Settings.System.DEFAULT_ALARM_ALERT_URI);

        //play the audio continuously
        player.setLooping(true);

        player.start();

        /**
         * START_STICKY: indicate the service should be restarted if its terminated by the system
         *         after it has been started
         *         the system will recreate the service with a null intent, and
         *         need to handle reinitialization of any necessary resources
         *   Use this return type if a service performs periodic tasks such as:
         *   background data synchronization, where you want to ensure that the service
         *   resumes operation after being temporary killed
         *
         *   START_NOT_STICKY: indicate the service should not be restarted if its terminated by the system
         *                  after it has been terminated
         *                  The service will remain stopped unless explicitly started again by the application
         *                  or component
         *    Suitable for services that perform specific tasks and dont need to be running continuously
         *    such as:
         *    service that plays a sound effect when a notification is received
         */
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //not bind to any components
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //stop player and release resources
        player.stop();
    }
}
