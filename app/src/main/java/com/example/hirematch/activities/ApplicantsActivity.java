package com.example.hirematch.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hirematch.R;
import com.example.hirematch.adapters.ApplicantAdapter;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Application;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Collections;

public class ApplicantsActivity
        extends AppCompatActivity {

    private RecyclerView rvApplicants;
    private TextView tvTotalApplicants;
    private TextView tvTopScore;
    private TextView tvEmptyState;

    private ArrayList<Application> applicantList;
    private ApplicantAdapter adapter;

    private String jobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicants);

        jobId =
                getIntent().getStringExtra(
                        "jobId"
                );

        initViews();
        setupRecycler();
        loadApplicants();
    }

    private void initViews() {

        rvApplicants =
                findViewById(R.id.rvApplicants);

        tvTotalApplicants =
                findViewById(R.id.tvTotalApplicants);

        tvTopScore =
                findViewById(R.id.tvTopScore);

        tvEmptyState =
                findViewById(R.id.tvEmptyState);

        applicantList =
                new ArrayList<>();
    }

    private void setupRecycler() {

        adapter =
                new ApplicantAdapter(
                        this,
                        applicantList
                );

        rvApplicants.setLayoutManager(
                new LinearLayoutManager(this)
        );

        rvApplicants.setAdapter(adapter);
    }

    private void loadApplicants() {

        String hrId =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        Query query =
                FirebaseManager.getFirestore()
                        .collection("applications")
                        .whereEqualTo("hrId", hrId);

        if (jobId != null) {
            query =
                    query.whereEqualTo(
                            "jobId",
                            jobId
                    );
        }

        query.addSnapshotListener(
                (snapshot, error) -> {

                    if (error != null)
                        return;

                    if (snapshot == null)
                        return;

                    applicantList.clear();

                    for (var doc :
                            snapshot.getDocuments()) {

                        Application application =
                                doc.toObject(
                                        Application.class
                                );

                        if (application != null) {

                            application.setApplicationId(
                                    doc.getId()
                            );

                            applicantList.add(
                                    application
                            );
                        }
                    }

                    Collections.sort(
                            applicantList,
                            (a1, a2) ->
                                    Integer.compare(
                                            a2.getAtsScore(),
                                            a1.getAtsScore()
                                    )
                    );

                    adapter.notifyDataSetChanged();

                    tvTotalApplicants.setText(
                            String.valueOf(
                                    applicantList.size()
                            )
                    );

                    if (applicantList.size() > 0) {

                        tvTopScore.setText(
                                applicantList.get(0)
                                        .getAtsScore() + "%"
                        );

                        tvEmptyState.setVisibility(
                                View.GONE
                        );

                        rvApplicants.setVisibility(
                                View.VISIBLE
                        );

                    } else {

                        tvTopScore.setText("0%");

                        tvEmptyState.setVisibility(
                                View.VISIBLE
                        );

                        rvApplicants.setVisibility(
                                View.GONE
                        );
                    }
                }
        );
    }
}