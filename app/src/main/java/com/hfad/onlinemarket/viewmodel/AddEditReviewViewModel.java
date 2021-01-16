package com.hfad.onlinemarket.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.hfad.onlinemarket.data.model.State;
import com.hfad.onlinemarket.data.model.review.Review;
import com.hfad.onlinemarket.data.repository.ReviewRepository;
import com.hfad.onlinemarket.utils.QueryPreferences;
import com.hfad.onlinemarket.view.fragment.AddEditReviewFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditReviewViewModel extends AndroidViewModel {
    private ReviewRepository mReviewRepository;
    private int mReviewId;
    private int mProductId;
    private String mProductName;
    private String mEmail;
    private String mReviewerName;
    private String mReviewNote;
    private int mReviewRate;
    private MutableLiveData<Review> mReviewLiveData;
    private MutableLiveData<State> mState;

    public AddEditReviewViewModel(@NonNull Application application) {
        super(application);
        mReviewRepository = ReviewRepository.getInstance();
        mReviewLiveData = new MutableLiveData<>();
        mState = new MutableLiveData<>();
        getState().setValue(State.WAIT);
    }

    public MutableLiveData<State> getState() {
        return mState;
    }

    public void setInitData(int reviewId, int productId, String productName) {
        Log.d(AddEditReviewFragment.TAG, "setInitData: " + reviewId);
        mReviewId = reviewId;
        mProductId = productId;
        mProductName = productName;
        if (mReviewId != 0) {
            fetchReview(reviewId);
        } else
            mState.setValue(State.READY);
        setReview(null);
    }

    private void fetchReview(int reviewId) {
        mReviewRepository.getReview(reviewId).enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.isSuccessful()) {
                    Log.d(AddEditReviewFragment.TAG, "onResponse: " + response.body().getReview());
                    mReviewLiveData.setValue(response.body());
                    setReview(response.body());
                    mState.setValue(State.READY);
                }

            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {

            }
        });
    }

    private void setReview(Review body) {
        if (body != null) {
            mEmail = body.getReviewerEmail();
            mReviewerName = body.getReviewer();
            mReviewNote = body.getReview();
            mReviewRate = body.getRating();
        } else {
            mEmail = QueryPreferences.getCustomerEmail(getApplication());
            mReviewRate = 5;
        }
    }

    public boolean isLoading() {
        return getState().getValue() == State.WAIT;
    }

    public String getCommentNote() {
        return "ثبت نظر برای " + mProductName;
    }

    public String getEmail() {
        return "ایمیل : " + mEmail;
    }

    public String getReviewerName() {
        return mReviewerName;
    }

    public String getReviewNote() {
        return mReviewNote;
    }

    public int getSelectedItemPosition() {
        return mReviewRate;
    }

    public void setSelectedItemPosition(int position) {
        mReviewRate = position;
        Log.d(AddEditReviewFragment.TAG, "setSelectedItemPosition: " + position);
    }

    public void onTextChangedReviewNote(CharSequence charSequence, int i, int i1, int i2) {
        mReviewNote = charSequence.toString();
        Log.d(AddEditReviewFragment.TAG, "onTextChangedReviewNote: " + mReviewNote);
    }

    public void onTextChangedReviewerName(CharSequence charSequence, int i, int i1, int i2) {
        mReviewerName = charSequence.toString();
        Log.d(AddEditReviewFragment.TAG, "onTextChangedReviewNote: " + mReviewerName);
    }

    public void submitReview() {
        mState.setValue(State.WAIT);
        Log.d(AddEditReviewFragment.TAG, "submitReview: " +
                "AddEditReviewViewModel{" +
                "mReviewId=" + mReviewId +
                ", mProductId=" + mProductId +
                ", mEmail='" + mEmail + '\'' +
                ", mReviewerName='" + mReviewerName + '\'' +
                ", mReviewNote='" + mReviewNote + '\'' +
                ", mReviewRate=" + mReviewRate +
                '}');
        if (mEmail.length() < 1)
            mEmail = "Anonymous@gmail.com";
        Review review = new Review(mReviewNote, mProductId, mReviewRate, mReviewId, mReviewerName, mEmail);
        mReviewRepository.updateReview(mReviewId, review).enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.isSuccessful()) {
                    Log.d(AddEditReviewFragment.TAG, "onResponse: " + response.isSuccessful());
                    mState.setValue(State.SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {

            }
        });
    }

}
