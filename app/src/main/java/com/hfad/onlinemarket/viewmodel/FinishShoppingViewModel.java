package com.hfad.onlinemarket.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hfad.onlinemarket.data.repository.AddressRepository;
import com.hfad.onlinemarket.data.room.entities.MyAddress;

import java.util.List;

public class FinishShoppingViewModel extends AndroidViewModel {
    private AddressRepository mAddressRepository;
    private List<MyAddress> mAddressesSubject;
    private MyAddress mLastAddressSelected;

    public List<MyAddress> getAddressesSubject() {
        return mAddressesSubject;
    }

    public void setAddressesSubject(List<MyAddress> addressesSubject) {
        mAddressesSubject = addressesSubject;
    }

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
