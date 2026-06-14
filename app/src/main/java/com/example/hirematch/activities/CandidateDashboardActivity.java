package com.example.hirematch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.utils.SharedPrefManager;

public class CandidateDashboardActivity extends AppCompatActivity {


    private Button btnProfile;
    private Button btnResume;
    private Button btnATS;
    private Button btnJobs;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_dashboard);

        initViews();
        setupListeners();
    }

    private void initViews() {

        btnProfile = findViewById(R.id.btnProfile);
        btnResume = findViewById(R.id.btnResume);
        btnATS = findViewById(R.id.btnATS);
        btnJobs = findViewById(R.id.btnJobs);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void setupListeners() {

        btnProfile.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                CandidateDashboardActivity.this,
                                CandidateProfileActivity.class
                        )
                )

        );

        btnResume.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                CandidateDashboardActivity.this,
                                UploadResumeActivity.class
                        )
                )

        );

        btnATS.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                CandidateDashboardActivity.this,
                                ATSScoreActivity.class
                        )
                )

        );

        btnJobs.setOnClickListener(v -> {

            // Sprint 3 me Jobs Screen open hogi

        });

        btnLogout.setOnClickListener(v -> {

            FirebaseManager.getAuth().signOut();

            SharedPrefManager pref =
                    new SharedPrefManager(this);

            pref.logout();

            startActivity(
                    new Intent(
                            CandidateDashboardActivity.this,
                            LoginActivity.class
                    )
            );

            finish();
        });
    }


}
