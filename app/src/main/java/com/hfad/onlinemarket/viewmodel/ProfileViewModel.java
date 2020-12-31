package com.hfad.onlinemarket.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hfad.onlinemarket.data.model.customer.Customer;
import com.hfad.onlinemarket.data.repository.CustomerRepository;
import com.hfad.onlinemarket.utils.QueryPreferences;

public class ProfileViewModel extends AndroidViewModel {
    private CustomerRepository mRepository;
    LiveData<Customer> mCustomerLiveData;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        mRepository = CustomerRepository.getCustomer();
        mCustomerLiveData = new MutableLiveData<>();
        mCustomerLiveData = mRepository.getCustomerLiveData();
    }


    public LiveData<Customer> getCustomerLiveData() {
        return mCustomerLiveData;
    }

    public void setCustomerLiveData(String email) {
        mRepository.setCustomerLiveData(email);
    }

    public boolean isCustomerReady() {
        return mCustomerLiveData.getValue() == null ? false : true;
    }

    public void signOut() {
        mRepository.signOut();
        QueryPreferences.setCustomerEmail(getApplication(), null);
    }

}
