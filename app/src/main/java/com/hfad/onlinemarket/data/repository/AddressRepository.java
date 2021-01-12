package com.hfad.onlinemarket.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import com.hfad.onlinemarket.data.room.RoomDataBase;
import com.hfad.onlinemarket.data.room.dao.AddressDAO;
import com.hfad.onlinemarket.data.room.entities.MyAddress;

import java.util.List;

public class AddressRepository {
    public static final String TAG = "Address Repository";
    private static AddressRepository sInstance;
    private AddressDAO mAddressDAO;

    public static synchronized AddressRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new AddressRepository(context);
        return sInstance;
    }

    public AddressRepository(Context context) {
        RoomDataBase roomDataBase = RoomDataBase.getDataBase(context);
        mAddressDAO = roomDataBase.getAddressDAO();
    }

    public LiveData<List<MyAddress>> getAddresses() {
        return mAddressDAO.getAddresses();
    }

    public void insertAddress(MyAddress myAddress) {
        RoomDataBase.dataBaseWriteExecutor.execute(() -> mAddressDAO.insertAddress(myAddress));
    }

    public void updateAddress(MyAddress myAddress) {
        RoomDataBase.dataBaseWriteExecutor.execute(() -> mAddressDAO.updateAddress(myAddress));

    }

    public void deleteAddress(MyAddress myAddress) {
        RoomDataBase.dataBaseWriteExecutor.execute(() -> mAddressDAO.deleteAddress(myAddress));

    }

    public MyAddress getAddress(int addressId) {
        return mAddressDAO.getAddress(addressId);
    }

    public void deleteAllAddresses() {
        RoomDataBase.dataBaseWriteExecutor.execute(() -> mAddressDAO.deleteAllAddresses());

    }
}
