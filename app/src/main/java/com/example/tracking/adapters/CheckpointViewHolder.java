package com.example.tracking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tracking.R;
import com.example.tracking.models.Checkpoint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A ViewHolder object for each visible item inside RecyclerView
 * A ViewHolder controls the behaviour of item's View
 */
public class CheckpointViewHolder extends RecyclerView.ViewHolder {
    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN, Locale.ENGLISH);
    private final TextView mLocationLabel, mCheckPointTime;

    public static CheckpointViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkpoint, parent, false);
        return new CheckpointViewHolder(view);
    }

    public CheckpointViewHolder(@NonNull View itemView) {
        super(itemView);
        mLocationLabel = itemView.findViewById(R.id.checkpointItem_label_location);
        mCheckPointTime = itemView.findViewById(R.id.checkpointItem_label_checkPointTime);
    }

    public void bind(Checkpoint item) {
        mLocationLabel.setText("> " + item.location);
        if (item.checkpointTime != null) {
            mCheckPointTime.setText(item.checkpointTime);
        }
    }
}
