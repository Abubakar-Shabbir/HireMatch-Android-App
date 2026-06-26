package com.example.hirematch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Application;
import com.example.hirematch.models.Notification;

public class ApplicantDetailsActivity extends AppCompatActivity {

    private TextView tvCandidateName;
    private TextView tvEmail;
    private TextView tvATSScore;

    private Button btnShortlist;
    private Button btnReject;

    private String applicationId;
    private Application currentApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_details);

        applicationId =
                getIntent().getStringExtra(
                        "applicationId"
                );

        initViews();
        loadApplicant();
    }

    private void initViews() {

        tvCandidateName =
                findViewById(
                        R.id.tvCandidateName
                );

        tvEmail =
                findViewById(
                        R.id.tvEmail
                );

        tvATSScore =
                findViewById(
                        R.id.tvATSScore
                );

        btnShortlist =
                findViewById(
                        R.id.btnShortlist
                );

        btnReject =
                findViewById(
                        R.id.btnReject
                );

        btnShortlist.setOnClickListener(v ->
                updateStatus("Shortlisted")
        );

        btnReject.setOnClickListener(v ->
                updateStatus("Rejected")
        );
    }

    private void loadApplicant() {

        FirebaseManager.getFirestore()
                .collection("applications")
                .document(applicationId)
                .get()
                .addOnSuccessListener(document -> {

                    currentApplication =
                            document.toObject(
                                    Application.class
                            );

                    if (currentApplication != null) {

                        currentApplication.setApplicationId(
                                document.getId()
                        );

                        tvCandidateName.setText(
                                currentApplication.getCandidateName()
                        );

                        tvEmail.setText(
                                currentApplication.getCandidateEmail()
                        );

                        tvATSScore.setText(
                                "ATS Score: " +
                                        currentApplication.getAtsScore() + "%"
                        );
                    }
                });
    }

    private void updateStatus(String status) {

        FirebaseManager.getFirestore()
                .collection("applications")
                .document(applicationId)
                .update(
                        "applicationStatus",
                        status
                )
                .addOnSuccessListener(unused -> {

                    createNotification(status);

                    if (status.equals("Shortlisted")) {

                        Intent intent =
                                new Intent(
                                        this,
                                        ScheduleInterviewActivity.class
                                );

                        intent.putExtra(
                                "applicationId",
                                currentApplication.getApplicationId()
                        );

                        intent.putExtra(
                                "candidateId",
                                currentApplication.getCandidateId()
                        );

                        intent.putExtra(
                                "jobId",
                                currentApplication.getJobId()
                        );

                        intent.putExtra(
                                "hrId",
                                currentApplication.getHrId()
                        );

                        intent.putExtra(
                                "jobTitle",
                                currentApplication.getJobTitle()
                        );

                        startActivity(intent);

                    } else {

                        Toast.makeText(
                                this,
                                "Candidate Rejected",
                                Toast.LENGTH_SHORT
                        ).show();

                        finish();
                    }
                });
    }

    private void createNotification(String status) {

        String notificationId =
                FirebaseManager.getFirestore()
                        .collection("notifications")
                        .document()
                        .getId();

        Notification notification =
                new Notification(
                        notificationId,
                        currentApplication.getCandidateId(),
                        "Application Update",
                        "Your application for " +
                                currentApplication.getJobTitle() +
                                " has been " + status,
                        status.toLowerCase(),
                        false,
                        String.valueOf(
                                System.currentTimeMillis()
                        )
                );

        FirebaseManager.getFirestore()
                .collection("notifications")
                .document(notificationId)
                .set(notification);
    }
}