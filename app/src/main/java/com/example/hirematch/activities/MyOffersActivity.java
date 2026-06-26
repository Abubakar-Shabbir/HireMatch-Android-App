package com.example.hirematch.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hirematch.R;
import com.example.hirematch.adapters.OfferAdapter;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Offer;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class MyOffersActivity extends AppCompatActivity {

    private RecyclerView rvOffers;

    private ArrayList<Offer> offerList;
    private OfferAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_offers);

        rvOffers =
                findViewById(
                        R.id.rvOffers
                );

        offerList =
                new ArrayList<>();

        adapter =
                new OfferAdapter(
                        this,
                        offerList
                );

        rvOffers.setLayoutManager(
                new LinearLayoutManager(this)
        );

        rvOffers.setAdapter(adapter);

        loadOffers();
    }

    private void loadOffers() {

        String candidateId =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        FirebaseManager.getFirestore()
                .collection("offers")
                .whereEqualTo(
                        "candidateId",
                        candidateId
                )
                .get()
                .addOnSuccessListener(query -> {

                    offerList.clear();

                    for (DocumentSnapshot doc :
                            query.getDocuments()) {

                        Offer offer =
                                doc.toObject(
                                        Offer.class
                                );

                        if (offer != null) {
                            offerList.add(offer);
                        }
                    }

                    adapter.notifyDataSetChanged();
                });
    }
}