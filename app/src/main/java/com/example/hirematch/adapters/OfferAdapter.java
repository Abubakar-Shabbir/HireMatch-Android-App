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
import com.example.hirematch.activities.OfferDetailsActivity;
import com.example.hirematch.models.Offer;

import java.util.List;

public class OfferAdapter
        extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {

    private Context context;
    private List<Offer> offerList;

    public OfferAdapter(
            Context context,
            List<Offer> offerList) {

        this.context = context;
        this.offerList = offerList;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view =
                LayoutInflater.from(
                        parent.getContext()
                ).inflate(
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

        Offer offer =
                offerList.get(position);

        holder.tvJobTitle.setText(
                offer.getJobTitle()
        );

        holder.tvSalary.setText(
                "Salary: " +
                        offer.getSalary()
        );

        holder.tvStatus.setText(
                "Status: " +
                        offer.getOfferStatus()
        );

        holder.itemView.setOnClickListener(v -> {

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
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    static class OfferViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvJobTitle;
        TextView tvSalary;
        TextView tvStatus;

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

            tvStatus =
                    itemView.findViewById(
                            R.id.tvStatus
                    );
        }
    }
}