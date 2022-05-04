package com.example.tracking.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tracking.db.AppDatabase;
import com.example.tracking.models.Checkpoint;
import com.example.tracking.models.Tracking;

import java.util.List;

public class TrackingListViewModel extends ViewModel {
    private AppDatabase mDatabase;
    public MutableLiveData<List<Tracking>> mTrackingsLiveData;
    private MutableLiveData<List<Checkpoint>> mCheckpointsLiveData = new MutableLiveData<>();

    public TrackingListViewModel(AppDatabase database) {
        mDatabase = database;
    }

    public void onScreenLoaded() {
        MediatorLiveData<List<Tracking>> mediatorLiveData = new MediatorLiveData<>();
        LiveData<List<Tracking>> source = mDatabase.trackingDao().observeTrackings();
        mediatorLiveData.addSource(source, trackings -> mTrackingsLiveData.setValue(trackings));
        mTrackingsLiveData = mediatorLiveData;
    }

}
