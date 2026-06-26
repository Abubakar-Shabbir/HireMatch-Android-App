package com.example.hirematch.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Interview;
import com.example.hirematch.models.Notification;

public class InterviewDetailsActivity extends AppCompatActivity {

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
                updateInterviewStatus("Rescheduled")
        );

        btnComplete.setOnClickListener(v ->
                updateInterviewStatus("Completed")
        );

        btnCancel.setOnClickListener(v ->
                updateInterviewStatus("Cancelled")
        );
    }

    private void loadInterviewDetails() {

        FirebaseManager.getFirestore()
                .collection("interviews")
                .document(interviewId)
                .get()
                .addOnSuccessListener(document -> {

                    currentInterview =
                            document.toObject(
                                    Interview.class
                            );

                    if (currentInterview != null) {

                        tvJobTitle.setText(
                                currentInterview.getJobTitle()
                        );

                        tvInterviewDate.setText(
                                "Date: " +
                                        currentInterview.getInterviewDate()
                        );

                        tvInterviewTime.setText(
                                "Time: " +
                                        currentInterview.getInterviewTime()
                        );

                        tvInterviewStatus.setText(
                                "Status: " +
                                        currentInterview.getInterviewStatus()
                        );

                        tvMeetingLink.setText(
                                "Meeting Link: " +
                                        currentInterview.getMeetingLink()
                        );
                    }
                });
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

                    Toast.makeText(
                            this,
                            "Interview " + status,
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();
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
                        "Your interview for " +
                                currentInterview.getJobTitle() +
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