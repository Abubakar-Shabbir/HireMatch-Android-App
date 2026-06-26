package com.example.hirematch.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Notification;
import com.example.hirematch.utils.SharedPrefManager;
import com.google.firebase.firestore.DocumentChange;

public class CandidateDashboardActivity
        extends AppCompatActivity {

    private Button btnProfile;
    private Button btnResume;
    private Button btnATS;
    private Button btnJobs;
    private Button btnMyApplications;
    private Button btnMyInterviews;
    private Button btnNotifications;
    private Button btnLogout;

    private static final String CHANNEL_ID =
            "hirematch_notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_dashboard);

        initViews();
        setupListeners();
        createNotificationChannel();
        loadUnreadCount();
        listenForRealtimeNotifications();
    }

    private void initViews() {

        btnProfile =
                findViewById(R.id.btnProfile);

        btnResume =
                findViewById(R.id.btnResume);

        btnATS =
                findViewById(R.id.btnATS);

        btnJobs =
                findViewById(R.id.btnJobs);

        btnMyApplications =
                findViewById(R.id.btnMyApplications);

        btnMyInterviews =
                findViewById(R.id.btnMyInterviews);

        btnNotifications =
                findViewById(R.id.btnNotifications);

        btnLogout =
                findViewById(R.id.btnLogout);
    }

    private void setupListeners() {

        btnProfile.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                CandidateProfileActivity.class
                        )
                )
        );

        btnResume.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                UploadResumeActivity.class
                        )
                )
        );

        btnATS.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                ATSScoreActivity.class
                        )
                )
        );

        btnJobs.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                JobListingActivity.class
                        )
                )
        );

        btnMyApplications.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                MyApplicationsActivity.class
                        )
                )
        );

        btnMyInterviews.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                MyInterviewsActivity.class
                        )
                )
        );

        btnNotifications.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                NotificationsActivity.class
                        )
                )
        );

        btnLogout.setOnClickListener(v -> {

            FirebaseManager.getAuth().signOut();

            SharedPrefManager pref =
                    new SharedPrefManager(this);

            pref.logout();

            startActivity(
                    new Intent(
                            this,
                            LoginActivity.class
                    )
            );

            finish();
        });
    }

    private void loadUnreadCount() {

        String userId =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        FirebaseManager.getFirestore()
                .collection("notifications")
                .whereEqualTo("userId", userId)
                .whereEqualTo("isRead", false)
                .get()
                .addOnSuccessListener(query -> {

                    int count = query.size();

                    btnNotifications.setText(
                            "🔔 Notifications (" +
                                    count + ")"
                    );
                });
    }

    private void listenForRealtimeNotifications() {

        String userId =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        FirebaseManager.getFirestore()
                .collection("notifications")
                .whereEqualTo("userId", userId)
                .whereEqualTo("isRead", false)
                .addSnapshotListener((value, error) -> {

                    if (value == null)
                        return;

                    for (DocumentChange dc :
                            value.getDocumentChanges()) {

                        if (dc.getType() ==
                                DocumentChange.Type.ADDED) {

                            Notification notification =
                                    dc.getDocument()
                                            .toObject(
                                                    Notification.class
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

                            loadUnreadCount();
                        }
                    }
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
                        .setContentTitle(title)
                        .setContentText(message)
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