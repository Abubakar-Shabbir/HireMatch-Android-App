
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
import com.example.hirematch.activities.ScheduleInterviewActivity;
import com.example.hirematch.activities.SendOfferActivity;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Interview;

import java.util.List;

public class InterviewAdapter extends RecyclerView.Adapter<InterviewAdapter.InterviewViewHolder> {

    private final Context context;
    private final List<Interview> interviewList;

    public InterviewAdapter(Context context, List<Interview> interviewList) {
        this.context = context;
        this.interviewList = interviewList;
    }

    @NonNull
    @Override
    public InterviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_interview, parent, false);

        return new InterviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InterviewViewHolder holder, int position) {

        Interview interview = interviewList.get(position);

        holder.tvJobTitle.setText(
                interview.getJobTitle() == null ?
                        "No Job Title" :
                        interview.getJobTitle()
        );

        holder.tvCandidateName.setText(
                interview.getCandidateName() == null ?
                        "Unknown Candidate" :
                        interview.getCandidateName()
        );

        boolean scheduled =
                interview.getInterviewDate() != null &&
                        !interview.getInterviewDate().trim().isEmpty();

        if (scheduled) {

            holder.tvDate.setText(
                    "Date : " + interview.getInterviewDate()
            );

            holder.tvTime.setText(
                    "Time : " + interview.getInterviewTime()
            );

            holder.tvMode.setText(
                    "Mode : " + interview.getInterviewMode()
            );

            holder.tvStatus.setText("SCHEDULED");
            holder.tvStatus.setBackgroundResource(R.drawable.status_green);

            holder.btnManageInterview.setText("Reschedule Interview");

            holder.btnCompleteInterview.setVisibility(View.VISIBLE);
            holder.btnRejectInterview.setVisibility(View.VISIBLE);

        } else {

            holder.tvDate.setText("Date : Not Scheduled");
            holder.tvTime.setText("Time : Not Scheduled");
            holder.tvMode.setText("Mode : Not Scheduled");

            holder.tvStatus.setText("UNSCHEDULED");
            holder.tvStatus.setBackgroundResource(R.drawable.status_red);

            holder.btnManageInterview.setText("Schedule Interview");

            holder.btnCompleteInterview.setVisibility(View.GONE);
            holder.btnRejectInterview.setVisibility(View.GONE);
        }

        holder.btnManageInterview.setOnClickListener(v -> {

            Intent intent = new Intent(
                    context,
                    ScheduleInterviewActivity.class
            );

            intent.putExtra(
                    "applicationId",
                    interview.getApplicationId()
            );

            intent.putExtra(
                    "candidateId",
                    interview.getCandidateId()
            );

            intent.putExtra(
                    "candidateName",
                    interview.getCandidateName()
            );

            intent.putExtra(
                    "jobId",
                    interview.getJobId()
            );

            intent.putExtra(
                    "hrId",
                    interview.getHrId()
            );

            intent.putExtra(
                    "jobTitle",
                    interview.getJobTitle()
            );

            context.startActivity(intent);
        });

        holder.btnCompleteInterview.setOnClickListener(v -> {

            FirebaseManager.getFirestore()
                    .collection("applications")
                    .document(interview.getApplicationId())
                    .update(
                            "applicationStatus", "Interview Completed",
                            "currentStage", "Offer"
                    );

            Intent intent = new Intent(
                    context,
                    SendOfferActivity.class
            );

            intent.putExtra(
                    "applicationId",
                    interview.getApplicationId()
            );

            intent.putExtra(
                    "candidateId",
                    interview.getCandidateId()
            );

            intent.putExtra(
                    "hrId",
                    interview.getHrId()
            );

            intent.putExtra(
                    "jobTitle",
                    interview.getJobTitle()
            );

            context.startActivity(intent);
        });

        holder.btnRejectInterview.setOnClickListener(v ->

                FirebaseManager.getFirestore()
                        .collection("applications")
                        .document(interview.getApplicationId())
                        .update(
                                "applicationStatus", "Rejected",
                                "currentStage", "Closed"
                        )

        );
    }

    @Override
    public int getItemCount() {
        return interviewList.size();
    }

    static class InterviewViewHolder extends RecyclerView.ViewHolder {

        TextView tvJobTitle;
        TextView tvCandidateName;
        TextView tvDate;
        TextView tvTime;
        TextView tvMode;
        TextView tvStatus;

        Button btnManageInterview;
        Button btnCompleteInterview;
        Button btnRejectInterview;

        public InterviewViewHolder(@NonNull View itemView) {
            super(itemView);

            tvJobTitle = itemView.findViewById(R.id.tvJobTitle);
            tvCandidateName = itemView.findViewById(R.id.tvCandidateName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvMode = itemView.findViewById(R.id.tvMode);
            tvStatus = itemView.findViewById(R.id.tvStatus);

            btnManageInterview = itemView.findViewById(R.id.btnManageInterview);
            btnCompleteInterview = itemView.findViewById(R.id.btnCompleteInterview);
            btnRejectInterview = itemView.findViewById(R.id.btnRejectInterview);
        }
    }
}

