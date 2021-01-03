package com.hfad.onlinemarket.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hfad.onlinemarket.data.model.order.LineItemsItem;
import com.hfad.onlinemarket.data.model.order.Order;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.data.repository.CartRepository;
import com.hfad.onlinemarket.data.repository.CustomerRepository;
import com.hfad.onlinemarket.data.repository.ProductRepository;
import com.hfad.onlinemarket.data.room.entities.Cart;
import com.hfad.onlinemarket.utils.PriceFormatter;
import com.hfad.onlinemarket.utils.QueryPreferences;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private CartRepository mCartRepository;
    private ProductRepository mProductRepository;
    private CustomerRepository mCustomerRepository;
    private List<Cart> mCartsSubject = new ArrayList<>();
    private LiveData<List<Cart>> mCartsLiveData;


    public CartViewModel(@NonNull Application application) {
        super(application);
        mCartRepository = CartRepository.getInstance(application);
        mProductRepository = ProductRepository.getInstance();
        mCustomerRepository = CustomerRepository.getCustomer();
        mCartsLiveData = fetchCartsLiveData();
    }

    public LiveData<List<Product>> getProductLiveData() {
        return mCartRepository.getProductLiveData();
    }

    public void setProductsList(List<Cart> carts) {
        mCartRepository.setProductLiveData(carts);
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

    public String getCartProductsNumber() {
        return mCartsSubject.size() > 0 ? mCartsSubject.size() + " مورد" : "سبد خرید خالی است";
    }

    public boolean isCartProductsEmpty() {
        return mCartsSubject.size() > 0 ? false : true;
    }

    public String getCartItemQuantity(int productId) {
        return String.valueOf(getCartFromCardSubject(productId).getCount());
    }

    public void removeCartItem(int productId) {
        Cart cart = getCartFromCardSubject(productId);
        if (cart.getCount() > 1) {
            cart.setCount(cart.getCount() - 1);
            mCartRepository.updateCart(cart);
        } else
            mCartRepository.deleteCart(cart);
    }

    public void addCartItem(int productId) {
        Cart cart = getCartFromCardSubject(productId);
        cart.setCount(cart.getCount() + 1);
        mCartRepository.updateCart(cart);
    }

    public String calculateItemPrice(Product product) {
        Cart cart = getCartFromCardSubject(product.getId());
        long price = product.getLongPrice() * cart.getCount();
        Log.d(CartRepository.TAG, "calculateItemPrice: price a product: " + product.getLongPrice());
        return PriceFormatter.priceFormatter(String.valueOf(price)) + " تومان";
    }

    public String calculateTotalPrice() {
        long total = 0;
        if (mCartRepository.getProductLiveData().getValue() != null) {
            for (int i = 0; i < mCartRepository.getProductLiveData().getValue().size(); i++) {
                Cart cart = getCartFromCardSubject(mCartRepository.getProductLiveData().getValue().get(i).getId());
                long price = mCartRepository.getProductLiveData().getValue().get(i).getLongPrice() * cart.getCount();
                total += price;
            }
        }
        return PriceFormatter.priceFormatter(String.valueOf(total)) + "تومان ";
    }


    public Cart getCartFromCardSubject(int productId) {
        for (Cart cart : mCartsSubject
        ) {
            if (cart.getProductid() == productId)
                return cart;
        }
        return null;
    }

    public boolean postOrder() {
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
        if (mCartRepository.postOrder(order)) {
            mCartRepository.deleteAllCarts();
            return true;
        }
        return false;
    }
}
