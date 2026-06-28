
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

        // Job Title
        holder.tvJobTitle.setText(
                interview.getJobTitle() == null ?
                        "No Job Title" :
                        interview.getJobTitle()
        );

        // Candidate Name
        holder.tvCandidateName.setText(
                interview.getCandidateName() == null ?
                        "Unknown Candidate" :
                        interview.getCandidateName()
        );
        holder.tvMeetingLink.setText(
                "Meeting Link : " +
                        (interview.getMeetingLink() == null ||
                                interview.getMeetingLink().isEmpty()
                                ? "N/A"
                                : interview.getMeetingLink())
        );

        holder.tvLocation.setText(
                "Location : " +
                        (interview.getLocation() == null ||
                                interview.getLocation().isEmpty()
                                ? "N/A"
                                : interview.getLocation())
        );


        // Check Interview Scheduled or Not
        boolean scheduled =
                interview.getInterviewDate() != null &&
                        !interview.getInterviewDate().trim().isEmpty();

        if (scheduled) {

            holder.tvDate.setText("Date : " + interview.getInterviewDate());
            holder.tvTime.setText("Time : " + interview.getInterviewTime());
            holder.tvMode.setText("Mode : " + interview.getInterviewMode());

            String status = interview.getInterviewStatus();

            if (status == null || status.isEmpty()) {
                status = "Scheduled";
            }

            holder.tvStatus.setText(status.toUpperCase());

            switch (status.toLowerCase()) {

                case "scheduled":
                    holder.tvStatus.setBackgroundResource(R.drawable.status_green);
                    break;

                case "rejected":
                    holder.tvStatus.setBackgroundResource(R.drawable.status_red);
                    break;

                case "completed":
                    holder.tvStatus.setBackgroundResource(R.drawable.status_blue);
                    break;

                case "hired":
                    holder.tvStatus.setBackgroundResource(R.drawable.status_green);
                    break;

                default:
                    holder.tvStatus.setBackgroundResource(R.drawable.status_gray);
                    break;
            }

            holder.btnManageInterview.setText("Reschedule");
        } else {

            holder.tvDate.setText("Date : Not Scheduled");
            holder.tvTime.setText("Time : Not Scheduled");
            holder.tvMode.setText("Mode : Not Scheduled");
            holder.tvMeetingLink.setText(
                    "Meeting Link : N/A"
            );

            holder.tvLocation.setText(
                    "Location : N/A"
            );

            holder.tvStatus.setText("UNSCHEDULED");
            holder.tvStatus.setBackgroundResource(R.drawable.status_red);

            holder.btnManageInterview.setText("Schedule");
        }

        // Hide buttons by default
        holder.btnManageInterview.setVisibility(View.GONE);
        holder.btnCompleteInterview.setVisibility(View.GONE);
        holder.btnRejectInterview.setVisibility(View.GONE);

        // Check Logged In User Role
        String currentUid =
                FirebaseManager.getAuth()
                        .getCurrentUser()
                        .getUid();

        FirebaseManager.getFirestore()
                .collection("users")
                .document(currentUid)
                .get()
                .addOnSuccessListener(document -> {

                    String role = document.getString("role");

                    if ("candidate".equals(role)) {

                        // Candidate sirf details dekhega

                        holder.btnManageInterview.setVisibility(View.GONE);
                        holder.btnCompleteInterview.setVisibility(View.GONE);
                        holder.btnRejectInterview.setVisibility(View.GONE);

                    } else {

                        // HR Controls

                        holder.btnManageInterview.setVisibility(View.VISIBLE);

                        if (scheduled) {

                            holder.btnCompleteInterview.setVisibility(View.VISIBLE);
                            holder.btnRejectInterview.setVisibility(View.VISIBLE);

                        } else {

                            holder.btnCompleteInterview.setVisibility(View.GONE);
                            holder.btnRejectInterview.setVisibility(View.GONE);
                        }
                    }
                });

        // Schedule / Reschedule Interview
        holder.btnManageInterview.setOnClickListener(v -> {

            Intent intent = new Intent(
                    context,
                    ScheduleInterviewActivity.class
            );

            intent.putExtra("applicationId", interview.getApplicationId());
            intent.putExtra("candidateId", interview.getCandidateId());
            intent.putExtra("candidateName", interview.getCandidateName());
            intent.putExtra("jobId", interview.getJobId());
            intent.putExtra("hrId", interview.getHrId());
            intent.putExtra("jobTitle", interview.getJobTitle());

            context.startActivity(intent);

        });

        // Complete Interview
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

            intent.putExtra("applicationId", interview.getApplicationId());
            intent.putExtra("candidateId", interview.getCandidateId());
            intent.putExtra("candidateName", interview.getCandidateName());
            intent.putExtra("jobId", interview.getJobId());
            intent.putExtra("hrId", interview.getHrId());
            intent.putExtra("jobTitle", interview.getJobTitle());

            context.startActivity(intent);

        });

        holder.btnRejectInterview.setOnClickListener(v -> {

            FirebaseManager.getFirestore()
                    .collection("applications")
                    .document(interview.getApplicationId())
                    .update(
                            "applicationStatus","Rejected",
                            "currentStage","Closed"
                    );

            FirebaseManager.getFirestore()
                    .collection("interviews")
                    .document(interview.getInterviewId())
                    .update(
                            "status","Rejected"
                    );

        });

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
        TextView tvMeetingLink;
        TextView tvLocation;

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
            tvMeetingLink = itemView.findViewById(
                            R.id.tvMeetingLink
                    );

            tvLocation = itemView.findViewById(
                            R.id.tvLocation
                    );
            btnManageInterview = itemView.findViewById(R.id.btnManageInterview);
            btnCompleteInterview = itemView.findViewById(R.id.btnCompleteInterview);
            btnRejectInterview = itemView.findViewById(R.id.btnRejectInterview);
        }
    }
}

