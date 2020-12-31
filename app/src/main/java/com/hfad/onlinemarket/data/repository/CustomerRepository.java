package com.hfad.onlinemarket.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hfad.onlinemarket.data.model.customer.Customer;
import com.hfad.onlinemarket.data.remote.NetworkParams;
import com.hfad.onlinemarket.data.remote.retrofit.RetrofitInstance;
import com.hfad.onlinemarket.data.remote.retrofit.WooCommerceAPI;
import com.hfad.onlinemarket.utils.QueryPreferences;
import com.hfad.onlinemarket.view.fragment.ProfileFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRepository {
    private static CustomerRepository sInstance;
    private final MutableLiveData<Customer> mCustomerLiveData;
    //    private final MutableLiveData<Boolean> mRegisterCustomerLiveData;
    private WooCommerceAPI mWooCommerceAPI;

    public static CustomerRepository getCustomer() {
        if (sInstance == null)
            sInstance = new CustomerRepository();
        return sInstance;
    }

    private CustomerRepository() {
        mWooCommerceAPI = RetrofitInstance.getInstance().create(WooCommerceAPI.class);
        mCustomerLiveData = new MutableLiveData<>();
//        mRegisterCustomerLiveData = new MutableLiveData<>();
    }

    public LiveData<Customer> getCustomerLiveData() {
        return mCustomerLiveData;
    }

/*    public MutableLiveData<Boolean> getRegisterCustomerLiveData() {
        return mRegisterCustomerLiveData;
    }*/

    public void setCustomerLiveData(String email) {
        Log.d(ProfileFragment.TAG, "onResponse: setCustomer " + email);
        mWooCommerceAPI.getCustomers(NetworkParams.getCustomer(email))
                .enqueue(new Callback<List<Customer>>() {
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
//                        Log.d(ProfileFragment.TAG, "onResponse: size " + response.body().size());
//                        Log.d(ProfileFragment.TAG, "onResponse: email " + response.body().get(0).getEmail());
                        if (response.body() != null && response.body().size() > 0)
                            mCustomerLiveData.setValue(response.body().get(0));
                        else
                            registerCustomer(email);
                    }

                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {

                    }
                });
    }

    public void registerCustomer(String email) {
        Customer customer = new Customer();
        customer.setEmail(email);
        mWooCommerceAPI.postCustomers(NetworkParams.BASE_OPTIONS, customer)
                .enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        Log.d(ProfileFragment.TAG, "onResponse: Register Customer " + response.isSuccessful());
                        Log.d(ProfileFragment.TAG, "onResponse: Register Customer " + response.message());
                        if (response.isSuccessful())
                            setCustomerLiveData(email);
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {

                    }
                });
    }

    public void signOut() {
        mCustomerLiveData.setValue(null);
    }


}
