package com.hfad.onlinemarket.data.repository;

import com.hfad.onlinemarket.data.model.review.Review;
import com.hfad.onlinemarket.data.remote.NetworkParams;
import com.hfad.onlinemarket.data.remote.retrofit.RetrofitInstance;
import com.hfad.onlinemarket.data.remote.retrofit.WooCommerceAPI;

import java.util.List;

import retrofit2.Call;

public class ReviewRepository {
    private static ReviewRepository sInstance;
    private static WooCommerceAPI mWooCommerceAPI;

    public static ReviewRepository getInstance(){
        if(sInstance==null)
            sInstance=new ReviewRepository();
        return sInstance;
    }

    public ReviewRepository() {
        mWooCommerceAPI= RetrofitInstance.getInstance().create(WooCommerceAPI.class);
    }

    public Call<List<Review>> getReviewsLiveData(){
        return mWooCommerceAPI.getReviews(NetworkParams.getCoupons(100));
    }
}
