package com.hfad.onlinemarket.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.onlinemarket.data.model.State;
import com.hfad.onlinemarket.data.model.review.Review;
import com.hfad.onlinemarket.data.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewViewModel extends ViewModel {
    public static final String TAG = "Review ViewModel";
    private final ReviewRepository mReviewRepository;
    private List<Review> mReviewsSubject;
    private final MutableLiveData<List<Review>> mReviewsLiveData;
    private int mProductId;
    private String mProductName;
    private String mProductRate;
    private final MutableLiveData<State> state;

    public ReviewViewModel() {
        mReviewRepository = ReviewRepository.getInstance();
        mReviewsSubject = new ArrayList<>();
        mReviewsLiveData = new MutableLiveData<>();
        state = new MutableLiveData<>();
        state.setValue(State.WAIT);
    }

    public MutableLiveData<List<Review>> getReviewsLiveData() {
        return mReviewsLiveData;
    }

    public MutableLiveData<State> getState() {
        return state;
    }

    public List<Review> getReviewsSubject() {
        return mReviewsSubject;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int productId) {
        this.mProductId = productId;
    }

    public void setProductRate(String productRate) {
        mProductRate = productRate;
    }

    public String getProductRate() {
        return mProductRate;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public void setInitData(int productId, String productName, String productRate) {
        setProductId(productId);
        setProductName(productName);
        setProductRate(productRate);
    }

    public void fetchReviews() {
        mReviewRepository.getReviewsLiveData().enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                Log.d(TAG, "onResponse: " + response.body().size());
                mReviewsLiveData.setValue(response.body());
                getProductReviews();
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {

            }
        });
    }

    public void getProductReviews() {
        if (mReviewsLiveData.getValue() != null && mReviewsLiveData.getValue().size() > 0) {
            mReviewsSubject = new ArrayList<>();
            for (int i = 0; i < mReviewsLiveData.getValue().size(); i++) {
                if (mReviewsLiveData.getValue().get(i).getProductId() == mProductId) {
                    mReviewsSubject.add(mReviewsLiveData.getValue().get(i));
                    Log.d(TAG, "getProductReviews: " + mReviewsLiveData.getValue().get(i).getReview());
                    Log.d(TAG, "getProductReviews: " + mReviewsLiveData.getValue().get(i).getDateCreated());
                }
            }
        }
        state.setValue(State.READY);
    }

    public boolean isLoading() {
        return state.getValue() == State.WAIT;
    }

    public String reviewsNumber() {
        String result = " دیدگاه";
        if (state.getValue() == State.READY)
            result = mReviewsSubject.size() + " دیدگاه";
        return result;
    }

    public void deleteReview(Review review) {
        state.setValue(State.WAIT);
        mReviewRepository.deleteReview(review.getId()).enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.isSuccessful());
                    mReviewsSubject.remove(review);
                    state.setValue(State.READY);
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {

            }
        });
    }

}
