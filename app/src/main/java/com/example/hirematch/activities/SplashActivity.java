package com.example.hirematch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hirematch.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView imgLogo;
    private ImageView imgBriefcase;
    private TextView tvAppName;
    private TextView tvTagline;
    private TextView tvLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initViews();
        startAnimations();

        new Handler().postDelayed(() -> {

            startActivity(
                    new Intent(
                            SplashActivity.this,
                            LoginActivity.class
                    )
            );

            finish();

        }, 3500);
    }

    private void initViews() {

        imgLogo =
                findViewById(R.id.imgLogo);

        imgBriefcase =
                findViewById(R.id.imgBriefcase);

        tvAppName =
                findViewById(R.id.tvAppName);

        tvTagline =
                findViewById(R.id.tvTagline);

        tvLoading =
                findViewById(R.id.tvLoading);
    }

    private void startAnimations() {

        Animation fadeIn =
                AnimationUtils.loadAnimation(
                        this,
                        R.anim.fade_in
                );

        Animation slideUp =
                AnimationUtils.loadAnimation(
                        this,
                        R.anim.slide_up
                );

        Animation scaleIn =
                AnimationUtils.loadAnimation(
                        this,
                        R.anim.scale_in
                );

        imgLogo.startAnimation(scaleIn);
        tvAppName.startAnimation(fadeIn);
        tvTagline.startAnimation(fadeIn);
        imgBriefcase.startAnimation(slideUp);
        tvLoading.startAnimation(slideUp);
    }
}