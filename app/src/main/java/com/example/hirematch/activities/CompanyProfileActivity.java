package com.example.hirematch.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Company;

public class CompanyProfileActivity extends AppCompatActivity {


    private EditText etCompanyName;
    private EditText etWebsite;
    private EditText etLocation;
    private EditText etDescription;

    private Button btnSaveCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);

        initViews();

        btnSaveCompany.setOnClickListener(v -> saveCompany());

        loadCompany();
    }

    private void initViews() {

        etCompanyName = findViewById(R.id.etCompanyName);
        etWebsite = findViewById(R.id.etWebsite);
        etLocation = findViewById(R.id.etLocation);
        etDescription = findViewById(R.id.etDescription);

        btnSaveCompany = findViewById(R.id.btnSaveCompany);
    }

    private void saveCompany() {

        String hrId =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        Company company =
                new Company(
                        hrId,
                        etCompanyName.getText().toString().trim(),
                        etWebsite.getText().toString().trim(),
                        etDescription.getText().toString().trim(),
                        etLocation.getText().toString().trim()
                );

        FirebaseManager.getFirestore()
                .collection("companies")
                .document(hrId)
                .set(company)
                .addOnSuccessListener(unused ->

                        Toast.makeText(
                                this,
                                "Company Profile Saved",
                                Toast.LENGTH_SHORT
                        ).show()

                );
    }

    private void loadCompany() {

        String hrId =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        FirebaseManager.getFirestore()
                .collection("companies")
                .document(hrId)
                .get()
                .addOnSuccessListener(document -> {

                    if(document.exists()) {

                        Company company =
                                document.toObject(
                                        Company.class
                                );

                        if(company != null) {

                            etCompanyName.setText(
                                    company.getCompanyName()
                            );

                            etWebsite.setText(
                                    company.getWebsite()
                            );

                            etLocation.setText(
                                    company.getLocation()
                            );

                            etDescription.setText(
                                    company.getDescription()
                            );
                        }
                    }
                });
    }


}
