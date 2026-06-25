package com.example.hirematch.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hirematch.R;
import com.example.hirematch.adapters.ApplicantAdapter;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Application;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ApplicantsActivity extends AppCompatActivity {


    private RecyclerView rvApplicants;

    private ArrayList<Application> applicantList;
    private ApplicantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicants);

        rvApplicants =
                findViewById(
                        R.id.rvApplicants
                );

        applicantList =
                new ArrayList<>();

        adapter =
                new ApplicantAdapter(
                        this,
                        applicantList
                );

        rvApplicants.setLayoutManager(
                new LinearLayoutManager(this)
        );

        rvApplicants.setAdapter(adapter);

        loadApplicants();
    }

    private void loadApplicants() {

        String hrId =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        FirebaseManager.getFirestore()
                .collection("applications")
                .whereEqualTo(
                        "hrId",
                        hrId
                )
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    applicantList.clear();

                    for (DocumentSnapshot doc :
                            queryDocumentSnapshots.getDocuments()) {

                        Application application =
                                doc.toObject(
                                        Application.class
                                );

                        if (application != null) {

                            applicantList.add(
                                    application
                            );
                        }
                    }

                    sortApplicantsByATS();

                    adapter.notifyDataSetChanged();
                });
    }

    private void sortApplicantsByATS() {

        Collections.sort(
                applicantList,
                new Comparator<Application>() {
                    @Override
                    public int compare(
                            Application a1,
                            Application a2) {

                        return Integer.compare(
                                a2.getAtsScore(),
                                a1.getAtsScore()
                        );
                    }
                }
        );
    }


}
