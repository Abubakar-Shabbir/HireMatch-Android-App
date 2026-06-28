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
import com.example.hirematch.activities.OfferDetailsActivity;
import com.example.hirematch.firebase.FirebaseManager;
import com.example.hirematch.models.Notification;
import com.example.hirematch.models.Offer;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {

    private final Context context;
    private final List<Offer> offerList;

    public OfferAdapter(Context context, List<Offer> offerList) {

        this.context = context;
        this.offerList = offerList;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_offer,
                        parent,
                        false
                );

        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull OfferViewHolder holder,
            int position) {

        Offer offer = offerList.get(position);

        holder.tvJobTitle.setText(
                offer.getJobTitle()
        );

        holder.tvSalary.setText(
                "Salary : " +
                        offer.getSalary()
        );

        holder.tvJoiningDate.setText(
                "Joining : " +
                        offer.getJoiningDate()
        );

        holder.tvOfferLetter.setText(
                "View Offer Letter"
        );

        holder.tvStatus.setText(
                offer.getOfferStatus()
        );

        // Status Badge

        if ("Accepted".equalsIgnoreCase(offer.getOfferStatus())) {

            holder.tvStatus.setBackgroundResource(
                    R.drawable.status_green
            );

            holder.btnAccept.setVisibility(View.GONE);
            holder.btnReject.setVisibility(View.GONE);

        }

        else if ("Rejected".equalsIgnoreCase(offer.getOfferStatus())) {

            holder.tvStatus.setBackgroundResource(
                    R.drawable.status_red
            );

            holder.btnAccept.setVisibility(View.GONE);
            holder.btnReject.setVisibility(View.GONE);

        }

        else {

            holder.tvStatus.setBackgroundResource(
                    R.drawable.status_blue
            );

            holder.btnAccept.setVisibility(View.VISIBLE);
            holder.btnReject.setVisibility(View.VISIBLE);
        }

        // Offer Details

        holder.tvOfferLetter.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            context,
                            OfferDetailsActivity.class
                    );

            intent.putExtra(
                    "offerId",
                    offer.getOfferId()
            );

            context.startActivity(intent);

        });

        // Accept Offer

        holder.btnAccept.setOnClickListener(v -> {

            FirebaseManager.getFirestore()
                    .collection("offers")
                    .document(offer.getOfferId())
                    .update(
                            "offerStatus",
                            "Accepted"
                    );

            createNotification(
                    offer,
                    "accepted"
            );

            offer.setOfferStatus(
                    "Accepted"
            );

            notifyItemChanged(position);

        });

        // Reject Offer

        holder.btnReject.setOnClickListener(v -> {

            FirebaseManager.getFirestore()
                    .collection("offers")
                    .document(offer.getOfferId())
                    .update(
                            "offerStatus",
                            "Rejected"
                    );

            createNotification(
                    offer,
                    "rejected"
            );

            offer.setOfferStatus(
                    "Rejected"
            );

            notifyItemChanged(position);

        });

    }

    @Override
    public int getItemCount() {

        return offerList.size();
    }

    private void createNotification(
            Offer offer,
            String action) {

        String notificationId =
                FirebaseManager.getFirestore()
                        .collection("notifications")
                        .document()
                        .getId();

        Notification notification =
                new Notification(
                        notificationId,
                        offer.getHrId(),
                        "Offer Response",
                        offer.getCandidateName()
                                + " has "
                                + action
                                + " your offer for "
                                + offer.getJobTitle(),
                        "offer",
                        false,
                        String.valueOf(
                                System.currentTimeMillis()
                        )
                );

        FirebaseManager.getFirestore()
                .collection("notifications")
                .document(notificationId)
                .set(notification);

    }

    static class OfferViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvJobTitle;
        TextView tvSalary;
        TextView tvJoiningDate;
        TextView tvOfferLetter;
        TextView tvStatus;

        Button btnAccept;
        Button btnReject;

        public OfferViewHolder(
                @NonNull View itemView) {

            super(itemView);

            tvJobTitle =
                    itemView.findViewById(
                            R.id.tvJobTitle
                    );

            tvSalary =
                    itemView.findViewById(
                            R.id.tvSalary
                    );

            tvJoiningDate =
                    itemView.findViewById(
                            R.id.tvJoiningDate
                    );

            tvOfferLetter =
                    itemView.findViewById(
                            R.id.tvOfferLetter
                    );

            tvStatus =
                    itemView.findViewById(
                            R.id.tvStatus
                    );

            btnAccept =
                    itemView.findViewById(
                            R.id.btnAccept
                    );

            btnReject =
                    itemView.findViewById(
                            R.id.btnReject
                    );

        }
    }
}