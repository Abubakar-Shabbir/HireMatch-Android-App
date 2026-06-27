package com.example.hirematch.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hirematch.R;
import com.example.hirematch.adapters.OfferAdapter;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Offer;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class MyOffersActivity
        extends AppCompatActivity {

    private RecyclerView rvOffers;
    private TextView tvEmptyState;

    private ArrayList<Offer> offerList;
    private OfferAdapter adapter;

    private String role;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_offers);

        initViews();
        setupRecycler();

        if (FirebaseManager.getAuth().getCurrentUser() != null) {

            currentUserId =
                    FirebaseManager.getAuth()
                            .getCurrentUser()
                            .getUid();

            loadRoleAndOffers();
        }
    }

    private void initViews() {

        rvOffers =
                findViewById(R.id.rvOffers);

        tvEmptyState =
                findViewById(R.id.tvEmptyState);

        offerList =
                new ArrayList<>();
    }

    private void setupRecycler() {

        adapter =
                new OfferAdapter(
                        this,
                        offerList
                );

        rvOffers.setLayoutManager(
                new LinearLayoutManager(this)
        );

        rvOffers.setAdapter(adapter);
    }

    private void loadRoleAndOffers() {

        FirebaseManager.getFirestore()
                .collection("users")
                .document(currentUserId)
                .get()
                .addOnSuccessListener(document -> {

                    if (document.exists()) {

                        role =
                                document.getString("role");

                        loadOffers();
                    }
                });
    }

    private void loadOffers() {

        Query query;

        if ("candidate".equals(role)) {

            query =
                    FirebaseManager.getFirestore()
                            .collection("offers")
                            .whereEqualTo(
                                    "candidateId",
                                    currentUserId
                            );

        } else {

            query =
                    FirebaseManager.getFirestore()
                            .collection("offers")
                            .whereEqualTo(
                                    "hrId",
                                    currentUserId
                            );
        }

        query.get()
                .addOnSuccessListener(snapshot -> {

                    offerList.clear();

                    for (DocumentSnapshot doc :
                            snapshot.getDocuments()) {

                        Offer offer =
                                doc.toObject(
                                        Offer.class
                                );

                        if (offer != null) {

                            offer.setOfferId(
                                    doc.getId()
                            );

                            offerList.add(
                                    offer
                            );
                        }
                    }

                    adapter.notifyDataSetChanged();

                    if (offerList.isEmpty()) {

                        tvEmptyState.setVisibility(
                                View.VISIBLE
                        );

                        rvOffers.setVisibility(
                                View.GONE
                        );

                    } else {

                        tvEmptyState.setVisibility(
                                View.GONE
                        );

                        rvOffers.setVisibility(
                                View.VISIBLE
                        );
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (currentUserId != null) {
            loadOffers();
        }
    }
}