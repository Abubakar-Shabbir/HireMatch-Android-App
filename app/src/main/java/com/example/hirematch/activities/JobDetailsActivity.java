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

public class JobDetailsActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvJobType;
    private TextView tvWorkMode;
    private TextView tvSalary;
    private TextView tvLocation;
    private TextView tvExperience;
    private TextView tvSkills;
    private TextView tvEducation;
    private TextView tvVacancies;
    private TextView tvDeadline;
    private TextView tvBenefits;

    private Button btnApply;

    private String jobId;
    private Job currentJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        initViews();

        jobId = getIntent().getStringExtra("jobId");

        if (jobId != null) {
            loadJob(jobId);
        }

        btnApply.setOnClickListener(v -> applyJob());
    }

    private void initViews() {

        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvJobType = findViewById(R.id.tvJobType);
        tvWorkMode = findViewById(R.id.tvWorkMode);
        tvSalary = findViewById(R.id.tvSalary);
        tvLocation = findViewById(R.id.tvLocation);
        tvExperience = findViewById(R.id.tvExperience);
        tvSkills = findViewById(R.id.tvSkills);
        tvEducation = findViewById(R.id.tvEducation);
        tvVacancies = findViewById(R.id.tvVacancies);
        tvDeadline = findViewById(R.id.tvDeadline);
        tvBenefits = findViewById(R.id.tvBenefits);

        btnApply = findViewById(R.id.btnApply);
    }

    private void loadJob(String jobId) {

        FirebaseManager.getFirestore()
                .collection("jobs")
                .document(jobId)
                .get()
                .addOnSuccessListener(document -> {

                    currentJob = document.toObject(Job.class);

                    if (currentJob == null)
                        return;

                    tvTitle.setText(
                            currentJob.getJobTitle()
                    );

                    tvDescription.setText(
                            currentJob.getJobDescription()
                    );

                    tvJobType.setText(
                            "Job Type : "
                                    + currentJob.getJobType()
                    );

                    tvWorkMode.setText(
                            "Work Mode : "
                                    + currentJob.getWorkMode()
                    );

                    tvSalary.setText(
                            "Salary : "
                                    + currentJob.getSalaryMin()
                                    + " - "
                                    + currentJob.getSalaryMax()
                    );

                    tvLocation.setText(
                            "Location : "
                                    + currentJob.getLocation()
                    );

                    tvExperience.setText(
                            "Experience : "
                                    + currentJob.getExperienceLevel()
                    );

                    tvSkills.setText(
                            "Required Skills\n\n"
                                    + currentJob.getRequiredSkills()
                    );

                    tvEducation.setText(
                            "Education Requirement\n\n"
                                    + currentJob.getEducationRequirement()
                    );

                    tvVacancies.setText(
                            "Vacancies : "
                                    + currentJob.getVacancies()
                    );

                    tvDeadline.setText(
                            "Application Deadline : "
                                    + currentJob.getDeadline()
                    );

                    tvBenefits.setText(
                            "Benefits\n\n"
                                    + currentJob.getBenefits()
                    );
                })
                .addOnFailureListener(e ->
                        Toast.makeText(
                                this,
                                "Failed to load job.",
                                Toast.LENGTH_SHORT
                        ).show());
    }

    private void applyJob() {

        if (currentJob == null) {

            Toast.makeText(
                    this,
                    "Job not loaded yet.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

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

                    Long profileScoreLong =
                            document.getLong("profileScore");

                    int profileScore = 0;

                    if (profileScoreLong != null) {
                        profileScore = profileScoreLong.intValue();
                    }

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
                                            "Application Submitted Successfully\nATS Score : "
                                                    + atsScore + "%",
                                            Toast.LENGTH_LONG
                                    ).show()
                            )
                            .addOnFailureListener(e ->

                                    Toast.makeText(
                                            this,
                                            "Failed to apply.",
                                            Toast.LENGTH_SHORT
                                    ).show()
                            );
                });
    }

    private int calculateATS(
            String candidateSkills,
            String jobSkills) {

        if (candidateSkills == null ||
                jobSkills == null ||
                candidateSkills.isEmpty() ||
                jobSkills.isEmpty()) {

            return 0;
        }

        String[] candidateArray =
                candidateSkills.toLowerCase().split(",");

        String[] jobArray =
                jobSkills.toLowerCase().split(",");

        int matched = 0;

        for (String jobSkill : jobArray) {

            for (String candidateSkill : candidateArray) {

                if (jobSkill.trim().equals(candidateSkill.trim())) {

                    matched++;
                    break;
                }
            }
        }

        return (matched * 100) / jobArray.length;
    }
}