package com.hfad.onlinemarket.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hfad.onlinemarket.data.repository.AddressRepository;
import com.hfad.onlinemarket.data.room.entities.MyAddress;

import java.util.List;

public class FinishShoppingViewModel extends AndroidViewModel {
    private final AddressRepository mAddressRepository;
    private MyAddress mLastAddressSelected;



    public FinishShoppingViewModel(@NonNull Application application) {
        super(application);
        mAddressRepository = AddressRepository.getInstance(getApplication());
    }

    public LiveData<List<MyAddress>> getAddresses() {
        return mAddressRepository.getAddresses();
    }

    public void setSelectedAddress(MyAddress address) {
        if (mLastAddressSelected != null) {
            mLastAddressSelected.setSelected(false);
            mAddressRepository.updateAddress(mLastAddressSelected);
        }
        address.setSelected(true);
        mLastAddressSelected = address;
        mAddressRepository.updateAddress(address);
    }

    public void deselectAllAddresses() {
        if (mLastAddressSelected != null) {
            mLastAddressSelected.setSelected(false);
            mAddressRepository.updateAddress(mLastAddressSelected);
        }
    }
}
