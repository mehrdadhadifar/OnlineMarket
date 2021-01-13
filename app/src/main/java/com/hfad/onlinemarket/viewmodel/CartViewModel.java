package com.hfad.onlinemarket.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hfad.onlinemarket.data.model.order.LineItemsItem;
import com.hfad.onlinemarket.data.model.order.Order;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.data.repository.CartRepository;
import com.hfad.onlinemarket.data.room.entities.Cart;
import com.hfad.onlinemarket.utils.PriceFormatter;
import com.hfad.onlinemarket.utils.QueryPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewModel extends AndroidViewModel {
    public static final String TAG = "Cart viewModel";
    private final CartRepository mCartRepository;
    private List<Product> mCartProducts = new ArrayList<>();
    private List<Cart> mCartsSubject = new ArrayList<>();
    private LiveData<List<Cart>> mCartsLiveData;
    private MutableLiveData<Product> mProductLiveData;


    public CartViewModel(@NonNull Application application) {
        super(application);
        mCartRepository = CartRepository.getInstance(application);
        mCartsLiveData = fetchCartsLiveData();
        mProductLiveData = new MutableLiveData<>();
    }


    public MutableLiveData<Product> getProductLiveData() {
        return mProductLiveData;
    }

    public List<Product> getCartProducts() {
        return mCartProducts;
    }

    public void setCartProducts(List<Product> cartProducts) {
        mCartProducts = cartProducts;
    }

    public void setProductsLiveData(List<Cart> carts) {
        for (int i = 0; i < carts.size(); i++) {
            mCartRepository.setCartProducts(carts.get(i).getProductid())
                    .enqueue(new Callback<Product>() {
                        @Override
                        public void onResponse(Call<Product> call, Response<Product> response) {
                            if (response.isSuccessful()) {
                                Log.d(TAG, "onResponse: product name fetched from cart " + response.body().getName());
                                mProductLiveData.setValue(response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<Product> call, Throwable t) {
                        }
                    });
        }
    }


    private LiveData<List<Cart>> fetchCartsLiveData() {
        return mCartRepository.getCartLiveData();
    }

    public List<Cart> getCartsSubject() {
        return mCartsSubject;
    }

    public LiveData<List<Cart>> getCartsLiveData() {
        return mCartsLiveData;
    }

    public void setCartsSubject(List<Cart> cartsSubject) {
        mCartsSubject = cartsSubject;
    }


    public void removeCartItem(Cart cart) {
        if (cart.getCount() > 1) {
            cart.setCount(cart.getCount() - 1);
            mCartRepository.updateCart(cart);
        } else {
            mCartRepository.deleteCart(cart);
            mCartProducts.remove(findProductFromProductsList(cart.getProductid()));
        }
    }

    public void addCartItem(Cart cart) {
        cart.setCount(cart.getCount() + 1);
        mCartRepository.updateCart(cart);
    }

    public String calculateItemPrice(Cart cart) {
        Product product = findProductFromProductsList(cart.getProductid());
        long price = 0;
        if (product != null) {
            price = product.getLongPrice() * cart.getCount();
            Log.d(CartRepository.TAG, "calculateItemPrice: price a product: " + product.getLongPrice());
        }
        return PriceFormatter.priceFormatter(String.valueOf(price)) + " تومان";
    }

    private Product findProductFromProductsList(Integer productid) {
        Log.d(TAG, "findProductFromProductsList: product id: " + productid);
        Log.d(TAG, "findProductFromProductsList: mcart Products Size : " + mCartProducts.size());
        for (Product product : mCartProducts
        ) {
            Log.d(TAG, "findProductFromProductsList: mcart product name: " + product.getName());
            if (product.getId() == productid)
                return product;
        }
        return null;
    }

    public String calculateTotalPrice() {
        long total = 0;
        if (mCartsSubject != null && mCartProducts != null && mCartProducts.size() == mCartsSubject.size() && mCartProducts.size() > 0) {
            for (int i = 0; i < mCartsSubject.size(); i++) {
                Log.d(TAG, "calculateTotalPrice: index " + i);
                Long price = findProductFromProductsList(mCartsSubject.get(i).getProductid()).getLongPrice() * mCartsSubject.get(i).getCount();
                total += price;
            }
        }
        return PriceFormatter.priceFormatter(String.valueOf(total)) + "تومان ";
    }


    public boolean postOrder() {
        final boolean[] result = new boolean[1];
        List<LineItemsItem> itemsList = new ArrayList<>();
        for (int i = 0; i < mCartsSubject.size(); i++) {
            LineItemsItem lineItemsItem = new LineItemsItem();
            lineItemsItem.setProductId(mCartsSubject.get(i).getProductid());
            lineItemsItem.setQuantity(mCartsSubject.get(i).getCount());
            itemsList.add(lineItemsItem);
        }
        Order order = new Order();
        order.setCustomerId(QueryPreferences.getCustomerId(getApplication()));
        order.setLineItems(itemsList);
        mCartRepository.postOrder(order).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Log.d(TAG, "onResponse: order" + response.isSuccessful());
                if (response.isSuccessful()) {
                    mCartRepository.deleteAllCarts();
                    result[0] = true;
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                result[0] = false;
            }
        });
        return result[0];
    }

    public String getCartProductsNumber() {
        return mCartsSubject.size() > 0 ? mCartsSubject.size() + " مورد" : "سبد خرید خالی است";
    }

    public boolean isCartProductsEmpty() {
        return mCartsSubject.size() <= 0;
    }

    public String getProductName(Cart cart) {
        return findProductFromProductsList(cart.getProductid()) == null ? null :
                findProductFromProductsList(cart.getProductid()).getName();
    }

    public boolean addProductToCardProductsList(Product product) {
        if (!mCartProducts.contains(product))
            mCartProducts.add(product);
        return mCartProducts.size() == mCartsSubject.size();
    }

    public String getProductFeatureImage(Cart cart) {
        return findProductFromProductsList(cart.getProductid()) == null ? null :
                findProductFromProductsList(cart.getProductid()).getFeaturedImageUrl();
    }

}
