package com.hfad.onlinemarket.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hfad.onlinemarket.data.model.product.Category;
import com.hfad.onlinemarket.data.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesViewModel extends AndroidViewModel {
    private static final String TAG = "Categories View Model";
    private CategoryRepository mRepository;
    private final MutableLiveData<List<Category>> mCategoriesItems;
    private final MutableLiveData<List<Category>> mDefaultCategories;
    private final MutableLiveData<List<Category>> mSubCategories;

    public CategoriesViewModel(@NonNull Application application) {
        super(application);
        mRepository = CategoryRepository.getInstance();
        mCategoriesItems = new MutableLiveData<>();
        mDefaultCategories = new MutableLiveData<>();
        mSubCategories = new MutableLiveData<>();
    }


    public void setAllCategories() {
        mRepository.setAllCategoriesItems()
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        mCategoriesItems.setValue(response.body());
                        List<Category> defaultCategories = new ArrayList<>();
                        List<Category> subCategories = new ArrayList<>();
                        for (int i = 0; i < response.body().size(); i++) {
                            Log.d(TAG, "onResponse: " + response.body().get(i).toString());
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

    public LiveData<List<Category>> getAllCategories() {
        return mCategoriesItems;
    }

    public LiveData<List<Category>> getDefaultCategories() {
        return mDefaultCategories;
    }

    public LiveData<List<Category>> getSubCategories() {
        return mSubCategories;
    }

}
