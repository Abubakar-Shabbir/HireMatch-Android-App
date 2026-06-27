package com.example.hirematch.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Notification;
import com.example.hirematch.models.Offer;

public class OfferDetailsActivity
        extends AppCompatActivity {

    private TextView tvJobTitle;
    private TextView tvSalary;
    private TextView tvJoiningDate;
    private TextView tvOfferStatus;

    private Button btnAcceptOffer;
    private Button btnRejectOffer;

    private String offerId;
    private Offer currentOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);

        offerId =
                getIntent().getStringExtra(
                        "offerId"
                );

        initViews();
        loadOfferDetails();
    }

    private void initViews() {

        tvJobTitle =
                findViewById(R.id.tvJobTitle);

        tvSalary =
                findViewById(R.id.tvSalary);

        tvJoiningDate =
                findViewById(R.id.tvJoiningDate);

        tvOfferStatus =
                findViewById(R.id.tvOfferStatus);

        btnAcceptOffer =
                findViewById(R.id.btnAcceptOffer);

        btnRejectOffer =
                findViewById(R.id.btnRejectOffer);

        btnAcceptOffer.setOnClickListener(v ->
                updateOfferStatus("Accepted")
        );

        btnRejectOffer.setOnClickListener(v ->
                updateOfferStatus("Rejected")
        );
    }

    private void loadOfferDetails() {

        FirebaseManager.getFirestore()
                .collection("offers")
                .document(offerId)
                .get()
                .addOnSuccessListener(document -> {

                    if (!document.exists()) {
                        finish();
                        return;
                    }

                    currentOffer =
                            document.toObject(
                                    Offer.class
                            );

                    if (currentOffer == null)
                        return;

                    tvJobTitle.setText(
                            currentOffer.getJobTitle()
                    );

                    tvSalary.setText(
                            "Salary: " +
                                    currentOffer.getSalary()
                    );

                    tvJoiningDate.setText(
                            "Joining Date: " +
                                    currentOffer.getJoiningDate()
                    );

                    tvOfferStatus.setText(
                            "Status: " +
                                    currentOffer.getOfferStatus()
                    );

                    handleButtonState();
                });
    }

    private void handleButtonState() {

        String status =
                currentOffer.getOfferStatus();

        if ("Accepted".equals(status) ||
                "Rejected".equals(status)) {

            btnAcceptOffer.setEnabled(false);
            btnRejectOffer.setEnabled(false);
        }
    }

    private void updateOfferStatus(String status) {

        FirebaseManager.getFirestore()
                .collection("offers")
                .document(offerId)
                .update(
                        "offerStatus",
                        status
                )
                .addOnSuccessListener(unused -> {

                    updateApplication(status);
                    createNotification(status);

                    Toast.makeText(
                            this,
                            "Offer " + status,
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();
                });
    }

    private void updateApplication(String status) {

        if (currentOffer == null)
            return;

        if ("Accepted".equals(status)) {

            FirebaseManager.getFirestore()
                    .collection("applications")
                    .document(
                            currentOffer.getApplicationId()
                    )
                    .update(
                            "applicationStatus", "Hired",
                            "currentStage", "Finalized",
                            "offerStatus", "Accepted"
                    );

        } else {

            FirebaseManager.getFirestore()
                    .collection("applications")
                    .document(
                            currentOffer.getApplicationId()
                    )
                    .update(
                            "applicationStatus", "Offer Rejected",
                            "currentStage", "Closed",
                            "offerStatus", "Rejected"
                    );
        }
    }

    private void createNotification(String status) {

        String notificationId =
                FirebaseManager.getFirestore()
                        .collection("notifications")
                        .document()
                        .getId();

        Notification notification =
                new Notification(
                        notificationId,
                        currentOffer.getHrId(),
                        "Offer Update",
                        "Candidate has "
                                + status.toLowerCase()
                                + " the offer for "
                                + currentOffer.getJobTitle(),
                        "offer",
                        false,
                        String.valueOf(
                                System.currentTimeMillis()
                        )
                );

        FirebaseManager.getFirestore()
                .collection("notifications")
                .document(notificationId)
                .set(notification);
    }
}