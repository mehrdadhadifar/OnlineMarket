package com.hfad.onlinemarket.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.onlinemarket.data.model.product.Category;
import com.hfad.onlinemarket.data.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

public class CategoriesViewModel extends AndroidViewModel {
    private static final String TAG = "Categories View Model";
    private CategoryRepository mRepository;

    public CategoriesViewModel(@NonNull Application application) {
        super(application);
        mRepository = CategoryRepository.getInstance();
    }


    public void setAllCategories() {
        mRepository.setAllCategoriesItems();
    }

    public LiveData<List<Category>> getAllCategories() {
        return mRepository.getCategoriesItems();
    }

    public LiveData<List<Category>> getDefaultCategories() {
        return mRepository.getDefaultCategories();
    }

    public LiveData<List<Category>> getSubCategories() {
        return mRepository.getSubCategories();
    }

}
