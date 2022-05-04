package com.example.tracking.controllers;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tracking.R;
import com.example.tracking.adapters.CheckpointsAdapter;
import com.example.tracking.db.AppDatabase;
import com.example.tracking.network.ApiService;
import com.example.tracking.utils.EventObserver;
import com.example.tracking.viewmodels.TrackingDetailsViewModel;
import com.example.tracking.viewmodels.ViewModelFactory;

public class TrackingDetailsActivity extends AppCompatActivity {
    private TrackingDetailsViewModel mViewModel;
    private TextView mTrackingNumLabel;
    private CheckpointsAdapter mAdapter;
    private RecyclerView mCheckpointList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_details);
        String extraTrackingNum = getIntent().getExtras().getString("tracking_number");
        String extraSlug = getIntent().getExtras().getString("slug");
        setSupportActionBar(findViewById(R.id.trackingDetails_toolbar));
        initViews();
        setupCheckpoints();

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(
                AppDatabase.getInstance(getApplicationContext()),
                ApiService.getInstance(getApplicationContext())
        )).get(TrackingDetailsViewModel.class);

        if (extraTrackingNum != null && extraSlug != null) {
            mViewModel.mTrackingNum = extraTrackingNum;
            mViewModel.mSlug = extraSlug;
        } else {
            finish();
        }

        mViewModel.onScreenLoaded();

        // Observe LiveData for updates on its data, i.e. when a LiveData's setValue/postValue is called.
        mViewModel.mCheckpointsLiveData.observe(this, checkpoints -> {
            if (checkpoints != null) {
                mAdapter.submitList(checkpoints);
            }
        });

        mViewModel.mOnDeleteEvent.observe(this, new EventObserver<>(deleted -> {
            if (deleted) {
                finish();
            }
        }));

        mTrackingNumLabel.setText(extraTrackingNum);
    }

    private void initViews() {
        mTrackingNumLabel = findViewById(R.id.trackingDetails_label_trackingNum);
        mCheckpointList = findViewById(R.id.trackingDetails_recycler_checkpoints);
    }

    private void setupCheckpoints() {
        mAdapter = new CheckpointsAdapter();
        mCheckpointList.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        mCheckpointList.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_tracking_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.trackingDetailsOptions_item_deleteTracking) {
            mViewModel.onDeleteTracking();
        }
        return super.onOptionsItemSelected(item);
    }

    // Save data passed from previous Activity, so when activity is killed, we don't end up with
    // null data
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("slug", mViewModel.mSlug);
        outState.putString("tracking_num", mViewModel.mTrackingNum);
        super.onSaveInstanceState(outState);
    }

    // Restore slug and tracking number and assign them to ViewModel
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mViewModel.mSlug = savedInstanceState.getString("slug");
        mViewModel.mTrackingNum = savedInstanceState.getString("tracking_num");
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
