package com.hfad.onlinemarket.viewmodel;

import android.app.Application;
import android.location.Address;
import android.location.Geocoder;
import android.text.Editable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.hfad.onlinemarket.data.repository.AddressRepository;
import com.hfad.onlinemarket.data.room.entities.MyAddress;
import com.hfad.onlinemarket.view.fragment.AddAddressMapsFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddAddressMapViewModel extends AndroidViewModel {
    private AddressRepository mAddressRepository;
    private MyAddress mMyAddress;
    private String mAddressName;
    private String mBillingAddress;
    private Double mLatitude;
    private Double mLongitude;

    public AddAddressMapViewModel(@NonNull Application application) {
        super(application);
        mAddressRepository = AddressRepository.getInstance(getApplication());
    }

    public AddressRepository getAddressRepository() {
        return mAddressRepository;
    }

    public void setAddressRepository(AddressRepository addressRepository) {
        mAddressRepository = addressRepository;
    }

    public String getAddressName() {
        return mAddressName;
    }

    public void setAddressName(String addressName) {
        mAddressName = addressName;
    }

    public String getBillingAddress() {
        return mBillingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        mBillingAddress = billingAddress;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Double latitude) {
        mLatitude = latitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Double longitude) {
        mLongitude = longitude;
    }

    public boolean isPointSelected() {
        if (mLatitude != null && mLongitude != null)
            return true;
        return false;
    }

    public void setFullAddress() {
        if (mLatitude != null && mLongitude != null) {
            Geocoder geocoder = new Geocoder(getApplication(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(mLatitude, mLongitude, 1);
/*                mBillingAddress = addresses.get(0).getCountryName() + "," +
                        addresses.get(0).getAdminArea() + "," +
                        addresses.get(0).getLocality() + "," +
                        addresses.get(0).getAddressLine(0);*/
                mBillingAddress = addresses.get(0).getAddressLine(0);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean setAddress(String addressName, String billingAddress) {
        mMyAddress = new MyAddress(addressName, billingAddress, mLatitude, mLongitude, false);
        mAddressRepository.insertAddress(mMyAddress);
        return true;
    }
}
