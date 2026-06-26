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
import com.example.hirematch.activities.ApplicantsActivity;
import com.example.hirematch.activities.EditJobActivity;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Job;

import java.util.List;

public class JobAdapter
        extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private Context context;
    private List<Job> jobList;

    public JobAdapter(
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
                LayoutInflater.from(
                        parent.getContext()
                ).inflate(
                        R.layout.item_job,
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

        holder.tvTitle.setText(
                job.getTitle()
        );

        holder.tvLocation.setText(
                "Location: " +
                        job.getLocation()
        );

        holder.tvSalary.setText(
                "Salary: " +
                        job.getSalary()
        );

        // Edit Job
        holder.btnEdit.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            context,
                            EditJobActivity.class
                    );

            intent.putExtra(
                    "jobId",
                    job.getJobId()
            );

            context.startActivity(intent);
        });

        // Delete Job
        holder.btnDelete.setOnClickListener(v -> {

            FirebaseManager.getFirestore()
                    .collection("jobs")
                    .document(
                            job.getJobId()
                    )
                    .delete()
                    .addOnSuccessListener(unused -> {

                        jobList.remove(position);

                        notifyItemRemoved(
                                position
                        );

                        notifyItemRangeChanged(
                                position,
                                jobList.size()
                        );
                    });
        });

        // View Applicants (click whole card)
        holder.itemView.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            context,
                            ApplicantsActivity.class
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

        Button btnEdit;
        Button btnDelete;

        public JobViewHolder(
                @NonNull View itemView) {

            super(itemView);

            tvTitle =
                    itemView.findViewById(
                            R.id.tvTitle
                    );

            tvLocation =
                    itemView.findViewById(
                            R.id.tvLocation
                    );

            tvSalary =
                    itemView.findViewById(
                            R.id.tvSalary
                    );

            btnEdit =
                    itemView.findViewById(
                            R.id.btnEdit
                    );

            btnDelete =
                    itemView.findViewById(
                            R.id.btnDelete
                    );
        }
    }


}
