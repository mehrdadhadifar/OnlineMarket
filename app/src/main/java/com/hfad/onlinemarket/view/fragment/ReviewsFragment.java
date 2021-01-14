package com.hfad.onlinemarket.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.adapters.ReviewAdapter;
import com.hfad.onlinemarket.data.model.State;
import com.hfad.onlinemarket.databinding.FragmentReviewsBinding;
import com.hfad.onlinemarket.viewmodel.ReviewViewModel;

public class ReviewsFragment extends Fragment {
    public static final String ARG_PRODUCT_ID = "productId";
    private FragmentReviewsBinding mBinding;
    private ReviewViewModel mViewModel;
    private ReviewAdapter mReviewAdapter;

    public ReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
        mViewModel.setProductId(getArguments().getInt(ARG_PRODUCT_ID, 0));
        mViewModel.fetchReviews();
        mReviewAdapter = new ReviewAdapter(mViewModel);

        mViewModel.getState().observe(this, new Observer<State>() {
            @Override
            public void onChanged(State state) {
                if (state == State.READY) {
                    mReviewAdapter.setItems(mViewModel.getReviewsSubject());
                    mReviewAdapter.notifyDataSetChanged();
                    mBinding.setViewModel(mViewModel);
                }
            }
        });


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reviews, container, false);
        mBinding.reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.reviewsRecyclerView.setAdapter(mReviewAdapter);
        mBinding.setViewModel(mViewModel);

        return mBinding.getRoot();
    }
}