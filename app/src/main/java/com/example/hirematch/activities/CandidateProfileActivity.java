package com.example.hirematch.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.CandidateProfile;

public class CandidateProfileActivity extends AppCompatActivity {


    private EditText etName;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etCity;
    private EditText etSkills;
    private EditText etEducation;
    private EditText etExperience;

    private Button btnSaveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile);

        initViews();

        btnSaveProfile.setOnClickListener(v -> saveProfile());

        loadProfile();
    }

    private void initViews() {

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etCity = findViewById(R.id.etCity);

        etSkills = findViewById(R.id.etSkills);
        etEducation = findViewById(R.id.etEducation);
        etExperience = findViewById(R.id.etExperience);

        btnSaveProfile = findViewById(R.id.btnSaveProfile);
    }

    private void saveProfile() {

        String uid = FirebaseManager
                .getAuth()
                .getCurrentUser()
                .getUid();

        CandidateProfile profile =
                new CandidateProfile(
                        uid,
                        etName.getText().toString().trim(),
                        etEmail.getText().toString().trim(),
                        etPhone.getText().toString().trim(),
                        etCity.getText().toString().trim(),
                        etSkills.getText().toString().trim(),
                        etEducation.getText().toString().trim(),
                        etExperience.getText().toString().trim(),
                        ""
                );

        FirebaseManager.getFirestore()
                .collection("candidate_profiles")
                .document(uid)
                .set(profile)
                .addOnSuccessListener(unused ->

                        Toast.makeText(
                                this,
                                "Profile Saved Successfully",
                                Toast.LENGTH_SHORT
                        ).show()

                );
    }

    private void loadProfile() {

        String uid = FirebaseManager
                .getAuth()
                .getCurrentUser()
                .getUid();

        FirebaseManager.getFirestore()
                .collection("candidate_profiles")
                .document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {

                    if(documentSnapshot.exists()) {

                        CandidateProfile profile =
                                documentSnapshot.toObject(
                                        CandidateProfile.class
                                );

                        if(profile != null) {

                            etName.setText(profile.getName());
                            etEmail.setText(profile.getEmail());
                            etPhone.setText(profile.getPhone());
                            etCity.setText(profile.getCity());

                            etSkills.setText(profile.getSkills());
                            etEducation.setText(profile.getEducation());
                            etExperience.setText(profile.getExperience());
                        }
                    }
                });
    }


}
