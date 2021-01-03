package com.hfad.onlinemarket.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hfad.onlinemarket.data.model.customer.Customer;
import com.hfad.onlinemarket.data.model.order.Order;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.data.remote.NetworkParams;
import com.hfad.onlinemarket.data.remote.retrofit.RetrofitInstance;
import com.hfad.onlinemarket.data.remote.retrofit.WooCommerceAPI;
import com.hfad.onlinemarket.data.room.CartRoomDataBase;
import com.hfad.onlinemarket.data.room.dao.CartDAO;
import com.hfad.onlinemarket.data.room.entities.Cart;
import com.hfad.onlinemarket.view.fragment.ProfileFragment;

import java.util.ArrayList;
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

    public LiveData<List<Product>> getProductLiveData() {
        return mProductLiveData;
    }

    public CartRepository(Context context) {
        CartRoomDataBase cartRoomDataBase = CartRoomDataBase.getDataBase(context);
        mCartDAO = cartRoomDataBase.getCardDAO();
        mProductLiveData = new MutableLiveData<>();
        mWooCommerceAPI = RetrofitInstance.getInstance().create(WooCommerceAPI.class);
    }

    public void setProductLiveData(List<Cart> carts) {
        List<Product> list = new ArrayList<>();
        mProductLiveData.setValue(list);
        for (int i = 0; i < carts.size(); i++) {
            mWooCommerceAPI.getProductById(carts.get(i).getProductid())
                    .enqueue(new Callback<Product>() {
                        @Override
                        public void onResponse(Call<Product> call, Response<Product> response) {
                            if (response.isSuccessful()) {
                                list.add(response.body());
                                Log.d(TAG, "onResponse: product name fetched from cart " + response.body().getName());
                                mProductLiveData.setValue(list);
                            }
                        }

                        @Override
                        public void onFailure(Call<Product> call, Throwable t) {

                        }
                    });
        }
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


    public boolean postOrder(Order order) {
        final boolean[] result = {false};
        mWooCommerceAPI.postOrder(new HashMap<>(), order)
                .enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        Log.d(TAG, "onResponse: order" + response.isSuccessful());
                        if (response.isSuccessful()) {
                            result[0] = true;
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {

                    }
                });
        Log.d(TAG, "postOrder: boolean" + result[0]);
        return result[0];
    }

}
