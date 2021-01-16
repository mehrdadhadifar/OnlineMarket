package com.hfad.onlinemarket.viewmodel;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.data.model.State;
import com.hfad.onlinemarket.data.model.review.Review;
import com.hfad.onlinemarket.data.repository.ReviewRepository;
import com.hfad.onlinemarket.utils.QueryPreferences;
import com.hfad.onlinemarket.view.fragment.AddEditReviewFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hfad.onlinemarket.utils.SnakeBar.showAddSnakeBar;

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void submitReview(View view) {
        if (mReviewerName == null || mReviewNote == null || mReviewerName.length() < 1 || mReviewNote.length() < 1) {
            Snackbar snackbar = Snackbar.make(view, R.string.please_get_full_info, BaseTransientBottomBar.LENGTH_LONG);
            showAddSnakeBar(snackbar, getApplication());
            return;
        }
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
        Review review = new Review(mReviewNote, mProductId, mReviewRate, mReviewerName, mEmail);
        if (mReviewId != 0) {
            review.setId(mReviewId);
            mReviewRepository.updateReview(mReviewId, review).enqueue(new Callback<Review>() {
                @Override
                public void onResponse(Call<Review> call, Response<Review> response) {
                    if (response.isSuccessful()) {
                        Log.d(AddEditReviewFragment.TAG, "onResponse: update review" + response.isSuccessful());
                        mState.setValue(State.COMPLETE);
                    }
                }

                @Override
                public void onFailure(Call<Review> call, Throwable t) {

                }
            });
        } else {
            mReviewRepository.postReview(review).enqueue(new Callback<Review>() {
                @Override
                public void onResponse(Call<Review> call, Response<Review> response) {
                    if (response.isSuccessful()) {
                        Log.d(AddEditReviewFragment.TAG, "onResponse: post review" + response.isSuccessful());
                        mState.setValue(State.COMPLETE);
                    }
                }

                @Override
                public void onFailure(Call<Review> call, Throwable t) {
                    Log.d(AddEditReviewFragment.TAG, "onFailure: " + t.getMessage());
                }
            });
        }
    }

}
