package com.example.tracking.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tracking.App;
import com.example.tracking.db.AppDatabase;
import com.example.tracking.models.Checkpoint;
import com.example.tracking.models.Event;
import com.example.tracking.network.ApiService;

import java.util.List;

public class TrackingDetailsViewModel extends ViewModel {
    private final AppDatabase mDatabase;
    private final ApiService mApiService;
    public MutableLiveData<List<Checkpoint>> mCheckpointsLiveData = new MutableLiveData<>();
    public String mTrackingNum;
    public String mSlug;
    public MutableLiveData<Event<Boolean>> mDeleteEventFailed = new MutableLiveData<>();

    public TrackingDetailsViewModel(AppDatabase database, ApiService apiService) {
        mDatabase = database;
        mApiService = apiService;
    }

    public MutableLiveData<Event<Boolean>> mOnDeleteEvent = new MutableLiveData<>();

    public void onScreenLoaded() {
        if (mSlug == null) return;
        App.getExecutor().submit(() -> {
            List<Checkpoint> checkpoints = mDatabase.checkpointDao().getAllByTrackingNum(mTrackingNum);
            mCheckpointsLiveData.postValue(checkpoints);
        });
    }

    public void onDeleteTracking() {
        if (mTrackingNum == null) return;
        mApiService.deleteTracking(mSlug, mTrackingNum, success -> {
            App.getExecutor().submit(() -> {
                mDatabase.trackingDao().delete(mTrackingNum);
                mDatabase.checkpointDao().delete(mTrackingNum);
                mOnDeleteEvent.postValue(new Event<>(true));
            });
        }, error -> {
            mDeleteEventFailed.postValue(new Event<>(false));
        });

    }
}
