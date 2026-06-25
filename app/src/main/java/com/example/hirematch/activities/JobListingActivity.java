package com.example.hirematch.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hirematch.R;
import com.example.hirematch.adapters.CandidateJobAdapter;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Job;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class JobListingActivity extends AppCompatActivity {

    private EditText etSearch;
    private EditText etLocationFilter;
    private RecyclerView rvJobs;

    private ArrayList<Job> jobList;
    private ArrayList<Job> filteredList;

    private CandidateJobAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_listing);

        initViews();

        jobList = new ArrayList<>();
        filteredList = new ArrayList<>();

        adapter = new CandidateJobAdapter(
                this,
                filteredList
        );

        rvJobs.setLayoutManager(
                new LinearLayoutManager(this)
        );

        rvJobs.setAdapter(adapter);

        loadJobs();

        setupSearch();
        setupLocationFilter();
    }

    private void initViews() {

        etSearch =
                findViewById(R.id.etSearch);

        etLocationFilter =
                findViewById(
                        R.id.etLocationFilter
                );

        rvJobs =
                findViewById(R.id.rvJobs);
    }

    private void loadJobs() {

        FirebaseManager.getFirestore()
                .collection("jobs")
                .whereEqualTo("status", "Open")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    jobList.clear();
                    filteredList.clear();

                    for (DocumentSnapshot doc :
                            queryDocumentSnapshots.getDocuments()) {

                        Job job =
                                doc.toObject(Job.class);

                        if (job != null) {

                            jobList.add(job);
                            filteredList.add(job);
                        }
                    }

                    adapter.notifyDataSetChanged();
                });
    }

    private void setupSearch() {

        etSearch.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after) {
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count) {

                        filterJobs(
                                s.toString()
                        );
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s) {
                    }
                });
    }

    private void setupLocationFilter() {

        etLocationFilter.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after) {
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count) {

                        filterByLocation(
                                s.toString()
                        );
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s) {
                    }
                });
    }

    private void filterJobs(String query) {

        filteredList.clear();

        for (Job job : jobList) {

            if (job.getTitle()
                    .toLowerCase()
                    .contains(
                            query.toLowerCase()
                    )) {

                filteredList.add(job);
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void filterByLocation(
            String location) {

        filteredList.clear();

        for (Job job : jobList) {

            if (job.getLocation()
                    .toLowerCase()
                    .contains(
                            location.toLowerCase()
                    )) {

                filteredList.add(job);
            }
        }

        adapter.notifyDataSetChanged();
    }
}