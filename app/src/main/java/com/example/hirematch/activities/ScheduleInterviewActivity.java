package com.example.hirematch.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Interview;
import com.example.hirematch.models.Notification;

import java.util.Calendar;

public class ScheduleInterviewActivity
        extends AppCompatActivity {

    private EditText etInterviewDate;
    private EditText etInterviewTime;
    private EditText etInterviewMode;
    private EditText etMeetingLink;
    private EditText etLocation;
    private EditText etNotes;

    private Button btnScheduleInterview;

    private String applicationId;
    private String candidateId;
    private String candidateName;
    private String jobId;
    private String hrId;
    private String jobTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_interview);

        initViews();
        getIntentData();
        setupPickers();

        btnScheduleInterview.setOnClickListener(v ->
                scheduleInterview()
        );
    }

    private void initViews() {

        etInterviewDate =
                findViewById(R.id.etInterviewDate);

        etInterviewTime =
                findViewById(R.id.etInterviewTime);

        etInterviewMode =
                findViewById(R.id.etInterviewMode);

        etMeetingLink =
                findViewById(R.id.etMeetingLink);

        etLocation =
                findViewById(R.id.etLocation);

        etNotes =
                findViewById(R.id.etNotes);

        btnScheduleInterview =
                findViewById(R.id.btnScheduleInterview);
    }

    private void setupPickers() {

        etInterviewDate.setOnClickListener(v -> {

            Calendar calendar =
                    Calendar.getInstance();

            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(
                            this,
                            (view, year, month, dayOfMonth) -> {

                                String date =
                                        dayOfMonth + "/" +
                                                (month + 1) + "/" +
                                                year;

                                etInterviewDate.setText(date);
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                    );

            datePickerDialog.show();
        });

        etInterviewTime.setOnClickListener(v -> {

            Calendar calendar =
                    Calendar.getInstance();

            TimePickerDialog timePickerDialog =
                    new TimePickerDialog(
                            this,
                            (view, hourOfDay, minute) -> {

                                String time =
                                        hourOfDay + ":" + minute;

                                etInterviewTime.setText(time);
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            false
                    );

            timePickerDialog.show();
        });
    }

    private void getIntentData() {

        applicationId =
                getIntent().getStringExtra("applicationId");

        candidateId =
                getIntent().getStringExtra("candidateId");

        candidateName =
                getIntent().getStringExtra("candidateName");

        jobId =
                getIntent().getStringExtra("jobId");

        hrId =
                getIntent().getStringExtra("hrId");

        jobTitle =
                getIntent().getStringExtra("jobTitle");

        if (applicationId == null ||
                candidateId == null ||
                hrId == null ||
                jobTitle == null) {

            Toast.makeText(
                    this,
                    "Missing interview data",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        }
    }

    private void scheduleInterview() {

        String interviewDate =
                etInterviewDate.getText().toString().trim();

        String interviewTime =
                etInterviewTime.getText().toString().trim();

        String interviewMode =
                etInterviewMode.getText().toString().trim();

        String meetingLink =
                etMeetingLink.getText().toString().trim();

        String location =
                etLocation.getText().toString().trim();

        String notes =
                etNotes.getText().toString().trim();

        if (interviewDate.isEmpty() ||
                interviewTime.isEmpty() ||
                interviewMode.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please fill required fields",
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
                        candidateName,
                        hrId,
                        jobTitle,
                        interviewDate,
                        interviewTime,
                        interviewMode,
                        meetingLink,
                        location,
                        notes,
                        "Scheduled",
                        String.valueOf(
                                System.currentTimeMillis()
                        )
                );

        FirebaseManager.getFirestore()
                .collection("interviews")
                .document(interviewId)
                .set(interview)
                .addOnSuccessListener(unused -> {

                    FirebaseManager.getFirestore()
                            .collection("applications")
                            .document(applicationId)
                            .update(
                                    "applicationStatus",
                                    "Interview Scheduled",
                                    "currentStage",
                                    "Interview"
                            );

                    createNotification();

                    Toast.makeText(
                            this,
                            "Interview Scheduled Successfully",
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();
                });
    }

    private void createNotification() {

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
                                " has been scheduled.",
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