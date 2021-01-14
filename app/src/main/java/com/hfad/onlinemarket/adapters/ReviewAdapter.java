package com.hfad.onlinemarket.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.data.model.review.Review;
import com.hfad.onlinemarket.databinding.ListItemReviewsBinding;
import com.hfad.onlinemarket.viewmodel.ReviewViewModel;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {
    List<Review> mItems;
    ReviewViewModel mReviewViewModel;

    public List<Review> getItems() {
        return mItems;
    }

    public void setItems(List<Review> items) {
        mItems = items;
    }

    public ReviewAdapter(ReviewViewModel reviewViewModel) {
        mReviewViewModel = reviewViewModel;
        mItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemReviewsBinding listItemReviewsBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_reviews,
                parent,
                false);
        return new ReviewHolder(listItemReviewsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewHolder holder, int position) {
        holder.bindReview(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {
        ListItemReviewsBinding mBinding;

        public ReviewHolder(ListItemReviewsBinding listItemReviewsBinding) {
            super(listItemReviewsBinding.getRoot());
            mBinding = listItemReviewsBinding;
            mBinding.setViewModel(mReviewViewModel);
        }

        public void bindReview(Review review) {
            mBinding.setReview(review);
        }
    }
}
