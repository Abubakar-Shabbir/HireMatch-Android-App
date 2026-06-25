package com.example.hirematch.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hirematch.R;
import com.example.hirematch.activities.ApplicantDetailsActivity;
import com.example.hirematch.models.Application;

import java.util.List;

public class ApplicantAdapter
        extends RecyclerView.Adapter<ApplicantAdapter.ApplicantViewHolder> {


    private Context context;
    private List<Application> applicantList;

    public ApplicantAdapter(
            Context context,
            List<Application> applicantList) {

        this.context = context;
        this.applicantList = applicantList;
    }

    @NonNull
    @Override
    public ApplicantViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

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
            int position) {

        Application application =
                applicantList.get(position);

        holder.tvCandidateName.setText(
                application.getCandidateName()
        );

        holder.tvATSScore.setText(
                "ATS Score: " +
                        application.getAtsScore() + "%"
        );

        holder.tvStatus.setText(
                "Status: " +
                        application.getApplicationStatus()
        );

        holder.itemView.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            context,
                            ApplicantDetailsActivity.class
                    );

            intent.putExtra(
                    "applicationId",
                    application.getApplicationId()
            );

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return applicantList.size();
    }

    static class ApplicantViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvCandidateName;
        TextView tvATSScore;
        TextView tvStatus;

        public ApplicantViewHolder(
                @NonNull View itemView) {
            super(itemView);

            tvCandidateName =
                    itemView.findViewById(
                            R.id.tvCandidateName
                    );

            tvATSScore =
                    itemView.findViewById(
                            R.id.tvATSScore
                    );

            tvStatus =
                    itemView.findViewById(
                            R.id.tvStatus
                    );
        }
    }


}
