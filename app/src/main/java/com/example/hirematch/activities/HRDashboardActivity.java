package com.example.hirematch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.utils.SharedPrefManager;

public class HRDashboardActivity extends AppCompatActivity {

    private Button btnCompanyProfile;
    private Button btnCreateJob;
    private Button btnMyJobs;
    private Button btnApplicants;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr_dashboard);

        initViews();
        setupListeners();
    }

    private void initViews() {

        btnCompanyProfile =
                findViewById(R.id.btnCompanyProfile);

        btnCreateJob =
                findViewById(R.id.btnCreateJob);

        btnMyJobs =
                findViewById(R.id.btnMyJobs);

        btnApplicants =
                findViewById(R.id.btnApplicants);

        btnLogout =
                findViewById(R.id.btnLogout);
    }

    private void setupListeners() {

        btnCompanyProfile.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                HRDashboardActivity.this,
                                CompanyProfileActivity.class
                        )
                )

        );

        btnCreateJob.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                HRDashboardActivity.this,
                                CreateJobActivity.class
                        )
                )

        );

        btnMyJobs.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                HRDashboardActivity.this,
                                JobManagementActivity.class
                        )
                )

        );

        btnApplicants.setOnClickListener(v -> {

            // Sprint 6
            // Applicants Screen

        });

        btnLogout.setOnClickListener(v -> {

            FirebaseManager.getAuth().signOut();

            SharedPrefManager pref =
                    new SharedPrefManager(this);

            pref.logout();

            startActivity(
                    new Intent(
                            HRDashboardActivity.this,
                            LoginActivity.class
                    )
            );

            finish();
        });
    }


}
