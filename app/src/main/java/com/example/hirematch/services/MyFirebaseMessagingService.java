package com.example.hirematch.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService
        extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        Log.d("FCM_TOKEN", token);
    }

    @Override
    public void onMessageReceived(
            RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(
                "FCM_MESSAGE",
                remoteMessage.getNotification().getBody()
        );
    }
}