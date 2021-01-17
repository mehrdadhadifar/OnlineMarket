package com.hfad.onlinemarket.viewmodel;

import android.app.Application;
import android.os.Build;
import android.text.Html;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.data.repository.CartRepository;
import com.hfad.onlinemarket.data.repository.ProductRepository;
import com.hfad.onlinemarket.data.room.entities.Cart;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsViewModel extends AndroidViewModel {
    private ProductRepository mProductRepository;
    private CartRepository mCartRepository;
    private Product mSelectedProduct = new Product();
    private MutableLiveData<Product> mProductLiveData;
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
        mProductLiveData = new MutableLiveData<>();
    }

    private LiveData<List<Cart>> fetchCartsLiveData() {
        return mCartRepository.getCartLiveData();
    }


    public void setSelectedProductLiveData(int productId) {
        mProductRepository.setSelectedProductLiveData(productId)
                .enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        mProductLiveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {

                    }
                });
    }

    public LiveData<Product> getSelectedProductLiveData() {
        return mProductLiveData;
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
        mCartRepository.insertCart(cart);
//        Log.d(CartRepository.TAG, "addTooCart: number of carts: " + mCartRepository.getCartLiveData().getValue().size());
//        Log.d(CartRepository.TAG, "addTooCart: number of carts: " + mCartRepository.getCartLiveData(mSelectedProduct.getValue().getId()).getValue().toString());
    }

    public boolean isProductInCart() {
        Cart cart = new Cart(mSelectedProduct.getId(), 1);
        return mCartsSubject.contains(cart);
    }

    public String addToCartButtonTitle() {
        if (isProductInCart())
            return "در سبد خرید موجود است";
        return getApplication().getResources().getString(R.string.add_to_cart);
    }

    public boolean isLoading() {
        return mSelectedProduct.getId() == 0;
    }

    public String getProductRate() {
        return mSelectedProduct.getAverageRating();
    }
}
