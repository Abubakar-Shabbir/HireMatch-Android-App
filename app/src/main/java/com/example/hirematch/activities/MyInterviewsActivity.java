package com.example.hirematch.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hirematch.R;
import com.example.hirematch.adapters.InterviewAdapter;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Interview;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class MyInterviewsActivity extends AppCompatActivity {

    private RecyclerView rvInterviews;

    private ArrayList<Interview> interviewList;
    private InterviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_interviews);

        rvInterviews =
                findViewById(R.id.rvInterviews);

        interviewList =
                new ArrayList<>();

        adapter =
                new InterviewAdapter(
                        this,
                        interviewList
                );

        rvInterviews.setLayoutManager(
                new LinearLayoutManager(this)
        );

        rvInterviews.setAdapter(adapter);

        loadInterviews();
    }

    private void loadInterviews() {

        String candidateId =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        FirebaseManager.getFirestore()
                .collection("interviews")
                .whereEqualTo(
                        "candidateId",
                        candidateId
                )
                .get()
                .addOnSuccessListener(query -> {

                    interviewList.clear();

                    for (DocumentSnapshot doc :
                            query.getDocuments()) {

                        Interview interview =
                                doc.toObject(
                                        Interview.class
                                );

                        if (interview != null) {

                            interviewList.add(
                                    interview
                            );
                        }
                    }

                    adapter.notifyDataSetChanged();
                });
    }
}