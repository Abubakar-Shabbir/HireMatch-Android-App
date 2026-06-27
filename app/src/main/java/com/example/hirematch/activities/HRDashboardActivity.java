package com.example.hirematch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Company;
import com.example.hirematch.utils.SharedPrefManager;

public class HRDashboardActivity
        extends AppCompatActivity {

    private LinearLayout btnCompanyProfile;

    private Button btnCreateJob;
    private Button btnMyJobs;
    private Button btnApplicants;
    private Button btnInterviews;
    private Button btnOffers;
    private Button btnNotifications;
    private Button btnLogout;

    private TextView tvCompanyName;
    private TextView tvHRName;
    private TextView tvCompanyProgress;

    private TextView tvJobsCount;
    private TextView tvApplicantsCount;
    private TextView tvInterviewsCount;
    private TextView tvOffersCount;

    private ProgressBar progressCompany;

    private int companyScore = 0;
    private boolean isCompanyComplete = false;

    private String hrId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr_dashboard);

        if (FirebaseManager.getAuth().getCurrentUser() == null) {
            finish();
            return;
        }

        hrId =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        initViews();
        setupListeners();

        loadCompanyStatus();
        loadAnalytics();
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

        btnInterviews =
                findViewById(R.id.btnInterviews);

        btnOffers =
                findViewById(R.id.btnOffers);

        btnNotifications =
                findViewById(R.id.btnNotifications);

        btnLogout =
                findViewById(R.id.btnLogout);

        tvCompanyName =
                findViewById(R.id.tvCompanyName);

        tvHRName =
                findViewById(R.id.tvHRName);

        tvCompanyProgress =
                findViewById(R.id.tvCompanyProgress);

        tvJobsCount =
                findViewById(R.id.tvJobsCount);

        tvApplicantsCount =
                findViewById(R.id.tvApplicantsCount);

        tvInterviewsCount =
                findViewById(R.id.tvInterviewsCount);

        tvOffersCount =
                findViewById(R.id.tvOffersCount);

        progressCompany =
                findViewById(R.id.progressCompany);
    }

    private void setupListeners() {

        btnCompanyProfile.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                CompanyProfileActivity.class
                        )
                )
        );

        btnCreateJob.setOnClickListener(v -> {

            if (!isCompanyComplete) {
                showLockMessage();
                return;
            }

            startActivity(
                    new Intent(
                            this,
                            CreateJobActivity.class
                    )
            );
        });

        btnMyJobs.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                JobManagementActivity.class
                        )
                )
        );

        btnApplicants.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                ApplicantsActivity.class
                        )
                )
        );

        btnInterviews.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                MyInterviewsActivity.class
                        )
                )
        );

        btnOffers.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                MyOffersActivity.class
                        )
                )
        );

        btnNotifications.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                NotificationsActivity.class
                        )
                )
        );

        btnLogout.setOnClickListener(v -> {

            FirebaseManager.getAuth().signOut();

            SharedPrefManager pref =
                    new SharedPrefManager(this);

            pref.logout();

            startActivity(
                    new Intent(
                            this,
                            LoginActivity.class
                    )
            );

            finish();
        });
    }

    private void loadCompanyStatus() {

        FirebaseManager.getFirestore()
                .collection("companies")
                .document(hrId)
                .get()
                .addOnSuccessListener(document -> {

                    if (!document.exists()) {
                        updateCompanyUI();
                        return;
                    }

                    Company company =
                            document.toObject(
                                    Company.class
                            );

                    if (company != null) {

                        tvCompanyName.setText(
                                company.getCompanyName()
                        );

                        tvHRName.setText(
                                "Welcome HR"
                        );

                        calculateCompanyScore(company);
                    }
                });
    }

    private void calculateCompanyScore(
            Company company) {

        companyScore = 0;

        if (company.getCompanyName() != null &&
                !company.getCompanyName().isEmpty())
            companyScore += 15;

        if (company.getWebsite() != null &&
                !company.getWebsite().isEmpty())
            companyScore += 10;

        if (company.getLocation() != null &&
                !company.getLocation().isEmpty())
            companyScore += 10;

        if (company.getDescription() != null &&
                !company.getDescription().isEmpty())
            companyScore += 10;

        if (company.getIndustry() != null &&
                !company.getIndustry().isEmpty())
            companyScore += 10;

        if (company.getCompanySize() != null &&
                !company.getCompanySize().isEmpty())
            companyScore += 10;

        if (company.getFoundedYear() != null &&
                !company.getFoundedYear().isEmpty())
            companyScore += 10;

        if (company.getLinkedIn() != null &&
                !company.getLinkedIn().isEmpty())
            companyScore += 10;

        if (company.getBenefits() != null &&
                !company.getBenefits().isEmpty())
            companyScore += 5;

        if (company.getCulture() != null &&
                !company.getCulture().isEmpty())
            companyScore += 5;

        if (company.getHiringEmail() != null &&
                !company.getHiringEmail().isEmpty())
            companyScore += 5;

        isCompanyComplete =
                companyScore >= 70;

        updateCompanyUI();
    }

    private void updateCompanyUI() {

        tvCompanyProgress.setText(
                companyScore + "% Completed"
        );

        progressCompany.setProgress(
                companyScore
        );
    }

    private void loadAnalytics() {

        // Total Jobs
        FirebaseManager.getFirestore()
                .collection("jobs")
                .whereEqualTo("hrId", hrId)
                .get()
                .addOnSuccessListener(snapshot ->
                        tvJobsCount.setText(
                                String.valueOf(snapshot.size())
                        )
                );

        // Total Applicants
        FirebaseManager.getFirestore()
                .collection("applications")
                .whereEqualTo("hrId", hrId)
                .get()
                .addOnSuccessListener(snapshot ->
                        tvApplicantsCount.setText(
                                String.valueOf(snapshot.size())
                        )
                );

        // Shortlisted Candidates Count
        FirebaseManager.getFirestore()
                .collection("applications")
                .whereEqualTo("hrId", hrId)
                .whereEqualTo(
                        "applicationStatus",
                        "Shortlisted"
                )
                .get()
                .addOnSuccessListener(snapshot ->
                        tvInterviewsCount.setText(
                                String.valueOf(snapshot.size())
                        )
                );

        // Offers Count
        FirebaseManager.getFirestore()
                .collection("offers")
                .whereEqualTo("hrId", hrId)
                .get()
                .addOnSuccessListener(snapshot ->
                        tvOffersCount.setText(
                                String.valueOf(snapshot.size())
                        )
                );
    }

    private void showLockMessage() {

        Toast.makeText(
                this,
                "Complete company profile first",
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadCompanyStatus();
        loadAnalytics();
    }
}