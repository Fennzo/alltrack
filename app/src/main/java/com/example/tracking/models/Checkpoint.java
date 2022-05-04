package com.example.tracking.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(
        tableName = "checkpoints"
//        foreignKeys = {
//                @ForeignKey(
//                        entity = Tracking.class,
//                        parentColumns = "slug",
//                        childColumns = "slug",
//                        onDelete = ForeignKey.CASCADE
//                )
//        }
)
public class Checkpoint {

//    @SerializedName("tracking_num")
//    @ColumnInfo(name = "tracking_num")
//    @PrimaryKey
//    @NonNull
//    public String trackingNum;

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public transient int id;

    @SerializedName("slug")
    @ColumnInfo(name = "slug")
    public String slug;

//    @SerializedName("created_at")
//    @ColumnInfo(name = "created_at")
//    public String createdAt;
//
    @SerializedName("checkpoint_time")
    @ColumnInfo(name = "checkpoint_time")
    public String checkpointTime;
//
    @SerializedName("location")
    @ColumnInfo(name = "location")
    public String location;
//
//    @SerializedName("country")
//    @ColumnInfo(name = "country")
//    public String country;
//
//    @SerializedName("city")
//    @ColumnInfo(name = "city")
//    public String city;
//
//    @SerializedName("country_iso3")
//    @ColumnInfo(name = "country_iso3")
//    public String iso3;
//
//    @SerializedName("message")
//    @ColumnInfo(name = "message")
//    public String message;
//
//    @SerializedName("state")
//    @ColumnInfo(name = "state")
//    public String state;
//
//    @SerializedName("tag")
//    @ColumnInfo(name = "tag")
//    public String tag;
//
//    @SerializedName("postal_code")
//    @ColumnInfo(name = "postal_code")
//    public String postalCode;

    @ColumnInfo(name = "tracking_num")
    public transient String trackingNum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Checkpoint that = (Checkpoint) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
