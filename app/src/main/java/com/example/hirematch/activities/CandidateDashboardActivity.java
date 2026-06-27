package com.example.hirematch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.CandidateProfile;

public class CandidateDashboardActivity
        extends AppCompatActivity {

    private LinearLayout btnProfile;
    private LinearLayout btnMyApplications;
    private LinearLayout btnMyInterviews;
    private LinearLayout btnMyOffers;
    private LinearLayout btnATS;

    private Button btnNotifications;
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

        btnProfile.setOnClickListener(v ->
                startActivity(new Intent(
                        this,
                        CandidateProfileActivity.class)));

        btnResume.setOnClickListener(v ->
                startActivity(new Intent(
                        this,
                        UploadResumeActivity.class)));

        btnJobs.setOnClickListener(v ->
                startActivity(new Intent(
                        this,
                        JobListingActivity.class)));

        btnMyApplications.setOnClickListener(v ->
                startActivity(new Intent(
                        this,
                        MyApplicationsActivity.class)));

        btnMyInterviews.setOnClickListener(v ->
                startActivity(new Intent(
                        this,
                        MyInterviewsActivity.class)));

        btnMyOffers.setOnClickListener(v ->
                startActivity(new Intent(
                        this,
                        MyOffersActivity.class)));

        btnNotifications.setOnClickListener(v ->
                startActivity(new Intent(
                        this,
                        NotificationsActivity.class)));

        btnATS.setOnClickListener(v ->
                startActivity(new Intent(
                        this,
                        ATSScoreActivity.class)));

        btnLogout.setOnClickListener(v -> {

            FirebaseManager.getAuth().signOut();

            startActivity(new Intent(
                    this,
                    LoginActivity.class));

            finish();
        });
    }

    private void loadProfile() {

        FirebaseManager.getFirestore()
                .collection("candidate_profiles")
                .document(uid)
                .get()
                .addOnSuccessListener(document -> {

                    if (document.exists()) {

                        CandidateProfile profile =
                                document.toObject(
                                        CandidateProfile.class
                                );

                        if (profile != null) {

                            tvCandidateName.setText(
                                    "Welcome, " + profile.getName()
                            );

                            int score =
                                    profile.getProfileScore();

                            tvProfileScore.setText(
                                    score + "%"
                            );

                            progressProfile.setProgress(score);

                            tvATSScore.setText(
                                    profile.getProfileScore() + "%"
                            );
                        }
                    }
                });
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
                .addOnSuccessListener(query ->
                        btnNotifications.setText(
                                String.valueOf(query.size())
                        ));
    }
}