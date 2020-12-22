package com.hfad.onlinemarket.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.onlinemarket.data.model.product.Category;
import com.hfad.onlinemarket.data.repository.CategoryRepository;

import java.util.List;

public class CategoriesViewModel extends ViewModel {
    private CategoryRepository mRepository;

    public CategoriesViewModel() {
        mRepository=CategoryRepository.getInstance();
    }
    public void setAllCategories(){
        mRepository.setAllCategoriesItems();
    }
    public LiveData<List<Category>> getAllCategories(){
        return mRepository.getCategoriesItems();
    }
}
