package com.hfad.onlinemarket.viewmodel;

import android.app.Application;
import android.os.Build;
import android.text.Html;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.data.repository.CartRepository;
import com.hfad.onlinemarket.data.repository.ProductRepository;
import com.hfad.onlinemarket.data.room.entities.Cart;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsViewModel extends AndroidViewModel {
    private ProductRepository mProductRepository;
    private CartRepository mCartRepository;
    private Product mSelectedProduct = new Product();
    private List<Cart> mCartsSubject = new ArrayList<>();
    private LiveData<List<Cart>> mCartsLiveData;

    public LiveData<List<Cart>> getCartsLiveData() {
        return mCartsLiveData;
    }

    public Product getSelectedProduct() {
        return mSelectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        mSelectedProduct = selectedProduct;
    }

    public List<Cart> getCartsSubject() {
        return mCartsSubject;
    }

    public void setCartsSubject(List<Cart> cartsSubject) {
        mCartsSubject = cartsSubject;
    }

    public ProductDetailsViewModel(@NonNull Application application) {
        super(application);
        mProductRepository = ProductRepository.getInstance();
        mCartRepository = CartRepository.getInstance(application);
        mCartsLiveData = fetchCartsLiveData();
    }

    private LiveData<List<Cart>> fetchCartsLiveData() {
        return mCartRepository.getCartLiveData();
    }


    public void setSelectedProductLiveData(int productId) {
        mProductRepository.setSelectedProductLiveData(productId);
    }

    public LiveData<Product> getSelectedProductLiveData() {
        return mProductRepository.getSelectedProductLiveData();
    }


    public int getNumberOfCarts() {
        return getCartsSubject() == null ? 0 : getCartsSubject().size();
    }

    public int getNumberOfImages() {
        return mSelectedProduct.getImages().size();
    }

    public String getFormattedDescription() {
        if (mSelectedProduct.getName() == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(mSelectedProduct.getDescription(), Html.FROM_HTML_MODE_COMPACT).toString();
        } else {
            return Html.fromHtml(mSelectedProduct.getDescription()).toString();
        }
    }

    public void addTooCart() {
        Cart cart = new Cart(mSelectedProduct.getId(), 1);
        if (mCartsSubject.contains(cart)) {
            for (Cart eachCart : mCartsSubject
            ) {
                if (eachCart.equals(cart)) {
                    int count = eachCart.getCount() + 1;
                    eachCart.setCount(count);
                    Log.d(CartRepository.TAG, "addTooCart: " + count);
                    mCartRepository.updateCart(eachCart);
                }
            }
        } else
            mCartRepository.insertCart(cart);
//        Log.d(CartRepository.TAG, "addTooCart: number of carts: " + mCartRepository.getCartLiveData().getValue().size());
//        Log.d(CartRepository.TAG, "addTooCart: number of carts: " + mCartRepository.getCartLiveData(mSelectedProduct.getValue().getId()).getValue().toString());
    }

    public boolean isLoading() {
        return mSelectedProduct.getId() == 0 ? true : false;
    }

}
