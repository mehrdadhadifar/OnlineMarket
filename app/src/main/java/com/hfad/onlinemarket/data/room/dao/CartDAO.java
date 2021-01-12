package com.hfad.onlinemarket.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hfad.onlinemarket.data.room.entities.Cart;

import java.util.List;

@Dao
public interface CartDAO {
    @Insert
    void insertCarts(Cart... carts);

    @Update
    void updateCarts(Cart... carts);

    @Delete
    void deleteCarts(Cart... carts);

    @Query("select * from cart")
    LiveData<List<Cart>> getCarts();

    @Query("select * from cart where productid = :productid")
    Cart getCart(Integer productid);

    @Query("delete from cart")
    void deleteAllCarts();
}
