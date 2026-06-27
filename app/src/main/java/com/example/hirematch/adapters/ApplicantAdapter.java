package com.example.hirematch.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hirematch.R;
import com.example.hirematch.activities.CandidateViewActivity;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ApplicantAdapter
        extends RecyclerView.Adapter<ApplicantAdapter.ApplicantViewHolder> {

    private Context context;
    private List<Application> applicantList;

    public ApplicantAdapter(
            Context context,
            List<Application> applicantList
    ) {
        this.context = context;
        this.applicantList = applicantList;
    }

    @NonNull
    @Override
    public ApplicantViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.item_applicant,
                                parent,
                                false
                        );

        return new ApplicantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ApplicantViewHolder holder,
            int position
    ) {

        Application application =
                applicantList.get(position);

        holder.tvCandidateName.setText(
                application.getCandidateName()
        );

        holder.tvCandidateEmail.setText(
                application.getCandidateEmail()
        );

        holder.tvJobTitle.setText(
                application.getJobTitle()
        );

        holder.tvATSScore.setText(
                application.getAtsScore() + "%"
        );

        holder.tvProfileScore.setText(
                application.getProfileScore() + "%"
        );

        holder.tvStage.setText(
                application.getCurrentStage()
        );

        String formattedDate =
                new SimpleDateFormat(
                        "dd MMM yyyy",
                        Locale.getDefault()
                ).format(
                        new Date(
                                application.getAppliedAt()
                        )
                );

        holder.tvAppliedAt.setText(
                formattedDate
        );

        String[] statuses = {
                "Pending",
                "Shortlisted",
                "On Hold",
                "Rejected"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        context,
                        android.R.layout.simple_spinner_item,
                        statuses
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        holder.spStatus.setAdapter(adapter);

        holder.btnViewProfile.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            context,
                            CandidateViewActivity.class
                    );

            intent.putExtra(
                    "candidateId",
                    application.getCandidateId()
            );

            context.startActivity(intent);
        });

        holder.btnSaveStatus.setOnClickListener(v -> {

            String selectedStatus =
                    holder.spStatus.getSelectedItem()
                            .toString();

            String stage = "Review";

            if (selectedStatus.equals("Shortlisted")) {
                stage = "Interview";
            } else if (selectedStatus.equals("Rejected")) {
                stage = "Closed";
            }

            FirebaseManager.getFirestore()
                    .collection("applications")
                    .document(application.getApplicationId())
                    .update(
                            "applicationStatus",
                            selectedStatus,
                            "currentStage",
                            stage
                    )
                    .addOnSuccessListener(unused -> {

                        Toast.makeText(
                                context,
                                "Status Updated",
                                Toast.LENGTH_SHORT
                        ).show();
                    });
        });
    }

    @Override
    public int getItemCount() {
        return applicantList.size();
    }

    static class ApplicantViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvCandidateName;
        TextView tvCandidateEmail;
        TextView tvJobTitle;
        TextView tvATSScore;
        TextView tvProfileScore;
        TextView tvStage;
        TextView tvAppliedAt;

        Spinner spStatus;

        Button btnViewProfile;
        Button btnSaveStatus;

        public ApplicantViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);

            tvCandidateName =
                    itemView.findViewById(R.id.tvCandidateName);

            tvCandidateEmail =
                    itemView.findViewById(R.id.tvCandidateEmail);

            tvJobTitle =
                    itemView.findViewById(R.id.tvJobTitle);

            tvATSScore =
                    itemView.findViewById(R.id.tvATSScore);

            tvProfileScore =
                    itemView.findViewById(R.id.tvProfileScore);

            tvStage =
                    itemView.findViewById(R.id.tvStage);

            tvAppliedAt =
                    itemView.findViewById(R.id.tvAppliedAt);

            spStatus =
                    itemView.findViewById(R.id.spStatus);

            btnViewProfile =
                    itemView.findViewById(R.id.btnViewProfile);

            btnSaveStatus =
                    itemView.findViewById(R.id.btnSaveStatus);
        }
    }
}