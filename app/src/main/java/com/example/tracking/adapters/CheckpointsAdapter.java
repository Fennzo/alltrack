package com.example.tracking.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tracking.models.Checkpoint;
import com.example.tracking.viewmodels.TrackingDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class CheckpointsAdapter extends RecyclerView.Adapter<CheckpointViewHolder> {
    private final List<Checkpoint> mCheckpoints = new ArrayList<>();


    @NonNull
    @Override
    public CheckpointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Calls a static factory method for creating a new ViewHolder
        return CheckpointViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckpointViewHolder holder, int position) {
        Checkpoint checkpoint = mCheckpoints.get(position);
        holder.bind(checkpoint);
    }

    @Override
    public int getItemCount() {
        return mCheckpoints.size();
    }

    /**
     * Send a new list to be replaced by the current one.
     * This needs to be called at least once when screen loads
     */
    public void submitList(List<Checkpoint> checkpoints) {
        // clear current
        mCheckpoints.clear();
        // add new ones
        mCheckpoints.addAll(checkpoints);
        // refresh recyclerview
        notifyDataSetChanged();
    }
}
