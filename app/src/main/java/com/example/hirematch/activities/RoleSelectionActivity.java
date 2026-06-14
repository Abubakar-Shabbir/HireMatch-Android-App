package com.example.hirematch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;

public class RoleSelectionActivity extends AppCompatActivity {

    Button btnCandidate, btnHR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        btnCandidate = findViewById(R.id.btnCandidate);
        btnHR = findViewById(R.id.btnHR);

        btnCandidate.setOnClickListener(v -> saveRole("candidate"));

        btnHR.setOnClickListener(v -> saveRole("hr"));
    }

    private void saveRole(String role) {

        String uid = FirebaseManager
                .getAuth()
                .getCurrentUser()
                .getUid();

        FirebaseManager.getFirestore()
                .collection("users")
                .document(uid)
                .update("role", role)
                .addOnSuccessListener(unused -> {

                    Toast.makeText(
                            this,
                            "Role Selected Successfully",
                            Toast.LENGTH_SHORT
                    ).show();

                    if(role.equals("candidate")) {

                        startActivity(
                                new Intent(
                                        this,
                                        CandidateDashboardActivity.class
                                )
                        );

                    } else {

                        startActivity(
                                new Intent(
                                        this,
                                        HRDashboardActivity.class
                                )
                        );
                    }

                    finish();

                });
    }
}