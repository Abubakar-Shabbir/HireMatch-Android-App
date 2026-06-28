package com.example.hirematch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.utils.SharedPrefManager;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {


    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvForgotPassword, tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Auto Login
        if (FirebaseManager.getAuth().getCurrentUser() != null) {

            String uid = FirebaseManager.getAuth()
                    .getCurrentUser()
                    .getUid();

            FirebaseManager.getFirestore()
                    .collection("users")
                    .document(uid)
                    .get()
                    .addOnSuccessListener(document -> {

                        if (document.exists()) {

                            String role = document.getString("role");

                            if ("candidate".equals(role)) {

                                startActivity(new Intent(
                                        LoginActivity.this,
                                        CandidateDashboardActivity.class));

                            } else if ("hr".equals(role)) {

                                startActivity(new Intent(
                                        LoginActivity.this,
                                        HRDashboardActivity.class));
                            }

                            finish();
                        }
                    });

            return;
        }

        // Views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);

        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvRegister = findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(v -> loginUser());

        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(
                        this,
                        RegisterActivity.class)));

        tvForgotPassword.setOnClickListener(v ->
                startActivity(new Intent(
                        this,
                        ForgotPasswordActivity.class)));
    }

    private void loginUser() {

        String email =
                etEmail.getText().toString().trim();

        String password =
                etPassword.getText().toString().trim();

        FirebaseManager.getAuth()
                .signInWithEmailAndPassword(
                        email,
                        password
                )
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        FirebaseUser user =
                                FirebaseManager
                                        .getAuth()
                                        .getCurrentUser();

                        if (user == null)
                            return;

                        if (!user.isEmailVerified()) {

                            Toast.makeText(
                                    this,
                                    "Verify your email first",
                                    Toast.LENGTH_LONG
                            ).show();

                            FirebaseManager
                                    .getAuth()
                                    .signOut();

                            return;
                        }

                        String uid =
                                user.getUid();

                        FirebaseManager
                                .getFirestore()
                                .collection("users")
                                .document(uid)
                                .get()
                                .addOnSuccessListener(document -> {

                                    String role =
                                            document.getString("role");

                                    SharedPrefManager pref =
                                            new SharedPrefManager(this);

                                    pref.saveUser(uid, role);

                                    // Save FCM Token
                                    FirebaseMessaging.getInstance()
                                            .getToken()
                                            .addOnSuccessListener(token -> {

                                                FirebaseManager
                                                        .getFirestore()
                                                        .collection("users")
                                                        .document(uid)
                                                        .update(
                                                                "fcmToken",
                                                                token
                                                        );
                                            });

                                    if ("candidate"
                                            .equals(role)) {

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

                    } else {

                        Toast.makeText(
                                this,
                                task.getException().getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }

}
