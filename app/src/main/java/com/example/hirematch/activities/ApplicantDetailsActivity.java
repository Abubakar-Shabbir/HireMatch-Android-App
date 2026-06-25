package com.example.hirematch.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Application;

public class ApplicantDetailsActivity extends AppCompatActivity {


    private TextView tvCandidateName;
    private TextView tvEmail;
    private TextView tvATSScore;

    private Button btnShortlist;
    private Button btnReject;

    private String applicationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_details);

        initViews();

        applicationId =
                getIntent().getStringExtra(
                        "applicationId"
                );

        loadApplicant();

        btnShortlist.setOnClickListener(v ->
                updateStatus("Shortlisted")
        );

        btnReject.setOnClickListener(v ->
                updateStatus("Rejected")
        );
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
    }

    private void loadApplicant() {

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

                        tvCandidateName.setText(
                                application.getCandidateName()
                        );

                        tvEmail.setText(
                                application.getCandidateEmail()
                        );

                        tvATSScore.setText(
                                "ATS Score: " +
                                        application.getAtsScore() + "%"
                        );
                    }
                });
    }

    private void updateStatus(
            String status) {

        FirebaseManager.getFirestore()
                .collection("applications")
                .document(applicationId)
                .update(
                        "applicationStatus",
                        status
                );
    }


}
