package com.example.hirematch.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Job;

public class CreateJobActivity extends AppCompatActivity {


    private EditText etTitle, etDescription,
            etSkills, etExperience,
            etLocation, etSalary;

    private Button btnCreateJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);

        initViews();

        btnCreateJob.setOnClickListener(v -> createJob());
    }

    private void initViews() {

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etSkills = findViewById(R.id.etSkills);
        etExperience = findViewById(R.id.etExperience);
        etLocation = findViewById(R.id.etLocation);
        etSalary = findViewById(R.id.etSalary);

        btnCreateJob = findViewById(R.id.btnCreateJob);
    }

    private void createJob() {

        String jobId =
                FirebaseManager.getFirestore()
                        .collection("jobs")
                        .document()
                        .getId();

        String hrId =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        Job job = new Job(
                jobId,
                hrId,
                etTitle.getText().toString().trim(),
                etDescription.getText().toString().trim(),
                etSkills.getText().toString().trim(),
                etExperience.getText().toString().trim(),
                etLocation.getText().toString().trim(),
                etSalary.getText().toString().trim(),
                "Open"
        );

        FirebaseManager.getFirestore()
                .collection("jobs")
                .document(jobId)
                .set(job)
                .addOnSuccessListener(unused ->

                        Toast.makeText(
                                this,
                                "Job Created Successfully",
                                Toast.LENGTH_SHORT
                        ).show()

                );
    }


}
