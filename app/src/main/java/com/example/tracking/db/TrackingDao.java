package com.example.tracking.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tracking.models.Tracking;

import java.util.List;

@Dao
public interface TrackingDao {

    @Query("SELECT * FROM trackings")
    LiveData<List<Tracking>> observeTrackings();

    // Replace the row's columns data if there's a conflict, i.e. tracking numbers are the same
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Tracking tracking);

    @Query("DELETE FROM trackings WHERE tracking_num = :trackingNum")
    void delete(String trackingNum);

}
