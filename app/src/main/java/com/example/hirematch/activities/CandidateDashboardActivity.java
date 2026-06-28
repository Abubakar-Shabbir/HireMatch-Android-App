package com.example.hirematch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.CandidateProfile;
import android.widget.ImageButton;
import android.view.View;
public class CandidateDashboardActivity
        extends AppCompatActivity {

    private LinearLayout btnProfile;
    private boolean isProfileComplete = false;
    private LinearLayout btnMyApplications;
    private LinearLayout btnMyInterviews;
    private LinearLayout btnMyOffers;
    private LinearLayout btnATS;
    private ImageButton btnNotifications;
    private TextView tvNotificationBadge;

    private Button btnResume;
    private Button btnJobs;
    private Button btnLogout;

    private TextView tvCandidateName;
    private TextView tvProfileScore;
    private TextView tvApplicationsCount;
    private TextView tvInterviewsCount;
    private TextView tvOffersCount;
    private TextView tvATSScore;

    private ProgressBar progressProfile;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_dashboard);

        if (FirebaseManager.getAuth().getCurrentUser() == null) {
            finish();
            return;
        }

        uid = FirebaseManager.getAuth()
                .getCurrentUser()
                .getUid();

        initViews();
        setupClicks();

        loadProfile();
        loadApplicationsCount();
        loadInterviewsCount();
        loadOffersCount();
        loadNotificationsCount();
    }

    private void initViews() {

        btnProfile = findViewById(R.id.btnProfile);
        btnMyApplications = findViewById(R.id.btnMyApplications);
        btnMyInterviews = findViewById(R.id.btnMyInterviews);
        btnMyOffers = findViewById(R.id.btnMyOffers);
        btnATS = findViewById(R.id.btnATS);
        tvNotificationBadge = findViewById(R.id.tvNotificationBadge);
        btnNotifications = findViewById(R.id.btnNotifications);
        btnResume = findViewById(R.id.btnResume);
        btnJobs = findViewById(R.id.btnJobs);
        btnLogout = findViewById(R.id.btnLogout);

        tvCandidateName = findViewById(R.id.tvCandidateName);
        tvProfileScore = findViewById(R.id.tvProfileScore);
        tvApplicationsCount = findViewById(R.id.tvApplicationsCount);
        tvInterviewsCount = findViewById(R.id.tvInterviewsCount);
        tvOffersCount = findViewById(R.id.tvOffersCount);
        tvATSScore = findViewById(R.id.tvATSScore);

        progressProfile = findViewById(R.id.progressProfile);
    }

    private void setupClicks() {

        // Profile (Always Open)
        btnProfile.setOnClickListener(v ->
                startActivity(new Intent(
                        this,
                        CandidateProfileActivity.class)));

        // Resume
        btnResume.setOnClickListener(v -> {

            if (!isProfileComplete) {
                showProfileLockMessage();
                return;
            }

            startActivity(new Intent(
                    this,
                    UploadResumeActivity.class));
        });

        // Browse Jobs
        btnJobs.setOnClickListener(v -> {

            if (!isProfileComplete) {
                showProfileLockMessage();
                return;
            }

            startActivity(new Intent(
                    this,
                    JobListingActivity.class));
        });

        // My Applications
        btnMyApplications.setOnClickListener(v -> {

            if (!isProfileComplete) {
                showProfileLockMessage();
                return;
            }

            startActivity(new Intent(
                    this,
                    MyApplicationsActivity.class));
        });

        // My Interviews
        btnMyInterviews.setOnClickListener(v -> {

            if (!isProfileComplete) {
                showProfileLockMessage();
                return;
            }

            startActivity(new Intent(
                    this,
                    MyInterviewsActivity.class));
        });

        // My Offers
        btnMyOffers.setOnClickListener(v -> {

            if (!isProfileComplete) {
                showProfileLockMessage();
                return;
            }

            startActivity(new Intent(
                    this,
                    MyOffersActivity.class));
        });

        // Notifications
        btnNotifications.setOnClickListener(v -> {

            if (!isProfileComplete) {
                showProfileLockMessage();
                return;
            }

            startActivity(new Intent(
                    this,
                    NotificationsActivity.class));
        });

        // ATS Score
        btnATS.setOnClickListener(v -> {

            if (!isProfileComplete) {
                showProfileLockMessage();
                return;
            }

            startActivity(new Intent(
                    this,
                    ATSScoreActivity.class));
        });

        // Logout (Always Open)
        btnLogout.setOnClickListener(v -> {

            FirebaseManager.getAuth().signOut();

            startActivity(new Intent(
                    this,
                    LoginActivity.class));

            finish();
        });
    }

    private void showProfileLockMessage() {

        Toast.makeText(
                this,
                "Complete your profile to 100% first.",
                Toast.LENGTH_SHORT
        ).show();
    }
    private void loadProfile() {

        FirebaseManager.getFirestore()
                .collection("candidate_profiles")
                .document(uid)
                .get()
                .addOnSuccessListener(document -> {

                    if (document.exists()) {

                        System.out.println("Document Data = " + document.getData());

                        CandidateProfile profile =
                                document.toObject(CandidateProfile.class);

                        if (profile != null) {

//                            Toast.makeText(this,
//                                    "Firestore Score = " + profile.getProfileScore(),
//                                    Toast.LENGTH_LONG).show();

                            tvCandidateName.setText("Welcome, " + profile.getName());

                            tvProfileScore.setText(profile.getProfileScore() + "%");

                            progressProfile.setProgress(profile.getProfileScore());

                            tvATSScore.setText(profile.getAtsScore() + "%");
                            int score = profile.getProfileScore();


                            isProfileComplete = score >= 100;

                            updateButtonState();
                        }
                    }
                });
    }
    private void updateButtonState() {

        btnProfile.setEnabled(true);
        btnProfile.setAlpha(1f);

        btnLogout.setEnabled(true);
        btnLogout.setAlpha(1f);

        btnResume.setEnabled(isProfileComplete);
        btnResume.setAlpha(isProfileComplete ? 1f : 0.4f);

        btnJobs.setEnabled(isProfileComplete);
        btnJobs.setAlpha(isProfileComplete ? 1f : 0.4f);

        btnMyApplications.setEnabled(isProfileComplete);
        btnMyApplications.setAlpha(isProfileComplete ? 1f : 0.4f);

        btnMyInterviews.setEnabled(isProfileComplete);
        btnMyInterviews.setAlpha(isProfileComplete ? 1f : 0.4f);

        btnMyOffers.setEnabled(isProfileComplete);
        btnMyOffers.setAlpha(isProfileComplete ? 1f : 0.4f);

        btnATS.setEnabled(isProfileComplete);
        btnATS.setAlpha(isProfileComplete ? 1f : 0.4f);

        btnNotifications.setEnabled(isProfileComplete);
        btnNotifications.setAlpha(isProfileComplete ? 1f : 0.4f);
    }
    private void loadApplicationsCount() {

        FirebaseManager.getFirestore()
                .collection("applications")
                .whereEqualTo("candidateId", uid)
                .get()
                .addOnSuccessListener(query ->
                        tvApplicationsCount.setText(
                                String.valueOf(query.size())
                        ));
    }

    private void loadInterviewsCount() {

        FirebaseManager.getFirestore()
                .collection("interviews")
                .whereEqualTo("candidateId", uid)
                .get()
                .addOnSuccessListener(query ->
                        tvInterviewsCount.setText(
                                String.valueOf(query.size())
                        ));
    }

    private void loadOffersCount() {

        FirebaseManager.getFirestore()
                .collection("offers")
                .whereEqualTo("candidateId", uid)
                .get()
                .addOnSuccessListener(query ->
                        tvOffersCount.setText(
                                String.valueOf(query.size())
                        ));
    }

    private void loadNotificationsCount() {

        FirebaseManager.getFirestore()
                .collection("notifications")
                .whereEqualTo("userId", uid)
                .whereEqualTo("isRead", false)
                .get()
                .addOnSuccessListener(query -> {

                    int count = query.size();

                    if (count > 0) {

                        tvNotificationBadge.setVisibility(View.VISIBLE);
                        tvNotificationBadge.setText(String.valueOf(count));

                    } else {

                        tvNotificationBadge.setVisibility(View.GONE);
                    }

                });
    }}