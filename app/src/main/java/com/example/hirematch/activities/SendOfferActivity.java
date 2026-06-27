package com.example.hirematch.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.DatePickerDialog;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Notification;
import com.example.hirematch.models.Offer;
import com.google.firebase.firestore.DocumentSnapshot;

public class SendOfferActivity extends AppCompatActivity {

    private EditText etSalary;
    private EditText etJoiningDate;

    private Button btnSendOffer;

    private String applicationId;
    private String candidateId;
    private String candidateName;
    private String hrId;
    private String jobId;
    private String jobTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_offer);

        initViews();
        getIntentData();
        setupDatePicker();

        btnSendOffer.setOnClickListener(v -> sendOffer());
    }
    private void setupDatePicker() {

        etJoiningDate.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(
                            this,
                            (view, year, month, dayOfMonth) -> {

                                String date =
                                        dayOfMonth + "/" +
                                                (month + 1) + "/" +
                                                year;

                                etJoiningDate.setText(date);

                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                    );

            datePickerDialog.show();
        });
    }
    private void initViews() {

        etSalary = findViewById(R.id.etSalary);
        etJoiningDate = findViewById(R.id.etJoiningDate);
        btnSendOffer = findViewById(R.id.btnSendOffer);
    }

    private void getIntentData() {

        applicationId = getIntent().getStringExtra("applicationId");
        candidateId = getIntent().getStringExtra("candidateId");
        candidateName = getIntent().getStringExtra("candidateName");
        hrId = getIntent().getStringExtra("hrId");
        jobId = getIntent().getStringExtra("jobId");
        jobTitle = getIntent().getStringExtra("jobTitle");
    }

    private void sendOffer() {

        String salary = etSalary.getText().toString().trim();
        String joiningDate = etJoiningDate.getText().toString().trim();

        if (salary.isEmpty() || joiningDate.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        FirebaseManager.getFirestore()
                .collection("offers")
                .whereEqualTo("applicationId", applicationId)
                .get()
                .addOnSuccessListener(querySnapshot -> {

                    if (!querySnapshot.isEmpty()) {

                        DocumentSnapshot document =
                                querySnapshot.getDocuments().get(0);

                        document.getReference()
                                .update(
                                        "salary", salary,
                                        "joiningDate", joiningDate,
                                        "offerStatus", "Pending",
                                        "createdAt",
                                        String.valueOf(System.currentTimeMillis())
                                )
                                .addOnSuccessListener(unused -> {

                                    updateApplicationStatus();
                                    createNotification(salary);

                                    Toast.makeText(
                                            this,
                                            "Offer Updated Successfully",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    finish();
                                });

                    } else {

                        createNewOffer(salary, joiningDate);
                    }

                });
    }

    private void createNewOffer(
            @NonNull String salary,
            @NonNull String joiningDate
    ) {

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
                        candidateName,
                        hrId,
                        jobId,
                        jobTitle,
                        salary,
                        joiningDate,
                        "",
                        "Pending",
                        String.valueOf(System.currentTimeMillis())
                );

        FirebaseManager.getFirestore()
                .collection("offers")
                .document(offerId)
                .set(offer)
                .addOnSuccessListener(unused -> {

                    updateApplicationStatus();
                    createNotification(salary);

                    Toast.makeText(
                            this,
                            "Offer Sent Successfully",
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();
                });
    }

    private void updateApplicationStatus() {

        FirebaseManager.getFirestore()
                .collection("applications")
                .document(applicationId)
                .update(
                        "applicationStatus", "Offer Sent",
                        "currentStage", "Offer",
                        "offerStatus", "Pending"
                );
    }

    private void createNotification(String salary) {

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
                        "Congratulations!\n\nYou have received an offer for "
                                + jobTitle
                                + "\n\nSalary: "
                                + salary
                                + "\nJoining Date: "
                                + etJoiningDate.getText().toString().trim(),
                        "offer",
                        false,
                        String.valueOf(System.currentTimeMillis())
                );

        FirebaseManager.getFirestore()
                .collection("notifications")
                .document(notificationId)
                .set(notification);
    }
}