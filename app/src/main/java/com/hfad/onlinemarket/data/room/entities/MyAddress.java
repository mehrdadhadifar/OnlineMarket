package com.hfad.onlinemarket.data.room.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "myAddress")
public class MyAddress {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "myAddressId")
    private Integer id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "billingAddress")
    private String billingAddress;
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo(name = "longitude")
    private double longitude;
    @ColumnInfo(name = "selected")
    private boolean selected;

    public MyAddress() {
    }

    @Ignore
    public MyAddress(String name, String billingAddress, double latitude, double longitude, boolean selected) {
        this.name = name;
        this.billingAddress = billingAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.selected = selected;
    }


    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyAddress myAddress = (MyAddress) o;
        return id == myAddress.id;
    }

    public String showAddress() {
        return name + " : " + billingAddress;
    }
}
