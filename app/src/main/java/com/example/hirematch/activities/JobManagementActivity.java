package com.example.hirematch.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hirematch.R;
import com.example.hirematch.adapters.JobAdapter;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Job;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class JobManagementActivity extends AppCompatActivity {

    private RecyclerView rvJobs;

    private ArrayList<Job> jobList;
    private JobAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_management);

        rvJobs = findViewById(R.id.rvJobs);

        jobList = new ArrayList<>();

        adapter = new JobAdapter(
                this,
                jobList
        );

        rvJobs.setLayoutManager(
                new LinearLayoutManager(this)
        );

        rvJobs.setAdapter(adapter);

        loadJobs();
    }

    private void loadJobs() {

        String hrId =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        FirebaseManager.getFirestore()
                .collection("jobs")
                .whereEqualTo("hrId", hrId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    jobList.clear();

                    for (DocumentSnapshot doc :
                            queryDocumentSnapshots.getDocuments()) {

                        Job job =
                                doc.toObject(Job.class);

                        if (job != null) {

                            jobList.add(job);
                        }
                    }

                    adapter.notifyDataSetChanged();
                });
    }


}
