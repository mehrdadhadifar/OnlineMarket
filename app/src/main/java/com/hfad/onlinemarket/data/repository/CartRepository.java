package com.hfad.onlinemarket.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.hfad.onlinemarket.data.room.CartRoomDataBase;
import com.hfad.onlinemarket.data.room.dao.CartDAO;
import com.hfad.onlinemarket.data.room.entities.Cart;

import java.util.List;

public class CartRepository {
    public static final String TAG = "Cart Repository";
    private static CartRepository sInstance;
    private CartDAO mCartDAO;

    public static synchronized CartRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new CartRepository(context);
        return sInstance;
    }

    public CartRepository(Context context) {
        CartRoomDataBase cartRoomDataBase = CartRoomDataBase.getDataBase(context);
        mCartDAO = cartRoomDataBase.getCardDAO();
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


}
