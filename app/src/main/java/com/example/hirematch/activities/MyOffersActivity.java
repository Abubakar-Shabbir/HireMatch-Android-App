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
import android.widget.LinearLayout;
public class MyOffersActivity extends AppCompatActivity {

    private RecyclerView rvOffers;
    private LinearLayout layoutEmpty;

    private ArrayList<Offer> offerList;
    private OfferAdapter adapter;

    private String currentUserId;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_offers);

        initViews();
        setupRecyclerView();

        if (FirebaseManager.getAuth().getCurrentUser() != null) {

            currentUserId =
                    FirebaseManager.getAuth()
                            .getCurrentUser()
                            .getUid();

            loadUserRole();
        }
    }

    private void initViews() {

        rvOffers = findViewById(R.id.rvOffers);
        layoutEmpty = findViewById(R.id.layoutEmpty);

        offerList = new ArrayList<>();
    }

    private void setupRecyclerView() {

        adapter = new OfferAdapter(
                this,
                offerList
        );

        rvOffers.setLayoutManager(
                new LinearLayoutManager(this)
        );

        rvOffers.setAdapter(adapter);
    }

    private void loadUserRole() {

        FirebaseManager.getFirestore()
                .collection("users")
                .document(currentUserId)
                .get()
                .addOnSuccessListener(document -> {

                    if (document.exists()) {

                        role = document.getString("role");

                        loadOffers();
                    }
                });
    }

    private void loadOffers() {

        Query query;

        if ("candidate".equals(role)) {

            query = FirebaseManager.getFirestore()
                    .collection("offers")
                    .whereEqualTo(
                            "candidateId",
                            currentUserId
                    );

        } else {

            query = FirebaseManager.getFirestore()
                    .collection("offers")
                    .whereEqualTo(
                            "hrId",
                            currentUserId
                    );
        }

        query.get()
                .addOnSuccessListener(querySnapshot -> {

                    offerList.clear();

                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {

                        Offer offer =
                                document.toObject(
                                        Offer.class
                                );

                        if (offer != null) {

                            offer.setOfferId(
                                    document.getId()
                            );

                            offerList.add(
                                    offer
                            );
                        }
                    }

                    adapter.notifyDataSetChanged();
                    updateUI();
                });
    }

    private void updateUI() {

        adapter.notifyDataSetChanged();

        if (offerList.isEmpty()) {

            layoutEmpty.setVisibility(View.VISIBLE);
            rvOffers.setVisibility(View.GONE);

        } else {

            layoutEmpty.setVisibility(View.GONE);
            rvOffers.setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (currentUserId != null && role != null) {

            loadOffers();
        }
    }
}