package com.example.hirematch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hirematch.R;
import com.example.hirematch.models.Notification;

import java.util.List;

public class NotificationAdapter
        extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {


    private Context context;
    private List<Notification> notificationList;

    public NotificationAdapter(
            Context context,
            List<Notification> notificationList) {

        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.item_notification,
                                parent,
                                false
                        );

        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull NotificationViewHolder holder,
            int position) {

        Notification notification =
                notificationList.get(position);

        holder.tvTitle.setText(
                notification.getTitle()
        );

        holder.tvMessage.setText(
                notification.getMessage()
        );

        holder.tvTime.setText(
                notification.getCreatedAt()
        );
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    static class NotificationViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvMessage;
        TextView tvTime;

        public NotificationViewHolder(
                @NonNull View itemView) {
            super(itemView);

            tvTitle =
                    itemView.findViewById(
                            R.id.tvTitle
                    );

            tvMessage =
                    itemView.findViewById(
                            R.id.tvMessage
                    );

            tvTime =
                    itemView.findViewById(
                            R.id.tvTime
                    );
        }
    }


}
