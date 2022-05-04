package com.example.tracking.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tracking.models.Checkpoint;
import com.example.tracking.models.Tracking;

/**
 * This classes is extended by Room's code generation.
 */
@Database(entities = { Tracking.class, Checkpoint.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TrackingDao trackingDao();
    public abstract CheckpointDao checkpointDao();

    private static AppDatabase INSTANCE = null;

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, NAME_DATABASE)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigrationOnDowngrade()
                    .build();
        }
        return INSTANCE;
    }

    private static final String NAME_DATABASE = "tracking.db";
}
