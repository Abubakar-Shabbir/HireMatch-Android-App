package com.example.hirematch.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Notification;
import com.example.hirematch.models.Offer;

public class SendOfferActivity extends AppCompatActivity {

    private EditText etSalary;
    private EditText etJoiningDate;
    private Button btnSendOffer;

    private String applicationId;
    private String candidateId;
    private String hrId;
    private String jobTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_offer);

        initViews();

        applicationId =
                getIntent().getStringExtra(
                        "applicationId"
                );

        candidateId =
                getIntent().getStringExtra(
                        "candidateId"
                );

        hrId =
                getIntent().getStringExtra(
                        "hrId"
                );

        jobTitle =
                getIntent().getStringExtra(
                        "jobTitle"
                );

        btnSendOffer.setOnClickListener(v ->
                sendOffer()
        );
    }

    private void initViews() {

        etSalary =
                findViewById(R.id.etSalary);

        etJoiningDate =
                findViewById(R.id.etJoiningDate);

        btnSendOffer =
                findViewById(R.id.btnSendOffer);
    }

    private void sendOffer() {

        String salary =
                etSalary.getText()
                        .toString()
                        .trim();

        String joiningDate =
                etJoiningDate.getText()
                        .toString()
                        .trim();

        String offerId =
                FirebaseManager.getFirestore()
                        .collection("offers")
                        .document()
                        .getId();

        Offer offer =
                new Offer(
                        offerId,
                        applicationId,
                        candidateId,
                        hrId,
                        jobTitle,
                        salary,
                        joiningDate,
                        "Pending",
                        String.valueOf(
                                System.currentTimeMillis()
                        )
                );

        FirebaseManager.getFirestore()
                .collection("offers")
                .document(offerId)
                .set(offer)
                .addOnSuccessListener(unused -> {

                    createNotification();

                    Toast.makeText(
                            this,
                            "Offer Sent Successfully",
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();
                });
    }

    private void createNotification() {

        String notificationId =
                FirebaseManager.getFirestore()
                        .collection("notifications")
                        .document()
                        .getId();

        Notification notification =
                new Notification(
                        notificationId,
                        candidateId,
                        "Offer Letter",
                        "You received an offer for " +
                                jobTitle,
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