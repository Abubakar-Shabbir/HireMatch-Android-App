package com.example.hirematch.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;
import com.example.hirematch.utils.SharedPrefManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        SharedPrefManager pref =
                new SharedPrefManager(this);

        if(pref.isLoggedIn()) {

            String role = pref.getRole();

            if("candidate".equals(role)) {

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

        } else {

            startActivity(
                    new Intent(
                            this,
                            LoginActivity.class
                    )
            );
        }

        finish();
    }
}