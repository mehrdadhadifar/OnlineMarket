package com.hfad.onlinemarket.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hfad.onlinemarket.data.model.order.Order;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.data.remote.retrofit.RetrofitInstance;
import com.hfad.onlinemarket.data.remote.retrofit.WooCommerceAPI;
import com.hfad.onlinemarket.data.room.CartRoomDataBase;
import com.hfad.onlinemarket.data.room.dao.CartDAO;
import com.hfad.onlinemarket.data.room.entities.Cart;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRepository {
    public static final String TAG = "Cart Repository";
    private static CartRepository sInstance;
    private CartDAO mCartDAO;
    private final MutableLiveData<List<Product>> mProductLiveData;
    private WooCommerceAPI mWooCommerceAPI;


    public static synchronized CartRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new CartRepository(context);
        return sInstance;
    }


    public CartRepository(Context context) {
        CartRoomDataBase cartRoomDataBase = CartRoomDataBase.getDataBase(context);
        mCartDAO = cartRoomDataBase.getCardDAO();
        mProductLiveData = new MutableLiveData<>();
        mWooCommerceAPI = RetrofitInstance.getInstance().create(WooCommerceAPI.class);
    }

    public Call<Product> setCartProducts(Integer productId) {
        return mWooCommerceAPI.getProductById(productId);
    }

    public LiveData<List<Cart>> getCartLiveData() {
        return mCartDAO.getCarts();
    }

    public Cart getCart(int productId) {
        return mCartDAO.getCart(productId);
    }

    public void updateCart(Cart cart) {
        CartRoomDataBase.dataBaseWriteExecutor.execute(() -> mCartDAO.updateCarts(cart));
    }

    public void deleteCart(Cart cart) {
        CartRoomDataBase.dataBaseWriteExecutor.execute(() -> mCartDAO.deleteCarts(cart));
    }

    public void insertCart(Cart cart) {
        CartRoomDataBase.dataBaseWriteExecutor.execute(() -> mCartDAO.insertCarts(cart));
    }

    public void deleteAllCarts() {
        CartRoomDataBase.dataBaseWriteExecutor.execute(() -> mCartDAO.deleteAllCarts());
    }


    public Call<Order> postOrder(Order order) {
        return mWooCommerceAPI.postOrder(order);
    }
}
