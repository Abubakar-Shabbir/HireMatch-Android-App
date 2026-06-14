package com.example.hirematch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.User;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etEmail,
            etPassword, etConfirmPassword;

    private Button btnRegister;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        btnRegister.setOnClickListener(v -> registerUser());

        tvLogin.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            RegisterActivity.this,
                            LoginActivity.class
                    )
            );

            finish();
        });
    }

    private void initViews() {

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword =
                findViewById(R.id.etConfirmPassword);

        btnRegister =
                findViewById(R.id.btnRegister);

        tvLogin =
                findViewById(R.id.tvLogin);
    }

    private void registerUser() {

        String name =
                etName.getText().toString().trim();

        String email =
                etEmail.getText().toString().trim();

        String password =
                etPassword.getText().toString().trim();

        String confirmPassword =
                etConfirmPassword.getText()
                        .toString()
                        .trim();

        if(TextUtils.isEmpty(name) ||
                TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(confirmPassword))
        {
            Toast.makeText(
                    this,
                    "Fill all fields",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if(!password.equals(confirmPassword))
        {
            Toast.makeText(
                    this,
                    "Passwords do not match",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        FirebaseManager.getAuth()
                .createUserWithEmailAndPassword(
                        email,
                        password
                )
                .addOnCompleteListener(task -> {

                    if(task.isSuccessful()) {

                        FirebaseUser firebaseUser =
                                FirebaseManager
                                        .getAuth()
                                        .getCurrentUser();

                        if(firebaseUser != null) {

                            String uid =
                                    firebaseUser.getUid();

                            User user =
                                    new User(
                                            uid,
                                            name,
                                            email,
                                            ""
                                    );

                            FirebaseManager
                                    .getFirestore()
                                    .collection("users")
                                    .document(uid)
                                    .set(user);

                            firebaseUser
                                    .sendEmailVerification();

                            Toast.makeText(
                                    this,
                                    "Verification Email Sent",
                                    Toast.LENGTH_LONG
                            ).show();

                            startActivity(
                                    new Intent(
                                            RegisterActivity.this,
                                            RoleSelectionActivity.class
                                    )
                            );

                            finish();
                        }

                    } else {

                        Toast.makeText(
                                this,
                                task.getException()
                                        .getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}