package com.example.tracking;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App extends Application {
    // Handles background thread pool and running tasks on those threads
    private static ExecutorService executor;

    @Override
    public void onCreate() {
        super.onCreate();
        // One thread is sufficient for our basic needs in this app
        executor = Executors.newSingleThreadExecutor();
    }

    public static ExecutorService getExecutor() {
        return executor;
    }
}
