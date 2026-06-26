package com.example.hirematch.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Job;

public class EditJobActivity extends AppCompatActivity {


    private EditText etTitle;
    private EditText etDescription;
    private EditText etSkills;
    private EditText etExperience;
    private EditText etLocation;
    private EditText etSalary;

    private Button btnUpdateJob;

    private String jobId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job);

        initViews();

        jobId = getIntent().getStringExtra("jobId");

        loadJob();

        btnUpdateJob.setOnClickListener(v -> updateJob());
    }

    private void initViews() {

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etSkills = findViewById(R.id.etSkills);
        etExperience = findViewById(R.id.etExperience);
        etLocation = findViewById(R.id.etLocation);
        etSalary = findViewById(R.id.etSalary);

        btnUpdateJob =
                findViewById(R.id.btnUpdateJob);
    }

    private void loadJob() {

        FirebaseManager.getFirestore()
                .collection("jobs")
                .document(jobId)
                .get()
                .addOnSuccessListener(document -> {

                    Job job =
                            document.toObject(
                                    Job.class
                            );

                    if(job != null) {

                        etTitle.setText(
                                job.getTitle()
                        );

                        etDescription.setText(
                                job.getDescription()
                        );

                        etSkills.setText(
                                job.getSkills()
                        );

                        etExperience.setText(
                                job.getExperience()
                        );

                        etLocation.setText(
                                job.getLocation()
                        );

                        etSalary.setText(
                                job.getSalary()
                        );
                    }
                });
    }

    private void updateJob() {

        FirebaseManager.getFirestore()
                .collection("jobs")
                .document(jobId)
                .update(
                        "title",
                        etTitle.getText().toString(),

                        "description",
                        etDescription.getText().toString(),

                        "skills",
                        etSkills.getText().toString(),

                        "experience",
                        etExperience.getText().toString(),

                        "location",
                        etLocation.getText().toString(),

                        "salary",
                        etSalary.getText().toString()
                )
                .addOnSuccessListener(unused -> {

                    Toast.makeText(
                            this,
                            "Job Updated Successfully",
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();
                });
    }


}
