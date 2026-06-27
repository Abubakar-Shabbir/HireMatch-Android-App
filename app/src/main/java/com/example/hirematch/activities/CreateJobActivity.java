package com.example.hirematch.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Company;
import com.example.hirematch.models.Job;

import java.util.Calendar;

public class CreateJobActivity
        extends AppCompatActivity {

    private EditText etJobTitle, etJobDescription;
    private EditText etJobType, etWorkMode;
    private EditText etSalaryMin, etSalaryMax;
    private EditText etLocation, etExperienceLevel;
    private EditText etRequiredSkills, etEducationRequirement;
    private EditText etVacancies, etDeadline, etBenefits;

    private Button btnCreateJob;

    private String companyId = "";
    private String companyName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);

        initViews();
        setupDeadlinePicker();
        loadCompanyData();

        btnCreateJob.setOnClickListener(v ->
                createJob()
        );
    }

    private void initViews() {

        etJobTitle = findViewById(R.id.etJobTitle);
        etJobDescription = findViewById(R.id.etJobDescription);

        etJobType = findViewById(R.id.etJobType);
        etWorkMode = findViewById(R.id.etWorkMode);

        etSalaryMin = findViewById(R.id.etSalaryMin);
        etSalaryMax = findViewById(R.id.etSalaryMax);

        etLocation = findViewById(R.id.etLocation);
        etExperienceLevel = findViewById(R.id.etExperienceLevel);

        etRequiredSkills =
                findViewById(R.id.etRequiredSkills);

        etEducationRequirement =
                findViewById(R.id.etEducationRequirement);

        etVacancies =
                findViewById(R.id.etVacancies);

        etDeadline =
                findViewById(R.id.etDeadline);

        etBenefits =
                findViewById(R.id.etBenefits);

        btnCreateJob =
                findViewById(R.id.btnCreateJob);
    }

    private void setupDeadlinePicker() {

        etDeadline.setOnClickListener(v -> {

            Calendar calendar =
                    Calendar.getInstance();

            DatePickerDialog dialog =
                    new DatePickerDialog(
                            this,
                            (view, year, month, day) ->
                                    etDeadline.setText(
                                            day + "/" +
                                                    (month + 1) + "/" +
                                                    year
                                    ),
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                    );

            dialog.show();
        });
    }

    private void loadCompanyData() {

        String hrId =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        FirebaseManager.getFirestore()
                .collection("companies")
                .document(hrId)
                .get()
                .addOnSuccessListener(document -> {

                    if (document.exists()) {

                        Company company =
                                document.toObject(
                                        Company.class
                                );

                        if (company != null) {
                            companyId = company.getHrId();
                            companyName =
                                    company.getCompanyName();
                        }
                    }
                });
    }

    private void createJob() {

        if (etJobTitle.getText().toString().trim().isEmpty()) {
            etJobTitle.setError("Required");
            return;
        }

        if (etJobDescription.getText().toString().trim().isEmpty()) {
            etJobDescription.setError("Required");
            return;
        }

        String jobId =
                FirebaseManager.getFirestore()
                        .collection("jobs")
                        .document()
                        .getId();

        String hrId =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        Job job =
                new Job(
                        jobId,
                        hrId,
                        companyId,
                        companyName,
                        etJobTitle.getText().toString().trim(),
                        etJobDescription.getText().toString().trim(),
                        etJobType.getText().toString().trim(),
                        etWorkMode.getText().toString().trim(),
                        etSalaryMin.getText().toString().trim(),
                        etSalaryMax.getText().toString().trim(),
                        etLocation.getText().toString().trim(),
                        etExperienceLevel.getText().toString().trim(),
                        etRequiredSkills.getText().toString().trim(),
                        etEducationRequirement.getText().toString().trim(),
                        etVacancies.getText().toString().trim(),
                        etDeadline.getText().toString().trim(),
                        etBenefits.getText().toString().trim(),
                        "Open",
                        String.valueOf(
                                System.currentTimeMillis()
                        )
                );

        FirebaseManager.getFirestore()
                .collection("jobs")
                .document(jobId)
                .set(job)
                .addOnSuccessListener(unused -> {

                    Toast.makeText(
                            this,
                            "Job Created Successfully",
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();
                })
                .addOnFailureListener(e ->

                        Toast.makeText(
                                this,
                                e.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show()
                );
    }
}