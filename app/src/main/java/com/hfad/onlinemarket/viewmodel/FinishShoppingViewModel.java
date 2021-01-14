package com.hfad.onlinemarket.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hfad.onlinemarket.data.model.coupon.Coupon;
import com.hfad.onlinemarket.data.model.order.LineItemsItem;
import com.hfad.onlinemarket.data.model.order.Order;
import com.hfad.onlinemarket.data.repository.AddressRepository;
import com.hfad.onlinemarket.data.repository.CartRepository;
import com.hfad.onlinemarket.data.room.entities.Cart;
import com.hfad.onlinemarket.data.room.entities.MyAddress;
import com.hfad.onlinemarket.utils.PriceFormatter;
import com.hfad.onlinemarket.utils.QueryPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinishShoppingViewModel extends AndroidViewModel {
    public static final String TAG = "Finish Shopping ViewMod";
    private final AddressRepository mAddressRepository;
    private final CartRepository mCartRepository;
    private MyAddress mLastAddressSelected;
    private final MutableLiveData<Long> mTotalPrice;
    private long mBasePrice;
    private boolean mCouponUsed;
    private List<Cart> mCartsSubject = new ArrayList<>();
    private List<Coupon> mCouponSubject = new ArrayList<>();
    private MutableLiveData<String> mOrderState;


    public FinishShoppingViewModel(@NonNull Application application) {
        super(application);
        mAddressRepository = AddressRepository.getInstance(getApplication());
        mCartRepository = CartRepository.getInstance(getApplication());
        mTotalPrice = new MutableLiveData<>();
        mOrderState=new MutableLiveData<>();
    }

    public boolean isCouponUsed() {
        return mCouponUsed;
    }

    public void setCouponUsed(boolean couponUsed) {
        this.mCouponUsed = couponUsed;
    }

    public String getBasePriceFormatted() {
        return PriceFormatter.priceFormatter(String.valueOf(mBasePrice)) + "تومان ";
    }

    public void setBasePrice(long basePrice) {
        this.mBasePrice = basePrice;
        mTotalPrice.setValue(basePrice);
    }

    public MutableLiveData<String> getOrderState() {
        return mOrderState;
    }

    public void setOrderState(MutableLiveData<String> orderState) {
        mOrderState = orderState;
    }

    public List<Cart> getCartsSubject() {
        return mCartsSubject;
    }

    public void setCartsSubject(List<Cart> cartsSubject) {
        mCartsSubject = cartsSubject;
    }

    public List<Coupon> getCouponSubject() {
        return mCouponSubject;
    }

    public void setCouponSubject(List<Coupon> couponSubject) {
        mCouponSubject = couponSubject;
    }

    public MutableLiveData<Long> getTotalPrice() {
        return mTotalPrice;
    }

    public String getTotalPriceFormatted() {
        return PriceFormatter.priceFormatter(String.valueOf(mTotalPrice.getValue())) + "تومان ";
    }

    public void setTotalPrice(long totalPrice) {
        this.mTotalPrice.setValue(totalPrice);
    }

    public LiveData<List<MyAddress>> getAddresses() {
        return mAddressRepository.getAddresses();
    }

    public void setSelectedAddress(MyAddress address) {
        if (mLastAddressSelected != null) {
            mLastAddressSelected.setSelected(false);
            mAddressRepository.updateAddress(mLastAddressSelected);
        }
        address.setSelected(true);
        mLastAddressSelected = address;
        mAddressRepository.updateAddress(address);
    }

    public void deselectAllAddresses() {
        if (mLastAddressSelected != null) {
            mLastAddressSelected.setSelected(false);
            mAddressRepository.updateAddress(mLastAddressSelected);
        }
    }

    public LiveData<List<Cart>> fetchCartsLiveData() {
        return mCartRepository.getCartLiveData();
    }

    public void fetchCouponsLiveData() {
        mCartRepository.setCoupons().enqueue(new Callback<List<Coupon>>() {
            @Override
            public void onResponse(Call<List<Coupon>> call, Response<List<Coupon>> response) {
                Log.d(TAG, "onResponse: coupon " + response.body().get(0).getCode());
                setCouponSubject(response.body());
            }

            @Override
            public void onFailure(Call<List<Coupon>> call, Throwable t) {

            }
        });
    }

    public boolean readyToFinish() {
        Log.d(TAG, "readyToFinish: " + mLastAddressSelected + mTotalPrice.getValue());
        return mLastAddressSelected != null && mTotalPrice.getValue() != null && mTotalPrice.getValue() != 0;
    }

    public String salePriceDifferenceFormatted() {
        if (mTotalPrice.getValue() == null)
            return "";
        long difference = mBasePrice - mTotalPrice.getValue();
        return PriceFormatter.priceFormatter(String.valueOf(difference)) + "- تومان ";
    }

    public boolean checkCoupon(String text) {
        Log.d(TAG, "checkCoupon: " + mCouponSubject);
        for (int i = 0; i < mCouponSubject.size(); i++) {
            Log.d(TAG, "checkCoupon: " + mCouponSubject.get(i).getCode() + text);
            if (mCouponSubject.get(i).getCode().equals(text)) {
                mCouponUsed = true;
                mTotalPrice.setValue(mBasePrice - Double.valueOf((mCouponSubject.get(i).getAmount())).longValue());

                return true;
            }
        }
        return false;
    }


    public void postOrder() {
        mOrderState.setValue("wait");
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
                Log.d(TAG, "onResponse: order " + response.isSuccessful());
                if (response.isSuccessful()) {
                    mCartRepository.deleteAllCarts();
                    mOrderState.setValue("ok");
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
            }
        });
    }
    public boolean isOrderWaiting(){
        return mOrderState.getValue() != null && mOrderState.getValue().equals("wait");
    }

}
