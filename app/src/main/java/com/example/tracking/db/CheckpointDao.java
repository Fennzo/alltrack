package com.example.tracking.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tracking.models.Checkpoint;

import java.util.List;

@Dao
public interface CheckpointDao {

    @Query("SELECT * FROM checkpoints WHERE tracking_num = :trackingNum")
    List<Checkpoint> getAllByTrackingNum(String trackingNum);

    @Insert
    void insertAll(List<Checkpoint> checkpoints);

    @Query("DELETE FROM checkpoints WHERE tracking_num = :trackingNum")
    void delete(String trackingNum);

}
