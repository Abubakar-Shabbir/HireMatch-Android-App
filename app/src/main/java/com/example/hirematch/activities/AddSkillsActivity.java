package com.example.hirematch.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;

public class AddSkillsActivity extends AppCompatActivity {

    private EditText etSkills;
    private Button btnSaveSkills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_skills);

        etSkills = findViewById(R.id.etSkills);
        btnSaveSkills = findViewById(R.id.btnSaveSkills);

        btnSaveSkills.setOnClickListener(v -> saveSkills());
    }

    private void saveSkills() {

        String skills =
                etSkills.getText().toString().trim();

        String uid =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        FirebaseManager
                .getFirestore()
                .collection("candidate_profiles")
                .document(uid)
                .update("skills", skills)
                .addOnSuccessListener(unused ->

                        Toast.makeText(
                                this,
                                "Skills Saved",
                                Toast.LENGTH_SHORT
                        ).show()

                );
    }
}