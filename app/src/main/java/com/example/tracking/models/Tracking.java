package com.example.tracking.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

@Entity(tableName = "trackings")
public class Tracking {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public transient int id;

    @SerializedName("tracking_number")
    @ColumnInfo(name = "tracking_num")
    public String trackingNum;

    @SerializedName("slug")
    @ColumnInfo(name = "slug")
    public String slug;

    @SerializedName("title")
    @ColumnInfo(name = "memo")
    public String memo;

    @SerializedName("customer_name")
    @ColumnInfo(name = "customer_name")
    public String customerName;

    @SerializedName("destination_country")
    @ColumnInfo(name = "destination_country")
    public String destCountry;

    @SerializedName("origin_country")
    @ColumnInfo(name = "origin_country")
    public String originCountry;

    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    public String createdAt;

    @SerializedName("last_update")
    @ColumnInfo(name = "last_update")
    public String updatedAt;

    @SerializedName("active_status")
    @ColumnInfo(name = "active_status")
    public boolean isActive;

    @SerializedName("expected_delivery")
    @ColumnInfo(name = "expected_delivery")
    public String expectedDelivery;

    @SerializedName("shipment_type")
    @ColumnInfo(name = "shipment_type")
    public String shipmentType;

    @SerializedName("signed_by")
    @ColumnInfo(name = "signed_by")
    public String signedBy;

    @SerializedName("tag")
    @ColumnInfo(name = "tag")
    public String tag;

    @SerializedName("tracking_postal_code")
    @ColumnInfo(name = "tracking_postal_code")
    public String trackingPostalCode;

    @SerializedName("tracking_ship_date")
    @ColumnInfo(name = "tracking_ship_date")
    public String trackingShipDate;

    @SerializedName("checkpoints")
    @Ignore
    public List<Checkpoint> checkpoints;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tracking tracking = (Tracking) o;
        return trackingNum.equals(tracking.trackingNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingNum);
    }
}
