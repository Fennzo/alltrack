package com.example.tracking.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tracking.App;
import com.example.tracking.db.AppDatabase;
import com.example.tracking.models.Checkpoint;
import com.example.tracking.models.Event;
import com.example.tracking.models.Tracking;
import com.example.tracking.network.ApiService;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class AddTrackingViewModel extends ViewModel {
    private final AppDatabase mDatabase;
    private final ApiService mApiService;

    public final MutableLiveData<Event<Boolean>> mSavedEvent = new MutableLiveData<>();
    public final MutableLiveData<Event<String>> mErrorEvent = new MutableLiveData<>();

    public AddTrackingViewModel(AppDatabase database, ApiService apiService) {
        mDatabase = database;
        mApiService = apiService;
    }

    // handles logic of saving a new trackingNum and fetching related data
    public void onSaveTracking(String trackingNum, String memo) {
        boolean isFormValid = evaluateForm(trackingNum, memo);
        if (!isFormValid) {
            return;
        }

//         Cascade calls
//         1. save the new tracking info on the web server
//         2. Request a complete data info on tracking number
//         3. Save all those data in app's database using a background thread provided by executor
        mApiService.addTracking(trackingNum, memo, slug -> {
            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mApiService.getTracking(trackingNum, slug, tracking -> {
                App.getExecutor().submit(() -> {
                mDatabase.trackingDao().insert(tracking);
                mDatabase.checkpointDao().insertAll(tracking.checkpoints);
                mSavedEvent.postValue(new Event<>(true));
                });

            }, error -> {
            });
        }, error -> {
            switch (error) {
                case "400":
                    mErrorEvent.setValue(new Event<>("Already exists"));
                    break;
                case "429":
                    mErrorEvent.setValue(new Event<>("Too many requests"));
                    break;
            }
        });

    }

    // Decides if a form's data is enough for saving a new tracking number in storage
    private boolean evaluateForm(String trackingNum, String memo) {
        return !trackingNum.isEmpty();
    }
}
