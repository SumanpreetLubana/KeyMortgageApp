package com.example.smartserve.keymortgageapp.Util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.smartserve.keymortgageapp.LoginActivity;
import com.example.smartserve.keymortgageapp.R;
import com.example.smartserve.keymortgageapp.UserDashboardActivity;
import com.google.firebase.messaging.FirebaseMessagingService;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by jwick on 8/1/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    SessionManager sessionManager;
    String keyuserId=null;
    HashMap<String, String> user;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
         sessionManager =new SessionManager(this);
        Log.d("Msg", "Message received ["+remoteMessage+"]");
        user = new HashMap<String, String>();
        user= sessionManager.getUserDetails();
        keyuserId = user.get("userID");
        // Create Notification
        if(keyuserId!=null && !(keyuserId.equals(""))) {
            Intent intent = new Intent(this, UserDashboardActivity.class);
            sessionManager.createLoginSession("1","is_notification");
            intent.putExtra("isNotify","1");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 1410,
                    intent, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notificationBuilder = new
                    NotificationCompat.Builder(this)

                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("Key Mortgage")
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
            Log.d(TAG, "From: " + remoteMessage.getFrom());
            Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

            NotificationManager notificationManager =
                    (NotificationManager)
                            getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(1410, notificationBuilder.build());
        }else{
            sessionManager.createLoginSession("1","is_notification");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}