package com.example.hirematch.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Application;

public class ApplicationDetailsActivity extends AppCompatActivity {


    private TextView tvJobId;
    private TextView tvStatus;
    private TextView tvAppliedAt;
    private TextView tvATSScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_details);

        initViews();

        String applicationId =
                getIntent().getStringExtra(
                        "applicationId"
                );

        loadApplication(applicationId);
    }

    private void initViews() {

        tvJobId =
                findViewById(R.id.tvJobId);

        tvStatus =
                findViewById(R.id.tvStatus);

        tvAppliedAt =
                findViewById(R.id.tvAppliedAt);

        tvATSScore =
                findViewById(R.id.tvATSScore);
    }

    private void loadApplication(
            String applicationId) {

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

                        tvJobId.setText(
                                "Job ID: " +
                                        application.getJobId()
                        );

                        tvStatus.setText(
                                "Status: " +
                                        application.getApplicationStatus()
                        );

                        tvAppliedAt.setText(
                                "Applied At: " +
                                        application.getAppliedAt()
                        );

                        tvATSScore.setText(
                                "ATS Score: " +
                                        application.getAtsScore()
                        );
                    }
                });
    }


}
