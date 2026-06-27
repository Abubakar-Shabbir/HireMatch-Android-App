package com.example.hirematch.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Application;
import com.example.hirematch.models.Job;

public class JobDetailsActivity
        extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvSkills;
    private TextView tvExperience;
    private TextView tvLocation;
    private TextView tvSalary;

    private Button btnApply;

    private String jobId;
    private Job currentJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        initViews();

        jobId = getIntent().getStringExtra("jobId");

        loadJob(jobId);

        btnApply.setOnClickListener(v ->
                applyJob()
        );
    }

    private void initViews() {

        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvSkills = findViewById(R.id.tvSkills);
        tvExperience = findViewById(R.id.tvExperience);
        tvLocation = findViewById(R.id.tvLocation);
        tvSalary = findViewById(R.id.tvSalary);

        btnApply = findViewById(R.id.btnApply);
    }

    private void loadJob(String jobId) {

        FirebaseManager.getFirestore()
                .collection("jobs")
                .document(jobId)
                .get()
                .addOnSuccessListener(document -> {

                    currentJob =
                            document.toObject(Job.class);

                    if (currentJob != null) {

                        tvTitle.setText(
                                currentJob.getJobTitle()
                        );

                        tvDescription.setText(
                                currentJob.getJobDescription()
                        );

                        tvSkills.setText(
                                "Skills: " +
                                        currentJob.getRequiredSkills()
                        );

                        tvExperience.setText(
                                "Experience: " +
                                        currentJob.getExperienceLevel()
                        );

                        tvLocation.setText(
                                "Location: " +
                                        currentJob.getLocation()
                        );

                        tvSalary.setText(
                                "Salary: " +
                                        currentJob.getSalaryMin()
                                        + " - " +
                                        currentJob.getSalaryMax()
                        );
                    }
                });
    }

    private void applyJob() {

        String applicationId =
                FirebaseManager.getFirestore()
                        .collection("applications")
                        .document()
                        .getId();

        String candidateId =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        FirebaseManager.getFirestore()
                .collection("candidate_profiles")
                .document(candidateId)
                .get()
                .addOnSuccessListener(document -> {

                    String name =
                            document.getString("name");

                    String email =
                            document.getString("email");

                    String phone =
                            document.getString("phone");

                    String resumeUrl =
                            document.getString("resumeUrl");

                    String candidateSkills =
                            document.getString("skills");

                    String jobSkills =
                            currentJob.getRequiredSkills();

                    int atsScore =
                            calculateATS(
                                    candidateSkills,
                                    jobSkills
                            );

                    int profileScore = 80;

                    Application application =
                            new Application(
                                    applicationId,
                                    currentJob.getJobId(),
                                    currentJob.getJobTitle(),
                                    currentJob.getHrId(),

                                    candidateId,
                                    name,
                                    email,
                                    phone,
                                    resumeUrl,

                                    atsScore,
                                    profileScore,

                                    "Applied",
                                    "Screening",

                                    "",
                                    "",

                                    "",
                                    "Pending",

                                    System.currentTimeMillis(),
                                    System.currentTimeMillis()
                            );

                    FirebaseManager.getFirestore()
                            .collection("applications")
                            .document(applicationId)
                            .set(application)
                            .addOnSuccessListener(unused ->

                                    Toast.makeText(
                                            this,
                                            "Applied Successfully | ATS Score: "
                                                    + atsScore + "%",
                                            Toast.LENGTH_LONG
                                    ).show()
                            );
                });
    }

    private int calculateATS(
            String candidateSkills,
            String jobSkills) {

        if (candidateSkills == null ||
                jobSkills == null) {
            return 0;
        }

        String[] candidateArray =
                candidateSkills.toLowerCase().split(",");

        String[] jobArray =
                jobSkills.toLowerCase().split(",");

        int matched = 0;

        for (String jobSkill : jobArray) {
            for (String candidateSkill : candidateArray) {

                if (jobSkill.trim().equals(
                        candidateSkill.trim()
                )) {
                    matched++;
                }
            }
        }

        return (matched * 100) / jobArray.length;
    }
}