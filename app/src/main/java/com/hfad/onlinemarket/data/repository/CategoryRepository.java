package com.hfad.onlinemarket.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.hfad.onlinemarket.data.model.product.Category;
import com.hfad.onlinemarket.data.remote.retrofit.RetrofitInstance;
import com.hfad.onlinemarket.data.remote.retrofit.WooCommerceAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    public static final String TAG = "Category Repository";
    private static CategoryRepository sInstance;
    private WooCommerceAPI mWooCommerceAPI;

    public static CategoryRepository getInstance() {
        if (sInstance == null)
            sInstance = new CategoryRepository();
        return sInstance;
    }

    public CategoryRepository() {
        mWooCommerceAPI = RetrofitInstance.getInstance().create(WooCommerceAPI.class);
    }


    public Call<List<Category>> setAllCategoriesItems() {
        Log.d(TAG, "setAllCategoriesItems: ");
        return mWooCommerceAPI.getCategories(new HashMap<>());
    }

}
