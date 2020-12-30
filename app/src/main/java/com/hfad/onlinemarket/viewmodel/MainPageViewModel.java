package com.hfad.onlinemarket.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.onlinemarket.data.model.Options;
import com.hfad.onlinemarket.data.model.product.ImagesItem;
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
        setOnSaleProducts();
    }

    private void setOnSaleProducts() {
        Options options = new Options();
        options.setOsSale(true);
        mRepository.setOnSaleProductsLiveData(options);
    }

    public LiveData<List<Product>> getOnSaleProducts() {
        return mRepository.getOnSaleProductsLiveData();
    }

    public void setSearchedProducts(String query) {
        Options options = new Options(query);
        mRepository.setProductByOptionsLiveData(options);
    }

    public LiveData<List<Product>> getSearchedProducts() {
        return mRepository.getProductByOptionsLiveData();
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


    public List<ImagesItem> getOnSaleImageItems(List<Product> products) {
        List<ImagesItem> result = new ArrayList<>();
        for (Product product : products
        ) {
            result.add(product.getFeatureImageItem());
        }
        return result;
    }
    public int getOnSaleProductSize(){
        return getOnSaleProducts().getValue().size();
    }
}
