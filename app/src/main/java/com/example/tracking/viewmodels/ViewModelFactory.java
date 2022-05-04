package com.example.tracking.viewmodels;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.tracking.db.AppDatabase;
import com.example.tracking.network.ApiService;

/**
 * Creates a new ViewModel subclass.
 * AppDatabase and ApiService are non-null when it's appropriate, i.e. the ViewModel is not
 * dependent on either of those.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final AppDatabase mDatabase;
    private final ApiService mApiService;

    public ViewModelFactory(AppDatabase database, ApiService apiService) {
        mDatabase = database;
        mApiService = apiService;
    }

    /**
     * A generic ViewModelFactory handling the creation of every ViewModel inside the app.
     * Throws an error if handling a subclass has not been defined
     * @param modelClass
     * @param <T> ViewModel
     * @return A ViewModel subclass instance
     */
    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddTrackingViewModel.class))  {
            return (T) new AddTrackingViewModel(mDatabase, mApiService);
        } else if (modelClass.isAssignableFrom(TrackingListViewModel.class)) {
            return (T) new TrackingListViewModel(mDatabase);
        } else if (modelClass.isAssignableFrom(TrackingDetailsViewModel.class)) {
            return (T) new TrackingDetailsViewModel(mDatabase, mApiService);
        }
        throw new IllegalStateException("Unknown ViewModel class: " + modelClass.getName());
    }

}
