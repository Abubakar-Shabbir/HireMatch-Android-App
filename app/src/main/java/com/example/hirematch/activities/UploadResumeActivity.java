package com.example.hirematch.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.firebase.FirebaseManager;

public class UploadResumeActivity extends AppCompatActivity {

    private TextView tvResumeName;
    private Button btnSelectResume;

    private final ActivityResultLauncher<String> filePicker =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {

                        if(uri != null) {

                            String fileName =
                                    getFileName(uri);

                            tvResumeName.setText(fileName);

                            saveResumeName(fileName);
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_resume);

        tvResumeName =
                findViewById(R.id.tvResumeName);

        btnSelectResume =
                findViewById(R.id.btnSelectResume);

        btnSelectResume.setOnClickListener(v ->

                filePicker.launch("application/pdf")

        );
    }

    private String getFileName(Uri uri) {

        String path = uri.getLastPathSegment();

        if(path == null)
            return "Resume.pdf";

        return path;
    }

    private void saveResumeName(String resumeName) {

        String uid =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        FirebaseManager.getFirestore()
                .collection("candidate_profiles")
                .document(uid)
                .update("resumeUrl", resumeName)
                .addOnSuccessListener(unused ->

                        Toast.makeText(
                                this,
                                "Resume Selected",
                                Toast.LENGTH_SHORT
                        ).show()

                );
    }
}