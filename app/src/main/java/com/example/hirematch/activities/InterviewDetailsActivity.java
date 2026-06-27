package com.example.hirematch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Interview;
import com.example.hirematch.models.Notification;

public class InterviewDetailsActivity
        extends AppCompatActivity {

    private TextView tvJobTitle;
    private TextView tvInterviewDate;
    private TextView tvInterviewTime;
    private TextView tvInterviewStatus;
    private TextView tvMeetingLink;

    private Button btnReschedule;
    private Button btnComplete;
    private Button btnCancel;

    private String interviewId;
    private Interview currentInterview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_details);

        initViews();

        interviewId =
                getIntent().getStringExtra(
                        "interviewId"
                );

        loadInterviewDetails();
        setupListeners();
    }

    private void initViews() {

        tvJobTitle =
                findViewById(R.id.tvJobTitle);

        tvInterviewDate =
                findViewById(R.id.tvInterviewDate);

        tvInterviewTime =
                findViewById(R.id.tvInterviewTime);

        tvInterviewStatus =
                findViewById(R.id.tvInterviewStatus);

        tvMeetingLink =
                findViewById(R.id.tvMeetingLink);

        btnReschedule =
                findViewById(R.id.btnReschedule);

        btnComplete =
                findViewById(R.id.btnComplete);

        btnCancel =
                findViewById(R.id.btnCancel);
    }

    private void setupListeners() {

        btnReschedule.setOnClickListener(v ->
                updateInterviewStatus("Rescheduled"));

        btnComplete.setOnClickListener(v ->
                updateInterviewStatus("Completed"));

        btnCancel.setOnClickListener(v ->
                updateInterviewStatus("Cancelled"));
    }

    private void loadInterviewDetails() {

        if (interviewId == null) {
            finish();
            return;
        }

        FirebaseManager.getFirestore()
                .collection("interviews")
                .document(interviewId)
                .get()
                .addOnSuccessListener(document -> {

                    if (!document.exists()) {
                        finish();
                        return;
                    }

                    currentInterview =
                            document.toObject(
                                    Interview.class
                            );

                    if (currentInterview == null) {
                        finish();
                        return;
                    }

                    currentInterview.setInterviewId(
                            document.getId()
                    );

                    tvJobTitle.setText(
                            currentInterview.getJobTitle()
                    );

                    tvInterviewDate.setText(
                            currentInterview.getInterviewDate()
                    );

                    tvInterviewTime.setText(
                            currentInterview.getInterviewTime()
                    );

                    tvInterviewStatus.setText(
                            currentInterview.getInterviewStatus()
                    );

                    tvMeetingLink.setText(
                            currentInterview.getMeetingLink()
                    );

                    checkUserRole();
                });
    }

    private void checkUserRole() {

        String currentUserId =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        if (currentInterview.getCandidateId()
                .equals(currentUserId)) {

            btnReschedule.setVisibility(
                    View.GONE
            );

            btnComplete.setVisibility(
                    View.GONE
            );

            btnCancel.setVisibility(
                    View.GONE
            );
        }
    }

    private void updateInterviewStatus(String status) {

        FirebaseManager.getFirestore()
                .collection("interviews")
                .document(interviewId)
                .update(
                        "interviewStatus",
                        status
                )
                .addOnSuccessListener(unused -> {

                    createNotification(status);

                    if (status.equals("Completed")) {

                        Intent intent =
                                new Intent(
                                        this,
                                        SendOfferActivity.class
                                );

                        intent.putExtra(
                                "applicationId",
                                currentInterview.getApplicationId()
                        );

                        intent.putExtra(
                                "candidateId",
                                currentInterview.getCandidateId()
                        );

                        intent.putExtra(
                                "hrId",
                                currentInterview.getHrId()
                        );

                        intent.putExtra(
                                "jobTitle",
                                currentInterview.getJobTitle()
                        );

                        startActivity(intent);

                    } else {
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
                        currentInterview.getCandidateId(),
                        "Interview Update",
                        "Interview " + status,
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