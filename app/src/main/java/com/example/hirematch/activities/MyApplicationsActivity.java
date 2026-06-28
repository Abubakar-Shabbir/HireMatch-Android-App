package com.example.hirematch.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hirematch.R;
import com.example.hirematch.adapters.ApplicationAdapter;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Application;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import com.example.hirematch.utils.LoadingManager;
public class MyApplicationsActivity extends AppCompatActivity {


    private RecyclerView rvApplications;

    private ArrayList<Application> applicationList;
    private ApplicationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_applications);
        LoadingManager.show(this);
        rvApplications =
                findViewById(
                        R.id.rvApplications
                );

        applicationList =
                new ArrayList<>();

        adapter =
                new ApplicationAdapter(
                        this,
                        applicationList
                );

        rvApplications.setLayoutManager(
                new LinearLayoutManager(this)
        );

        rvApplications.setAdapter(adapter);

        loadApplications();
    }

    private void loadApplications() {

        String candidateId =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        FirebaseManager.getFirestore()
                .collection("applications")
                .whereEqualTo(
                        "candidateId",
                        candidateId
                )
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    LoadingManager.hide();
                    applicationList.clear();

                    for (DocumentSnapshot doc :
                            queryDocumentSnapshots.getDocuments()) {

                        Application application =
                                doc.toObject(
                                        Application.class
                                );

                        if (application != null) {

                            applicationList.add(
                                    application
                            );
                        }
                    }

                    adapter.notifyDataSetChanged();
                });
    }


}
