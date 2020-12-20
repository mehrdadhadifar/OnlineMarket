package com.hfad.onlinemarket.viewmodel;

import android.os.Build;
import android.text.Html;

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
        mSelectedProduct = new MutableLiveData<>();
        mSelectedProduct = mRepository.getSelectedProductLiveData();
    }

    public void setSelectedProduct(int productId) {
        mRepository.setSelectedProductLiveData(productId);
    }

    public LiveData<Product> getSelectedProduct() {
        mSelectedProduct = mRepository.getSelectedProductLiveData();
        return mRepository.getSelectedProductLiveData();
    }


    public int getNumberOfImages() {
        return mSelectedProduct.getValue().getImages().size();
    }

    public String getFormattedDescription() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(mSelectedProduct.getValue().getDescription(), Html.FROM_HTML_MODE_COMPACT).toString();
        } else {
            return Html.fromHtml(mSelectedProduct.getValue().getDescription()).toString();
        }
    }
}
