package com.hfad.onlinemarket.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.data.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class MainPageViewModel extends ViewModel {
    private ProductRepository mRepository;


    public MainPageViewModel() {
        mRepository = ProductRepository.getInstance();
    }

    public LiveData<List<Product>> getLatestProducts() {
        return mRepository.getLatestProductsLiveData();
    }

    public void setLatestProducts() {
        mRepository.setLatestProductsLiveData();
    }

    public LiveData<List<Product>> getPopularProducts() {
        return mRepository.getPopularProductsLiveData();
    }

    public void setInitialData() {
        setLatestProducts();
        setPopularProducts();
        setTopRatedProducts();
    }

    private void setTopRatedProducts() {
        mRepository.setTopRatedProductsLiveData();
    }

    public LiveData<List<Product>> getTopRatedProducts() {
        return mRepository.getTopRatedProductsLiveData();
    }

    private void setPopularProducts() {
        mRepository.setPopularProductsLiveData();
    }
}
