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
import com.example.hirematch.activities.InterviewDetailsActivity;
import com.example.hirematch.models.Interview;

import java.util.List;

public class InterviewAdapter
        extends RecyclerView.Adapter<InterviewAdapter.InterviewViewHolder> {

    private Context context;
    private List<Interview> interviewList;

    public InterviewAdapter(
            Context context,
            List<Interview> interviewList) {

        this.context = context;
        this.interviewList = interviewList;
    }

    @NonNull
    @Override
    public InterviewViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view =
                LayoutInflater.from(
                        parent.getContext()
                ).inflate(
                        R.layout.item_interview,
                        parent,
                        false
                );

        return new InterviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull InterviewViewHolder holder,
            int position) {

        Interview interview =
                interviewList.get(position);

        holder.tvJobTitle.setText(
                interview.getJobTitle()
        );

        holder.tvDate.setText(
                "Date: " +
                        interview.getInterviewDate()
        );

        holder.tvTime.setText(
                "Time: " +
                        interview.getInterviewTime()
        );

        holder.tvStatus.setText(
                "Status: " +
                        interview.getInterviewStatus()
        );

        holder.itemView.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            context,
                            InterviewDetailsActivity.class
                    );

            intent.putExtra(
                    "interviewId",
                    interview.getInterviewId()
            );

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return interviewList.size();
    }

    static class InterviewViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvJobTitle;
        TextView tvDate;
        TextView tvTime;
        TextView tvStatus;

        public InterviewViewHolder(
                @NonNull View itemView) {

            super(itemView);

            tvJobTitle =
                    itemView.findViewById(
                            R.id.tvJobTitle
                    );

            tvDate =
                    itemView.findViewById(
                            R.id.tvDate
                    );

            tvTime =
                    itemView.findViewById(
                            R.id.tvTime
                    );

            tvStatus =
                    itemView.findViewById(
                            R.id.tvStatus
                    );
        }
    }
}