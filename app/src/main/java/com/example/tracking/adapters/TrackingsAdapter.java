package com.example.tracking.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.example.tracking.models.Tracking;

public class TrackingsAdapter extends ListAdapter<Tracking, TrackingViewHolder> {

    public TrackingsAdapter(TrackingDiffUtil diffUtil) {
        super(diffUtil);
    }

    @NonNull
    @Override
    public TrackingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TrackingViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackingViewHolder holder, int position) {
        Tracking tracking = getItem(position);
        holder.bind(tracking);
    }
}
