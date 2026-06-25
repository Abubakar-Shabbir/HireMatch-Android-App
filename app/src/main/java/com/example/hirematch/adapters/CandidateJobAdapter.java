package com.example.hirematch.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hirematch.R;
import com.example.hirematch.activities.JobDetailsActivity;
import com.example.hirematch.models.Job;

import java.util.List;

public class CandidateJobAdapter
        extends RecyclerView.Adapter<CandidateJobAdapter.JobViewHolder> {

    private Context context;
    private List<Job> jobList;

    public CandidateJobAdapter(
            Context context,
            List<Job> jobList) {

        this.context = context;
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.item_candidate_job,
                                parent,
                                false
                        );

        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull JobViewHolder holder,
            int position) {

        Job job = jobList.get(position);

        holder.tvTitle.setText(job.getTitle());

        holder.tvLocation.setText(
                "Location: " + job.getLocation()
        );

        holder.tvSalary.setText(
                "Salary: " + job.getSalary()
        );

        holder.btnViewDetails.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            context,
                            JobDetailsActivity.class
                    );

            intent.putExtra(
                    "jobId",
                    job.getJobId()
            );

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    static class JobViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvLocation;
        TextView tvSalary;
        Button btnViewDetails;

        public JobViewHolder(
                @NonNull View itemView) {

            super(itemView);

            tvTitle =
                    itemView.findViewById(R.id.tvTitle);

            tvLocation =
                    itemView.findViewById(R.id.tvLocation);

            tvSalary =
                    itemView.findViewById(R.id.tvSalary);

            btnViewDetails =
                    itemView.findViewById(
                            R.id.btnViewDetails
                    );
        }
    }
}