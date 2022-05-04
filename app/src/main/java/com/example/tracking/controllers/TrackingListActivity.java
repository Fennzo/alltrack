package com.example.tracking.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tracking.R;
import com.example.tracking.adapters.TrackingDiffUtil;
import com.example.tracking.adapters.TrackingsAdapter;
import com.example.tracking.db.AppDatabase;
import com.example.tracking.viewmodels.TrackingListViewModel;
import com.example.tracking.viewmodels.ViewModelFactory;


public class TrackingListActivity extends AppCompatActivity {
    private TrackingListViewModel mViewModel;
    private RecyclerView mTrackingList;
    private TrackingsAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_list);
        // Set the toolbar as ActionBar, so that toolbar use the apis of ActionBar's
        setSupportActionBar((Toolbar) findViewById(R.id.trackingList_toolbar));
        initViews();
        setupTrackingList();

        findViewById(R.id.trackingList_fab_addTracking).setOnClickListener(view -> {
            Intent addTrackingLauncher = new Intent(this, AddTrackingActivity.class);
            startActivity(addTrackingLauncher);
        });

        // Get an instance of TrackingListView model
        mViewModel = new ViewModelProvider(this, new ViewModelFactory(
                AppDatabase.getInstance(getApplicationContext()),
                null
        )).get(TrackingListViewModel.class);

        mViewModel.onScreenLoaded();

        mViewModel.mTrackingsLiveData.observe(this, trackings -> {
            if (trackings != null) {
                mAdapter.submitList(trackings);
            }
        });

    }

    /**
     * Inflates the toolbar's menu, i.e. actions that are part of ActionBar/Toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.options_tracking_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // TODO: search, filter
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        mTrackingList = findViewById(R.id.trackingList_recyclerView_trackings);
    }

    private void setupTrackingList() {
        mTrackingList.addItemDecoration(new DividerItemDecoration(this, LinearLayout.HORIZONTAL));
        mAdapter = new TrackingsAdapter(new TrackingDiffUtil());
        mTrackingList.setAdapter(mAdapter);
    }
}
