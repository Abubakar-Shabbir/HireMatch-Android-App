package com.example.hirematch.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hirematch.R;
import com.example.hirematch.adapters.NotificationAdapter;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Notification;
import com.google.firebase.firestore.DocumentChange;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {


    private RecyclerView rvNotifications;

    private ArrayList<Notification> notificationList;
    private NotificationAdapter adapter;

    private static final String CHANNEL_ID =
            "hirematch_notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        initViews();
        setupRecyclerView();
        createNotificationChannel();
        listenNotifications();
    }

    private void initViews() {

        rvNotifications =
                findViewById(
                        R.id.rvNotifications
                );
    }

    private void setupRecyclerView() {

        notificationList =
                new ArrayList<>();

        adapter =
                new NotificationAdapter(
                        this,
                        notificationList
                );

        rvNotifications.setLayoutManager(
                new LinearLayoutManager(this)
        );

        rvNotifications.setAdapter(adapter);
    }

    private void listenNotifications() {

        String userId =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        FirebaseManager.getFirestore()
                .collection("notifications")
                .whereEqualTo(
                        "userId",
                        userId
                )
                .addSnapshotListener((value, error) -> {

                    if (value == null)
                        return;

                    for (DocumentChange dc :
                            value.getDocumentChanges()) {

                        Notification notification =
                                dc.getDocument()
                                        .toObject(
                                                Notification.class
                                        );

                        if (dc.getType() ==
                                DocumentChange.Type.ADDED) {

                            notificationList.add(
                                    notification
                            );

                            showLocalNotification(
                                    notification.getTitle(),
                                    notification.getMessage()
                            );

                            FirebaseManager.getFirestore()
                                    .collection("notifications")
                                    .document(
                                            notification.getNotificationId()
                                    )
                                    .update(
                                            "isRead",
                                            true
                                    );
                        }
                    }

                    adapter.notifyDataSetChanged();
                });
    }

    private void showLocalNotification(
            String title,
            String message) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(
                        this,
                        CHANNEL_ID
                )
                        .setSmallIcon(
                                R.mipmap.ic_launcher
                        )
                        .setContentTitle(
                                title
                        )
                        .setContentText(
                                message
                        )
                        .setPriority(
                                NotificationCompat.PRIORITY_HIGH
                        )
                        .setAutoCancel(true);

        NotificationManager manager =
                (NotificationManager)
                        getSystemService(
                                NOTIFICATION_SERVICE
                        );

        manager.notify(
                (int) System.currentTimeMillis(),
                builder.build()
        );
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.O) {

            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            "HireMatch Notifications",
                            NotificationManager.IMPORTANCE_HIGH
                    );

            channel.setDescription(
                    "HireMatch Candidate Notifications"
            );

            NotificationManager manager =
                    getSystemService(
                            NotificationManager.class
                    );

            if (manager != null) {

                manager.createNotificationChannel(
                        channel
                );
            }
        }
    }


}
