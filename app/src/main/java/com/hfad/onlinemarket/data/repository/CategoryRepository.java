package com.hfad.onlinemarket.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.hfad.onlinemarket.data.model.product.Category;
import com.hfad.onlinemarket.data.remote.NetworkParams;
import com.hfad.onlinemarket.data.remote.retrofit.RetrofitInstance;
import com.hfad.onlinemarket.data.remote.retrofit.WooCommerceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    public static final String TAG = "Category Repository";
    private static CategoryRepository sInstance;
    private WooCommerceAPI mWooCommerceAPI;


    private final MutableLiveData<List<Category>> mCategoriesItems;
    private final MutableLiveData<List<Category>> mDefaultCategories;
    private final MutableLiveData<List<Category>> mSubCategories;
    private final MutableLiveData<List<Category>> mSubCategoriesById;

    public static CategoryRepository getInstance() {
        if (sInstance == null)
            sInstance = new CategoryRepository();
        return sInstance;
    }

    public CategoryRepository() {
        mWooCommerceAPI = RetrofitInstance.getInstance().create(WooCommerceAPI.class);
        mCategoriesItems = new MutableLiveData<>();
        mDefaultCategories = new MutableLiveData<>();
        mSubCategories = new MutableLiveData<>();
        mSubCategoriesById = new MutableLiveData<>();
    }

    public MutableLiveData<List<Category>> getCategoriesItems() {
        return mCategoriesItems;
    }

    public MutableLiveData<List<Category>> getDefaultCategories() {
        return mDefaultCategories;
    }

    public MutableLiveData<List<Category>> getSubCategories() {
        return mSubCategories;
    }

    public MutableLiveData<List<Category>> getSubCategoriesById(int parentId) {
        setSubCategoriesById(parentId);
        return mSubCategoriesById;
    }

    private void setSubCategoriesById(int parentId) {
        Log.d(TAG, "setSubCategoriesById: " + parentId);
        mWooCommerceAPI.getCategories(NetworkParams.BASE_OPTIONS)
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        List<Category> subCategoriesById = new ArrayList<>();
                        for (int i = 0; i < response.body().size(); i++) {
                            if (response.body().get(i).getParent() == parentId)
                                subCategoriesById.add(response.body().get(i));
                        }
                        mSubCategoriesById.setValue(subCategoriesById);
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {

                    }
                });
    }

    public void setAllCategoriesItems() {
        Log.d(TAG, "setAllCategoriesItems: ");
        mWooCommerceAPI.getCategories(NetworkParams.BASE_OPTIONS)
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        mCategoriesItems.setValue(response.body());
                        List<Category> defaultCategories = new ArrayList<>();
                        List<Category> subCategories = new ArrayList<>();
                        for (int i = 0; i < response.body().size(); i++) {
                            if (response.body().get(i).getParent() == 0)
                                defaultCategories.add(response.body().get(i));
                            else
                                subCategories.add(response.body().get(i));
                        }
                        mSubCategories.setValue(subCategories);
                        mDefaultCategories.setValue(defaultCategories);
                        Log.d(TAG, "onResponse: " + mSubCategories.getValue().size());
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {

                    }
                });
    }

}
