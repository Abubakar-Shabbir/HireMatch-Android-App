package com.example.hirematch.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hirematch.R;
import com.example.hirematch.adapters.InterviewAdapter;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Application;
import com.example.hirematch.models.Interview;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashSet;

public class MyInterviewsActivity extends AppCompatActivity {

    private RecyclerView rvInterviews;
    private TextView tvEmptyState;

    private ArrayList<Interview> interviewList;
    private InterviewAdapter adapter;

    private String role;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_interviews);

        rvInterviews = findViewById(R.id.rvInterviews);
        tvEmptyState = findViewById(R.id.tvEmptyState);

        interviewList = new ArrayList<>();

        adapter = new InterviewAdapter(this, interviewList);

        rvInterviews.setLayoutManager(new LinearLayoutManager(this));
        rvInterviews.setAdapter(adapter);

        if (FirebaseManager.getAuth().getCurrentUser() != null) {

            currentUserId =
                    FirebaseManager.getAuth()
                            .getCurrentUser()
                            .getUid();

            FirebaseManager.getFirestore()
                    .collection("users")
                    .document(currentUserId)
                    .get()
                    .addOnSuccessListener(document -> {

                        if (document.exists()) {

                            role = document.getString("role");

                            loadInterviews();
                        }
                    });
        }
    }

    private void loadInterviews() {

        if ("candidate".equals(role)) {

            loadCandidateInterviews();

        } else {

            loadHRInterviews();
        }
    }

    private void loadCandidateInterviews() {

        FirebaseManager.getFirestore()
                .collection("interviews")
                .whereEqualTo("candidateId", currentUserId)
                .get()
                .addOnSuccessListener(snapshot -> {

                    interviewList.clear();

                    for (DocumentSnapshot doc : snapshot.getDocuments()) {

                        Interview interview =
                                doc.toObject(Interview.class);

                        if (interview != null) {

                            interview.setInterviewId(doc.getId());

                            interviewList.add(interview);
                        }
                    }

                    updateUI();
                });
    }

    private void loadHRInterviews() {

        FirebaseManager.getFirestore()
                .collection("interviews")
                .whereEqualTo("hrId", currentUserId)
                .get()
                .addOnSuccessListener(interviewSnapshot -> {

                    interviewList.clear();

                    HashSet<String> applicationIds =
                            new HashSet<>();

                    for (DocumentSnapshot doc :
                            interviewSnapshot.getDocuments()) {

                        Interview interview =
                                doc.toObject(Interview.class);

                        if (interview != null) {

                            interview.setInterviewId(doc.getId());

                            interviewList.add(interview);

                            applicationIds.add(
                                    interview.getApplicationId()
                            );
                        }
                    }

                    FirebaseManager.getFirestore()
                            .collection("applications")
                            .whereEqualTo(
                                    "hrId",
                                    currentUserId
                            )
                            .whereEqualTo(
                                    "applicationStatus",
                                    "Shortlisted"
                            )
                            .get()
                            .addOnSuccessListener(applicationSnapshot -> {

                                for (DocumentSnapshot doc :
                                        applicationSnapshot.getDocuments()) {

                                    Application application =
                                            doc.toObject(Application.class);

                                    if (application == null)
                                        continue;

                                    application.setApplicationId(
                                            doc.getId()
                                    );

                                    if (!applicationIds.contains(
                                            application.getApplicationId())) {

                                        Interview interview =
                                                new Interview(
                                                        "",
                                                        application.getApplicationId(),
                                                        application.getJobId(),
                                                        application.getCandidateId(),
                                                        application.getCandidateName(),
                                                        application.getHrId(),
                                                        application.getJobTitle(),
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "Pending",
                                                        ""
                                                );

                                        interviewList.add(interview);
                                    }
                                }

                                updateUI();
                            });
                });
    }

    private void updateUI() {

        adapter.notifyDataSetChanged();

        if (interviewList.isEmpty()) {

            tvEmptyState.setVisibility(View.VISIBLE);
            rvInterviews.setVisibility(View.GONE);

        } else {

            tvEmptyState.setVisibility(View.GONE);
            rvInterviews.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (currentUserId != null) {
            loadInterviews();
        }
    }
}