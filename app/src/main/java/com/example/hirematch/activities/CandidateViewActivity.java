package com.example.hirematch.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.CandidateProfile;

public class CandidateViewActivity
        extends AppCompatActivity {

    private TextView tvName;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvCity;

    private TextView tvTitle;
    private TextView tvAbout;

    private TextView tvSkills;
    private TextView tvEducation;
    private TextView tvExperience;

    private TextView tvCertifications;
    private TextView tvAchievements;
    private TextView tvAwards;

    private TextView tvProjects;
    private TextView tvPortfolio;

    private TextView tvExpectedSalary;
    private TextView tvPreferredJobType;
    private TextView tvPreferredLocation;
    private TextView tvAvailability;
    private TextView tvWorkMode;

    private TextView tvProfileScore;

    private String candidateId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_view);

        candidateId =
                getIntent().getStringExtra(
                        "candidateId"
                );

        initViews();
        loadCandidateProfile();
    }

    private void initViews() {

        tvName =
                findViewById(R.id.tvName);

        tvEmail =
                findViewById(R.id.tvEmail);

        tvPhone =
                findViewById(R.id.tvPhone);

        tvCity =
                findViewById(R.id.tvCity);

        tvTitle =
                findViewById(R.id.tvTitle);

        tvAbout =
                findViewById(R.id.tvAbout);

        tvSkills =
                findViewById(R.id.tvSkills);

        tvEducation =
                findViewById(R.id.tvEducation);

        tvExperience =
                findViewById(R.id.tvExperience);

        tvCertifications =
                findViewById(R.id.tvCertifications);

        tvAchievements =
                findViewById(R.id.tvAchievements);

        tvAwards =
                findViewById(R.id.tvAwards);

        tvProjects =
                findViewById(R.id.tvProjects);

        tvPortfolio =
                findViewById(R.id.tvPortfolio);

        tvExpectedSalary =
                findViewById(R.id.tvExpectedSalary);

        tvPreferredJobType =
                findViewById(R.id.tvPreferredJobType);

        tvPreferredLocation =
                findViewById(R.id.tvPreferredLocation);

        tvAvailability =
                findViewById(R.id.tvAvailability);

        tvWorkMode =
                findViewById(R.id.tvWorkMode);

        tvProfileScore =
                findViewById(R.id.tvProfileScore);
    }

    private void loadCandidateProfile() {

        FirebaseManager.getFirestore()
                .collection("candidate_profiles")
                .document(candidateId)
                .get()
                .addOnSuccessListener(document -> {

                    if (!document.exists()) {
                        return;
                    }

                    CandidateProfile profile =
                            document.toObject(
                                    CandidateProfile.class
                            );

                    if (profile == null) {
                        return;
                    }

                    bindProfileData(profile);
                });
    }

    private void bindProfileData(
            CandidateProfile profile) {

        tvName.setText(profile.getName());
        tvEmail.setText(profile.getEmail());
        tvPhone.setText(profile.getPhone());
        tvCity.setText(profile.getCity());

        tvTitle.setText(profile.getTitle());
        tvAbout.setText(profile.getAbout());

        tvSkills.setText(profile.getSkills());
        tvEducation.setText(profile.getEducation());
        tvExperience.setText(profile.getExperience());

        tvCertifications.setText(
                profile.getCertifications()
        );

        tvAchievements.setText(
                profile.getAchievements()
        );

        tvAwards.setText(
                profile.getAwards()
        );

        tvProjects.setText(
                profile.getProjects()
        );

        tvPortfolio.setText(
                profile.getPortfolio()
        );

        tvExpectedSalary.setText(
                profile.getExpectedSalary()
        );

        tvPreferredJobType.setText(
                profile.getPreferredJobType()
        );

        tvPreferredLocation.setText(
                profile.getPreferredLocation()
        );

        tvAvailability.setText(
                profile.getAvailability()
        );

        tvWorkMode.setText(
                profile.getWorkMode()
        );

        tvProfileScore.setText(
                calculateProfileScore(profile) + "%"
        );
    }

    private int calculateProfileScore(
            CandidateProfile profile) {

        int score = 0;

        if (!profile.getName().isEmpty()) score += 10;
        if (!profile.getEmail().isEmpty()) score += 10;
        if (!profile.getPhone().isEmpty()) score += 10;
        if (!profile.getCity().isEmpty()) score += 10;
        if (!profile.getTitle().isEmpty()) score += 10;
        if (!profile.getAbout().isEmpty()) score += 10;
        if (!profile.getSkills().isEmpty()) score += 10;
        if (!profile.getEducation().isEmpty()) score += 10;
        if (!profile.getExperience().isEmpty()) score += 10;
        if (!profile.getProjects().isEmpty()) score += 10;

        return score;
    }
}