package com.example.hirematch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Interview;
import com.example.hirematch.models.Notification;

public class ScheduleInterviewActivity extends AppCompatActivity {

    private EditText etInterviewDate;
    private EditText etInterviewTime;
    private EditText etMeetingLink;
    private Button btnScheduleInterview;

    private String applicationId;
    private String candidateId;
    private String jobId;
    private String hrId;
    private String jobTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_interview);

        initViews();
        getIntentData();

        btnScheduleInterview.setOnClickListener(v ->
                scheduleInterview()
        );
    }

    private void initViews() {

        etInterviewDate =
                findViewById(R.id.etInterviewDate);

        etInterviewTime =
                findViewById(R.id.etInterviewTime);

        etMeetingLink =
                findViewById(R.id.etMeetingLink);

        btnScheduleInterview =
                findViewById(R.id.btnScheduleInterview);
    }

    private void getIntentData() {

        applicationId =
                getIntent().getStringExtra("applicationId");

        candidateId =
                getIntent().getStringExtra("candidateId");

        jobId =
                getIntent().getStringExtra("jobId");

        hrId =
                getIntent().getStringExtra("hrId");

        jobTitle =
                getIntent().getStringExtra("jobTitle");
    }

    private void scheduleInterview() {

        String date =
                etInterviewDate.getText()
                        .toString()
                        .trim();

        String time =
                etInterviewTime.getText()
                        .toString()
                        .trim();

        String meetingLink =
                etMeetingLink.getText()
                        .toString()
                        .trim();

        if (date.isEmpty() ||
                time.isEmpty() ||
                meetingLink.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        String interviewId =
                FirebaseManager.getFirestore()
                        .collection("interviews")
                        .document()
                        .getId();

        Interview interview =
                new Interview(
                        interviewId,
                        applicationId,
                        jobId,
                        candidateId,
                        hrId,
                        jobTitle,
                        date,
                        time,
                        "Scheduled",
                        meetingLink,
                        String.valueOf(
                                System.currentTimeMillis()
                        )
                );

        FirebaseManager.getFirestore()
                .collection("interviews")
                .document(interviewId)
                .set(interview)
                .addOnSuccessListener(unused -> {

                    createNotification(date, time);

                    Toast.makeText(
                            this,
                            "Interview Scheduled Successfully",
                            Toast.LENGTH_LONG
                    ).show();

                    Intent intent =
                            new Intent(
                                    ScheduleInterviewActivity.this,
                                    InterviewDetailsActivity.class
                            );

                    intent.putExtra(
                            "interviewId",
                            interviewId
                    );

                    startActivity(intent);

                    finish();
                });
    }

    private void createNotification(
            String date,
            String time) {

        String notificationId =
                FirebaseManager.getFirestore()
                        .collection("notifications")
                        .document()
                        .getId();

        Notification notification =
                new Notification(
                        notificationId,
                        candidateId,
                        "Interview Scheduled",
                        "Your interview for " +
                                jobTitle +
                                " is scheduled on " +
                                date +
                                " at " +
                                time,
                        "interview",
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