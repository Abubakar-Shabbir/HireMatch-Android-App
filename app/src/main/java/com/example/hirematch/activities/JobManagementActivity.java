package com.example.hirematch.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hirematch.R;
import com.example.hirematch.adapters.JobAdapter;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Job;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class JobManagementActivity
        extends AppCompatActivity {

    private RecyclerView rvJobs;
    private TextView tvEmptyState;
    private TextView tvTotalJobs;

    private ArrayList<Job> jobList;
    private JobAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_management);

        initViews();
        setupRecycler();
        loadJobs();
    }

    private void initViews() {

        rvJobs =
                findViewById(R.id.rvJobs);

        tvEmptyState =
                findViewById(R.id.tvEmptyState);

        tvTotalJobs =
                findViewById(R.id.tvTotalJobs);

        jobList =
                new ArrayList<>();
    }

    private void setupRecycler() {

        adapter =
                new JobAdapter(
                        this,
                        jobList
                );

        rvJobs.setLayoutManager(
                new LinearLayoutManager(this)
        );

        rvJobs.setAdapter(adapter);
    }

    private void loadJobs() {

        String hrId =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        FirebaseManager.getFirestore()
                .collection("jobs")
                .whereEqualTo("hrId", hrId)
                .orderBy(
                        "createdAt",
                        Query.Direction.DESCENDING
                )
                .addSnapshotListener((value, error) -> {

                    if (value == null)
                        return;

                    jobList.clear();
                    for (var doc : value.getDocuments()) {

                        Job job =
                                doc.toObject(Job.class);

                        if (job != null) {

                            job.setJobId(
                                    doc.getId()
                            );

                            jobList.add(
                                    job
                            );
                        }
                    }

                    adapter.notifyDataSetChanged();

                    tvTotalJobs.setText(
                            String.valueOf(
                                    jobList.size()
                            )
                    );

                    if (jobList.isEmpty()) {

                        tvEmptyState.setVisibility(
                                View.VISIBLE
                        );

                        rvJobs.setVisibility(
                                View.GONE
                        );

                    } else {

                        tvEmptyState.setVisibility(
                                View.GONE
                        );

                        rvJobs.setVisibility(
                                View.VISIBLE
                        );
                    }
                });
    }
}