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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ApplicantDetailsActivity
        extends AppCompatActivity {

    private TextView tvCandidateName;
    private TextView tvEmail;
    private TextView tvJobTitle;
    private TextView tvATSScore;
    private TextView tvStatus;
    private TextView tvStage;
    private TextView tvAppliedAt;

    private Button btnViewProfile;
    private Button btnShortlist;
    private Button btnHold;
    private Button btnReject;

    private String applicationId;
    private String currentHrId;

    private Application currentApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_details);

        applicationId =
                getIntent().getStringExtra("applicationId");

        if (applicationId == null || applicationId.isEmpty()) {

            Toast.makeText(
                    this,
                    "Application not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        if (FirebaseManager.getAuth().getCurrentUser() != null) {
            currentHrId =
                    FirebaseManager.getAuth()
                            .getCurrentUser()
                            .getUid();
        }

        initViews();
        loadApplicant();
    }

    private void initViews() {

        tvCandidateName =
                findViewById(R.id.tvCandidateName);

        tvEmail =
                findViewById(R.id.tvEmail);

        tvJobTitle =
                findViewById(R.id.tvJobTitle);

        tvATSScore =
                findViewById(R.id.tvATSScore);

        tvStatus =
                findViewById(R.id.tvStatus);

        tvStage =
                findViewById(R.id.tvStage);

        tvAppliedAt =
                findViewById(R.id.tvAppliedAt);

        btnViewProfile =
                findViewById(R.id.btnViewProfile);

        btnShortlist =
                findViewById(R.id.btnShortlist);

        btnHold =
                findViewById(R.id.btnHold);

        btnReject =
                findViewById(R.id.btnReject);

        btnViewProfile.setOnClickListener(v ->
                openCandidateProfile()
        );

        btnShortlist.setOnClickListener(v ->
                updateStatus(
                        "Shortlisted",
                        "Interview"
                )
        );

        btnHold.setOnClickListener(v ->
                updateStatus(
                        "On Hold",
                        "Review"
                )
        );

        btnReject.setOnClickListener(v ->
                updateStatus(
                        "Rejected",
                        "Closed"
                )
        );
    }

    private void openCandidateProfile() {

        if (currentApplication == null)
            return;

        Intent intent =
                new Intent(
                        this,
                        CandidateViewActivity.class
                );

        intent.putExtra(
                "candidateId",
                currentApplication.getCandidateId()
        );

        startActivity(intent);
    }

    private void loadApplicant() {

        FirebaseManager.getFirestore()
                .collection("applications")
                .document(applicationId)
                .get()
                .addOnSuccessListener(document -> {

                    if (!document.exists()) {
                        finish();
                        return;
                    }

                    currentApplication =
                            document.toObject(
                                    Application.class
                            );

                    if (currentApplication == null)
                        return;

                    if (currentApplication.getHrId() == null) {
                        currentApplication.setApplicationStatus(
                                currentApplication.getApplicationStatus()
                        );
                    }

                    currentApplication.setApplicationId(
                            document.getId()
                    );

                    bindApplicantData();
                    handleButtonState();
                });
    }

    private void bindApplicantData() {

        tvCandidateName.setText(
                currentApplication.getCandidateName()
        );

        tvEmail.setText(
                currentApplication.getCandidateEmail()
        );

        tvJobTitle.setText(
                currentApplication.getJobTitle()
        );

        tvATSScore.setText(
                currentApplication.getAtsScore() + "%"
        );

        tvStatus.setText(
                currentApplication.getApplicationStatus()
        );

        tvStage.setText(
                currentApplication.getCurrentStage()
        );

        String formattedDate =
                new SimpleDateFormat(
                        "dd MMM yyyy",
                        Locale.getDefault()
                ).format(
                        new Date(
                                currentApplication.getAppliedAt()
                        )
                );

        tvAppliedAt.setText(
                formattedDate
        );
    }

    private void handleButtonState() {

        btnShortlist.setEnabled(true);
        btnHold.setEnabled(true);
        btnReject.setEnabled(true);

        String status =
                currentApplication.getApplicationStatus();

        if ("Rejected".equals(status)) {

            btnShortlist.setEnabled(false);
            btnHold.setEnabled(false);
            btnReject.setEnabled(false);
        }

        if ("Shortlisted".equals(status)) {

            btnShortlist.setEnabled(false);
        }
    }

    private void updateStatus(
            String status,
            String stage
    ) {

        if (currentApplication == null)
            return;

        FirebaseManager.getFirestore()
                .collection("applications")
                .document(applicationId)
                .update(
                        "applicationStatus", status,
                        "currentStage", stage
                )
                .addOnSuccessListener(unused -> {

                    currentApplication.setApplicationStatus(
                            status
                    );

                    currentApplication.setCurrentStage(
                            stage
                    );

                    bindApplicantData();
                    handleButtonState();
                    createNotification(status);

                    if ("Shortlisted".equals(status)) {

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
                                "Status Updated Successfully",
                                Toast.LENGTH_SHORT
                        ).show();
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
                        "Your application for "
                                + currentApplication.getJobTitle()
                                + " has been " + status,
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