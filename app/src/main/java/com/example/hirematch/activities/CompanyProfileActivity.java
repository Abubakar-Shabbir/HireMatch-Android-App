package com.example.hirematch.activities;
import com.example.hirematch.utils.LoadingManager;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Company;

import java.util.Calendar;

public class CompanyProfileActivity
        extends AppCompatActivity {

    private EditText etCompanyName, etWebsite, etLocation;
    private EditText etDescription, etIndustry, etCompanySize;
    private EditText etFoundedYear, etLinkedIn;
    private EditText etBenefits, etCulture, etHiringEmail;

    private Button btnSaveCompany;

    private TextView tvCompanyProgress;
    private ProgressBar progressCompany;

    private String hrId;
    private boolean isUpdateMode = false;
    private int companyScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        LoadingManager.show(this);
        hrId = FirebaseManager.getAuth()
                .getCurrentUser()
                .getUid();

        initViews();
        setupYearPicker();
        loadCompany();

        btnSaveCompany.setOnClickListener(v ->
                saveCompany()
        );
    }

    private void initViews() {

        etCompanyName = findViewById(R.id.etCompanyName);
        etWebsite = findViewById(R.id.etWebsite);
        etLocation = findViewById(R.id.etLocation);
        etDescription = findViewById(R.id.etDescription);

        etIndustry = findViewById(R.id.etIndustry);
        etCompanySize = findViewById(R.id.etCompanySize);
        etFoundedYear = findViewById(R.id.etFoundedYear);
        etLinkedIn = findViewById(R.id.etLinkedIn);

        etBenefits = findViewById(R.id.etBenefits);
        etCulture = findViewById(R.id.etCulture);
        etHiringEmail = findViewById(R.id.etHiringEmail);

        btnSaveCompany = findViewById(R.id.btnSaveCompany);

        tvCompanyProgress =
                findViewById(R.id.tvCompanyProgress);

        progressCompany =
                findViewById(R.id.progressCompany);
    }

    private void setupYearPicker() {

        etFoundedYear.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            int currentYear =
                    calendar.get(Calendar.YEAR);

            DatePickerDialog dialog =
                    new DatePickerDialog(
                            this,
                            (view, year, month, dayOfMonth) ->
                                    etFoundedYear.setText(
                                            String.valueOf(year)
                                    ),
                            currentYear,
                            0,
                            1
                    );

            dialog.show();
        });
    }

    private void saveCompany() {

        if (etCompanyName.getText().toString().trim().isEmpty()) {
            etCompanyName.setError("Required");
            return;
        }

        if (etWebsite.getText().toString().trim().isEmpty()) {
            etWebsite.setError("Required");
            return;
        }

        btnSaveCompany.setEnabled(false);
        btnSaveCompany.setText("Saving...");

        Company company =
                new Company(
                        hrId,
                        etCompanyName.getText().toString().trim(),
                        etWebsite.getText().toString().trim(),
                        etDescription.getText().toString().trim(),
                        etLocation.getText().toString().trim(),
                        etIndustry.getText().toString().trim(),
                        etCompanySize.getText().toString().trim(),
                        etFoundedYear.getText().toString().trim(),
                        etLinkedIn.getText().toString().trim(),
                        etBenefits.getText().toString().trim(),
                        etCulture.getText().toString().trim(),
                        etHiringEmail.getText().toString().trim(),
                        "",
                        String.valueOf(System.currentTimeMillis())
                );
        LoadingManager.show(this);

        FirebaseManager.getFirestore()
                .collection("companies")
                .document(hrId)
                .set(company)
                .addOnSuccessListener(unused -> {
                    LoadingManager.hide();
                    calculateCompanyScore(company);

                    btnSaveCompany.setEnabled(true);

                    if (isUpdateMode) {
                        btnSaveCompany.setText(
                                "Update Company"
                        );
                    } else {
                        btnSaveCompany.setText(
                                "Save Company"
                        );
                    }

                    Toast.makeText(
                            this,
                            "Company Profile Saved Successfully",
                            Toast.LENGTH_SHORT
                    ).show();
                })
                .addOnFailureListener(e -> {

                    btnSaveCompany.setEnabled(true);
                    btnSaveCompany.setText(
                            "Save Company"
                    );

                    Toast.makeText(
                            this,
                            e.getMessage(),
                            Toast.LENGTH_SHORT
                    ).show();
                });
    }

    private void loadCompany() {

        FirebaseManager.getFirestore()
                .collection("companies")
                .document(hrId)
                .get()
                .addOnSuccessListener(document -> {
                    LoadingManager.hide();
                    if (document.exists()) {

                        Company company =
                                document.toObject(
                                        Company.class
                                );

                        if (company != null) {

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

                            etIndustry.setText(
                                    company.getIndustry()
                            );

                            etCompanySize.setText(
                                    company.getCompanySize()
                            );

                            etFoundedYear.setText(
                                    company.getFoundedYear()
                            );

                            etLinkedIn.setText(
                                    company.getLinkedIn()
                            );

                            etBenefits.setText(
                                    company.getBenefits()
                            );

                            etCulture.setText(
                                    company.getCulture()
                            );

                            etHiringEmail.setText(
                                    company.getHiringEmail()
                            );

                            calculateCompanyScore(company);

                            isUpdateMode = true;

                            btnSaveCompany.setText(
                                    "Update Company"
                            );
                        }
                    }
                });
    }

    private void calculateCompanyScore(
            Company company) {

        companyScore = 0;

        if (!company.getCompanyName().isEmpty()) companyScore += 15;
        if (!company.getWebsite().isEmpty()) companyScore += 10;
        if (!company.getLocation().isEmpty()) companyScore += 10;
        if (!company.getDescription().isEmpty()) companyScore += 10;
        if (!company.getIndustry().isEmpty()) companyScore += 10;
        if (!company.getCompanySize().isEmpty()) companyScore += 10;
        if (!company.getFoundedYear().isEmpty()) companyScore += 10;
        if (!company.getLinkedIn().isEmpty()) companyScore += 10;
        if (!company.getBenefits().isEmpty()) companyScore += 5;
        if (!company.getCulture().isEmpty()) companyScore += 5;
        if (!company.getHiringEmail().isEmpty()) companyScore += 5;

        tvCompanyProgress.setText(
                companyScore + "% Completed"
        );

        progressCompany.setProgress(companyScore);
    }
}