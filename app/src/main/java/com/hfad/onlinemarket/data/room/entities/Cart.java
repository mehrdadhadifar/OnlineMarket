package com.hfad.onlinemarket.data.room.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "cart")
public class Cart {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "productid")
    private Integer mProductid;
    @ColumnInfo(name = "count")
    private int count;

    @NonNull
    public Integer getProductid() {
        return mProductid;
    }

    public void setProductid(@NonNull Integer productid) {
        mProductid = productid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Ignore
    public Cart(Integer productid, int count) {
        mProductid = productid;
        this.count = count;
    }

    @Ignore
    public Cart(Integer productid) {
        mProductid = productid;
    }

    public Cart() {
    }

    @Override
    public String toString() {
        return "Cart{" +
                "mProductId=" + mProductid +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return mProductid.equals(cart.mProductid);
    }
}
