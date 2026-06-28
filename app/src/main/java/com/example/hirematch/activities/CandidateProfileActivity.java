package com.example.hirematch.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.CandidateProfile;

public class CandidateProfileActivity
        extends AppCompatActivity {

    private EditText etName, etEmail, etPhone, etCity;
    private EditText etTitle, etAbout;
    private EditText etSkills, etEducation, etExperience;
    private EditText etCertifications, etAchievements, etAwards;
    private EditText etProjects, etPortfolio;
    private EditText etExpectedSalary, etPreferredJobType;
    private EditText etPreferredLocation, etAvailability, etWorkMode;

    private Button btnSaveProfile;

    private TextView tvProfileProgress;
    private ProgressBar progressProfile;

    private String uid;
    private boolean isUpdateMode = false;
    private int profileScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile);

        uid = FirebaseManager.getAuth()
                .getCurrentUser()
                .getUid();

        initViews();
        loadProfile();

        btnSaveProfile.setOnClickListener(v ->
                saveProfile()
        );
    }

    private void initViews() {

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etCity = findViewById(R.id.etCity);

        etTitle = findViewById(R.id.etTitle);
        etAbout = findViewById(R.id.etAbout);

        etSkills = findViewById(R.id.etSkills);
        etEducation = findViewById(R.id.etEducation);
        etExperience = findViewById(R.id.etExperience);

        etCertifications = findViewById(R.id.etCertifications);
        etAchievements = findViewById(R.id.etAchievements);
        etAwards = findViewById(R.id.etAwards);
        etProjects = findViewById(R.id.etProjects);
        etPortfolio = findViewById(R.id.etPortfolio);

        etExpectedSalary =
                findViewById(R.id.etExpectedSalary);

        etPreferredJobType =
                findViewById(R.id.etPreferredJobType);

        etPreferredLocation =
                findViewById(R.id.etPreferredLocation);

        etAvailability =
                findViewById(R.id.etAvailability);

        etWorkMode =
                findViewById(R.id.etWorkMode);

        btnSaveProfile =
                findViewById(R.id.btnSaveProfile);

        tvProfileProgress =
                findViewById(R.id.tvProfileProgress);

        progressProfile =
                findViewById(R.id.progressProfile);
    }

    private void saveProfile() {

        if (etName.getText().toString().trim().isEmpty()) {
            etName.setError("Required");
            return;
        }

        if (etEmail.getText().toString().trim().isEmpty()) {
            etEmail.setError("Required");
            return;
        }

        btnSaveProfile.setEnabled(false);
        btnSaveProfile.setText("Saving...");

        CandidateProfile profile =
                new CandidateProfile(
                        uid,
                        etName.getText().toString().trim(),
                        etEmail.getText().toString().trim(),
                        etPhone.getText().toString().trim(),
                        etCity.getText().toString().trim(),
                        etTitle.getText().toString().trim(),
                        etAbout.getText().toString().trim(),
                        etSkills.getText().toString().trim(),
                        etEducation.getText().toString().trim(),
                        etExperience.getText().toString().trim(),
                        etCertifications.getText().toString().trim(),
                        etAchievements.getText().toString().trim(),
                        etAwards.getText().toString().trim(),
                        etProjects.getText().toString().trim(),
                        etPortfolio.getText().toString().trim(),
                        etExpectedSalary.getText().toString().trim(),
                        etPreferredJobType.getText().toString().trim(),
                        etPreferredLocation.getText().toString().trim(),
                        etAvailability.getText().toString().trim(),
                        etWorkMode.getText().toString().trim(),
                        "",
                        "",
                        String.valueOf(
                                System.currentTimeMillis()
                        )
                );
        System.out.println("PROFILE SCORE = " + profile.getProfileScore());
// Calculate score BEFORE saving
        calculateProfileScore(profile);
        Toast.makeText(this,
                "Score = " + profile.getProfileScore(),
                Toast.LENGTH_LONG).show();

        FirebaseManager.getFirestore()
                .collection("candidate_profiles")
                .document(uid)
                .set(profile)
                .addOnSuccessListener(unused -> {

                    btnSaveProfile.setEnabled(true);

                    if (isUpdateMode) {
                        btnSaveProfile.setText(
                                "Update Profile"
                        );
                    } else {
                        btnSaveProfile.setText(
                                "Save Profile"
                        );
                    }

                    Toast.makeText(
                            this,
                            "Profile Saved Successfully",
                            Toast.LENGTH_SHORT
                    ).show();
                })
                .addOnFailureListener(e -> {

                    btnSaveProfile.setEnabled(true);
                    btnSaveProfile.setText(
                            "Save Profile"
                    );

                    Toast.makeText(
                            this,
                            e.getMessage(),
                            Toast.LENGTH_SHORT
                    ).show();
                });
    }

    private void loadProfile() {

        FirebaseManager.getFirestore()
                .collection("candidate_profiles")
                .document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {

                    if (documentSnapshot.exists()) {

                        CandidateProfile profile =
                                documentSnapshot.toObject(
                                        CandidateProfile.class
                                );

                        if (profile != null) {

                            etName.setText(profile.getName());
                            etEmail.setText(profile.getEmail());
                            etPhone.setText(profile.getPhone());
                            etCity.setText(profile.getCity());

                            etTitle.setText(profile.getTitle());
                            etAbout.setText(profile.getAbout());

                            etSkills.setText(profile.getSkills());
                            etEducation.setText(profile.getEducation());
                            etExperience.setText(profile.getExperience());

                            etCertifications.setText(
                                    profile.getCertifications()
                            );

                            etAchievements.setText(
                                    profile.getAchievements()
                            );

                            etAwards.setText(
                                    profile.getAwards()
                            );

                            etProjects.setText(
                                    profile.getProjects()
                            );

                            etPortfolio.setText(
                                    profile.getPortfolio()
                            );

                            etExpectedSalary.setText(
                                    profile.getExpectedSalary()
                            );

                            etPreferredJobType.setText(
                                    profile.getPreferredJobType()
                            );

                            etPreferredLocation.setText(
                                    profile.getPreferredLocation()
                            );

                            etAvailability.setText(
                                    profile.getAvailability()
                            );

                            etWorkMode.setText(
                                    profile.getWorkMode()
                            );

                            calculateProfileScore(profile);

                            isUpdateMode = true;
                            btnSaveProfile.setText(
                                    "Update Profile"
                            );
                        }
                    }
                });
    }

    private void calculateProfileScore(
            CandidateProfile profile) {

        profileScore = 0;

        if (!profile.getName().isEmpty()) profileScore += 10;
        if (!profile.getPhone().isEmpty()) profileScore += 10;
        if (!profile.getCity().isEmpty()) profileScore += 10;
        if (!profile.getTitle().isEmpty()) profileScore += 10;
        if (!profile.getAbout().isEmpty()) profileScore += 10;
        if (!profile.getSkills().isEmpty()) profileScore += 10;
        if (!profile.getEducation().isEmpty()) profileScore += 10;
        if (!profile.getExperience().isEmpty()) profileScore += 10;
        if (!profile.getCertifications().isEmpty()) profileScore += 5;
        if (!profile.getAchievements().isEmpty()) profileScore += 5;
        if (!profile.getAwards().isEmpty()) profileScore += 5;
        if (!profile.getProjects().isEmpty()) profileScore += 5;
        if (!profile.getPortfolio().isEmpty()) profileScore += 5;
        if (!profile.getPreferredJobType().isEmpty()) profileScore += 5;
        if(profileScore > 100){
            profileScore = 100;
        }
        tvProfileProgress.setText(profileScore + "% Completed");

        progressProfile.setProgress(profileScore);



        profile.setProfileScore(profileScore);
        profile.setAtsScore(profileScore);
    }
}