package com.hfad.onlinemarket.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.data.repository.ProductRepository;

public class ProductDetailsViewModel extends ViewModel {
    private ProductRepository mRepository;
    private LiveData<Product> mSelectedProduct;

    public ProductDetailsViewModel() {
        mRepository = ProductRepository.getInstance();
    }

    public void setSelectedProduct(int productId) {
        mRepository.setSelectedProductLiveData(productId);
        mSelectedProduct=mRepository.getSelectedProductLiveData();
    }

    public LiveData<Product> getSelectedProduct() {
        return mSelectedProduct;
    }

    public int getNumberOfImages(){
        return mSelectedProduct.getValue().getImages().size();
    }
}
