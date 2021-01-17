package com.hfad.onlinemarket.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.adapters.ReviewAdapter;
import com.hfad.onlinemarket.data.model.State;
import com.hfad.onlinemarket.databinding.FragmentReviewsBinding;
import com.hfad.onlinemarket.viewmodel.ReviewViewModel;

import static com.hfad.onlinemarket.view.fragment.AddEditReviewFragment.ARG_PRODUCT_NAME;
import static com.hfad.onlinemarket.view.fragment.AddEditReviewFragment.ARG_REVIEW_ID;

public class ReviewsFragment extends Fragment implements ReviewAdapter.OnReviewListener {
    public static final String ARG_PRODUCT_ID = "productId";
    public static final String ARG_PRODUCT_RATE = "productRate";
    private FragmentReviewsBinding mBinding;
    private ReviewViewModel mViewModel;
    private ReviewAdapter mReviewAdapter;
    private NavController mNavController;

    public ReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
        mViewModel.setInitData(getArguments().getInt(ARG_PRODUCT_ID, 0), getArguments().getString(ARG_PRODUCT_NAME), getArguments().getString(ARG_PRODUCT_RATE));
        mReviewAdapter = new ReviewAdapter(mViewModel, this);

        mViewModel.getState().observe(this, state -> {
            if (state == State.READY) {
                mReviewAdapter.setItems(mViewModel.getReviewsSubject());
                mReviewAdapter.notifyDataSetChanged();
            }
            mBinding.setViewModel(mViewModel);
        });


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reviews, container, false);
        initUI();
        setListeners();
        return mBinding.getRoot();
    }

    private void setListeners() {
        mBinding.floatingActionButtonAddReview.setOnClickListener(v -> onReviewClicked(0));
    }


    private void initUI() {
        mBinding.reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.reviewsRecyclerView.setAdapter(mReviewAdapter);
        mBinding.setViewModel(mViewModel);
        mViewModel.fetchReviews();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = Navigation.findNavController(view);
    }

    @Override
    public void onReviewClicked(int reviewId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_REVIEW_ID, reviewId);
        bundle.putString(ARG_PRODUCT_NAME, getArguments().getString(ARG_PRODUCT_NAME));
        bundle.putInt(ARG_PRODUCT_ID, getArguments().getInt(ARG_PRODUCT_ID));
        mNavController.navigate(R.id.action_reviewsFragment_to_addEditReviewFragment, bundle);
    }
}