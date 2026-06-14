package com.example.hirematch.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etEmail;
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.etEmail);
        btnReset = findViewById(R.id.btnReset);

        btnReset.setOnClickListener(v -> resetPassword());
    }

    private void resetPassword() {

        String email =
                etEmail.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {

            Toast.makeText(
                    this,
                    "Enter Email",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        FirebaseManager.getAuth()
                .sendPasswordResetEmail(email)
                .addOnSuccessListener(unused ->

                        Toast.makeText(
                                this,
                                "Reset Link Sent",
                                Toast.LENGTH_LONG
                        ).show()

                );
    }
}