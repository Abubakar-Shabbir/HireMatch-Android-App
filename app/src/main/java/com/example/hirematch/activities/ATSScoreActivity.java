package com.example.hirematch.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.utils.LoadingManager;
public class ATSScoreActivity extends AppCompatActivity {

    private TextView tvOverallScore;
    private TextView tvProfileStatus;
    private TextView tvSkillsScore;
    private TextView tvEducationScore;
    private TextView tvExperienceScore;
    private TextView tvResumeScore;
    private TextView tvContactScore;
    private TextView tvRecommendations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ats_score);
        LoadingManager.show(this);
        initViews();

        calculateATS();
        LoadingManager.hide();
    }

    private void initViews() {

        tvOverallScore = findViewById(R.id.tvOverallScore);
        tvProfileStatus = findViewById(R.id.tvProfileStatus);

        tvSkillsScore = findViewById(R.id.tvSkillsScore);
        tvEducationScore = findViewById(R.id.tvEducationScore);
        tvExperienceScore = findViewById(R.id.tvExperienceScore);
        tvResumeScore = findViewById(R.id.tvResumeScore);
        tvContactScore = findViewById(R.id.tvContactScore);

        tvRecommendations = findViewById(R.id.tvRecommendations);
    }

    private void calculateATS() {

        String uid = FirebaseManager
                .getAuth()
                .getCurrentUser()
                .getUid();

        FirebaseManager.getFirestore()
                .collection("candidate_profiles")
                .document(uid)
                .get()
                .addOnSuccessListener(document -> {

                    int skillsScore = 0;
                    int educationScore = 0;
                    int experienceScore = 0;
                    int resumeScore = 0;
                    int contactScore = 0;

                    StringBuilder recommendations =
                            new StringBuilder();

                    String skills =
                            document.getString("skills");

                    String education =
                            document.getString("education");

                    String experience =
                            document.getString("experience");

                    String resume =
                            document.getString("resumeUrl");

                    String phone =
                            document.getString("phone");

                    String city =
                            document.getString("city");

                    // Skills
                    if(skills != null &&
                            !skills.trim().isEmpty()) {

                        if(skills.split(",").length >= 3)
                            skillsScore = 20;
                        else
                            skillsScore = 10;

                    } else {

                        recommendations.append(
                                "• Add Skills\n");
                    }

                    // Education
                    if(education != null &&
                            !education.trim().isEmpty()) {

                        educationScore = 20;

                    } else {

                        recommendations.append(
                                "• Add Education\n");
                    }

                    // Experience
                    if(experience != null &&
                            !experience.trim().isEmpty()) {

                        if(experience.length() > 30)
                            experienceScore = 20;
                        else
                            experienceScore = 10;

                    } else {

                        recommendations.append(
                                "• Add Experience\n");
                    }

                    // Resume
                    if(resume != null &&
                            !resume.trim().isEmpty()) {

                        resumeScore = 20;

                    } else {

                        recommendations.append(
                                "• Upload Resume\n");
                    }

                    // Contact Info
                    if(phone != null &&
                            !phone.trim().isEmpty()) {

                        contactScore += 10;
                    }

                    if(city != null &&
                            !city.trim().isEmpty()) {

                        contactScore += 10;
                    }

                    if(contactScore < 20) {

                        recommendations.append(
                                "• Complete Contact Information\n");
                    }

                    int totalScore =
                            skillsScore +
                                    educationScore +
                                    experienceScore +
                                    resumeScore +
                                    contactScore;

                    String profileStatus;

                    if(totalScore >= 80) {

                        profileStatus = "Strong Profile";

                    } else if(totalScore >= 60) {

                        profileStatus = "Good Profile";

                    } else if(totalScore >= 40) {

                        profileStatus = "Average Profile";

                    } else {

                        profileStatus = "Weak Profile";
                    }

                    tvOverallScore.setText(
                            totalScore + "%");

                    tvProfileStatus.setText(
                            profileStatus);

                    tvSkillsScore.setText(
                            "Skills Score: "
                                    + skillsScore + "/20");

                    tvEducationScore.setText(
                            "Education Score: "
                                    + educationScore + "/20");

                    tvExperienceScore.setText(
                            "Experience Score: "
                                    + experienceScore + "/20");

                    tvResumeScore.setText(
                            "Resume Score: "
                                    + resumeScore + "/20");

                    tvContactScore.setText(
                            "Contact Score: "
                                    + contactScore + "/20");

                    if(recommendations.length() == 0) {

                        tvRecommendations.setText(
                                "Excellent profile. No improvements required.");

                    } else {

                        tvRecommendations.setText(
                                recommendations.toString());
                    }
                });
    }
}