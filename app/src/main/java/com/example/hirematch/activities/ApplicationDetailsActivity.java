package com.example.hirematch.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Application;
import com.example.hirematch.utils.LoadingManager;
public class ApplicationDetailsActivity extends AppCompatActivity {

    private TextView tvJobTitle;
    private TextView tvStatus;
    private TextView tvATSScore;
    private TextView tvAppliedAt;

    private String applicationId;
    private TextView tvLocation;
    private TextView tvSalary;
    private TextView tvEmploymentType;
    private TextView tvExperience;
    private TextView tvEducation;
    private TextView tvSkills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_details);
        LoadingManager.show(this);
        initViews();

        applicationId =
                getIntent().getStringExtra(
                        "applicationId"
                );

        loadApplicationDetails();
        LoadingManager.hide();
    }

    private void initViews() {

        tvJobTitle =
                findViewById(
                        R.id.tvJobTitle
                );

        tvStatus =
                findViewById(
                        R.id.tvStatus
                );

        tvATSScore =
                findViewById(
                        R.id.tvATSScore
                );

        tvAppliedAt =
                findViewById(
                        R.id.tvAppliedAt
                );

    }

    private void loadApplicationDetails() {

        FirebaseManager.getFirestore()
                .collection("applications")
                .document(applicationId)
                .get()
                .addOnSuccessListener(document -> {

                    Application application =
                            document.toObject(
                                    Application.class
                            );

                    if (application != null) {

                        tvJobTitle.setText(
                                application.getJobTitle()
                        );

                        tvStatus.setText(
                                "Status: " +
                                        application.getApplicationStatus()
                        );

                        tvATSScore.setText(
                                "ATS Score: " +
                                        application.getAtsScore() + "%"
                        );

                        tvAppliedAt.setText(
                                "Applied At: " +
                                        application.getAppliedAt()
                        );
                    }
                });
    }
}