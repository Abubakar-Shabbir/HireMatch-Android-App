package com.example.hirematch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hirematch.R;
import com.example.hirematch.models.Application;

import java.util.List;

public class ApplicationAdapter
        extends RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder> {

    private Context context;
    private List<Application> applicationList;

    public ApplicationAdapter(
            Context context,
            List<Application> applicationList) {

        this.context = context;
        this.applicationList = applicationList;
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.item_application,
                                parent,
                                false
                        );

        return new ApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ApplicationViewHolder holder,
            int position) {

        Application application =
                applicationList.get(position);

        holder.tvJobTitle.setText(
                application.getJobTitle()
        );

        holder.tvStatus.setText(
                "Status: " +
                        application.getApplicationStatus()
        );

        holder.tvATSScore.setText(
                "ATS Score: " +
                        application.getAtsScore() + "%"
        );
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    static class ApplicationViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvJobTitle;
        TextView tvStatus;
        TextView tvATSScore;

        public ApplicationViewHolder(
                @NonNull View itemView) {

            super(itemView);

            tvJobTitle =
                    itemView.findViewById(
                            R.id.tvJobTitle
                    );

            tvStatus =
                    itemView.findViewById(
                            R.id.tvStatus
                    );

            tvATSScore =
                    itemView.findViewById(
                            R.id.tvATSScore
                    );
        }
    }
}