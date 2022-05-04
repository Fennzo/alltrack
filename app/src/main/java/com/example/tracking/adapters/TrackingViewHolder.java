package com.example.tracking.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tracking.R;
import com.example.tracking.controllers.TrackingDetailsActivity;
import com.example.tracking.models.Tracking;

class TrackingViewHolder extends RecyclerView.ViewHolder {
    private final View mRootView;
    private final TextView mTrackingNumLabel, mDeliveryStatusLabel, mMemoLable;

    public TrackingViewHolder(@NonNull View itemView) {
        super(itemView);
        mRootView = itemView.findViewById(R.id.trackingItem_linear_root);
        mTrackingNumLabel = itemView.findViewById(R.id.trackingItem_text_trackingNum);
        mDeliveryStatusLabel = itemView.findViewById(R.id.trackingItem_text_deliveryStatus);
        mMemoLable = itemView.findViewById(R.id.trackingItem_text_memo);
    }

    public void bind(Tracking item) {
        mTrackingNumLabel.setText(itemView.getContext().getString(R.string.trackingHolder_label_trackingNum, item.trackingNum));
        mDeliveryStatusLabel.setText(
                item.isActive ?
                        R.string.trackingHolder_lable_notDelivered :
                        R.string.trackingHolder_label_delivered
        );
        mMemoLable.setText(item.memo);
        mRootView.setOnClickListener(view -> {
            launchDetailsScreen(item.trackingNum, item.slug);
        });
    }

    private void launchDetailsScreen(String extraTrackingNum, String extraSlug) {
        Intent detailsLauncher = new Intent(itemView.getContext(), TrackingDetailsActivity.class);
        detailsLauncher.putExtra("tracking_number", extraTrackingNum);
        detailsLauncher.putExtra("slug", extraSlug);
        itemView.getContext().startActivity(detailsLauncher);
    }

    public static TrackingViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tracking, parent, false);
        return new TrackingViewHolder(view);
    }

}
