package com.example.tracking.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.tracking.models.Tracking;

/**
 * Evaluate new item's updating, i.e. an item's ViewHolder needs to be refreshed
 */
public class TrackingDiffUtil extends DiffUtil.ItemCallback<Tracking> {
    @Override
    public boolean areItemsTheSame(@NonNull Tracking oldItem, @NonNull Tracking newItem) {
        return newItem.trackingNum.equals(oldItem.trackingNum);
    }

    @Override
    public boolean areContentsTheSame(@NonNull Tracking oldItem, @NonNull Tracking newItem) {
        return newItem.equals(oldItem);
    }
}
