package com.hfad.onlinemarket.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hfad.onlinemarket.data.room.entities.MyAddress;
import com.hfad.onlinemarket.data.room.entities.Cart;

import java.util.List;

import retrofit2.http.QueryMap;

@Dao
public interface AddressDAO {
    @Insert
    void insertAddress(MyAddress... myAddresses);

    @Update
    void updateAddress(MyAddress... myAddresses);

    @Delete
    void deleteAddress(MyAddress... myAddresses);

    @Query("select * from myAddress")
    LiveData<List<MyAddress>> getAddresses();

    @Query("select * from myaddress where myAddressId = :myAddressId")
    MyAddress getAddress(Integer myAddressId);

    @Query("delete from myaddress")
    void deleteAllAddresses();

}
