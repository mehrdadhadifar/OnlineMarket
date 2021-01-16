package com.hfad.onlinemarket.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.data.model.State;
import com.hfad.onlinemarket.databinding.FragmentAddEditReviewBinding;
import com.hfad.onlinemarket.viewmodel.AddEditReviewViewModel;


public class AddEditReviewFragment extends Fragment {
    public static final String TAG = "Add Review Fragment";
    public static final String ARG_PRODUCT_NAME = "name";
    public static final String ARG_PRODUCT_ID = "productId";
    public static final String ARG_REVIEW_ID = "reviewId";
    private FragmentAddEditReviewBinding mBinding;
    private AddEditReviewViewModel mViewModel;
    private NavController mNavController;


    public AddEditReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mViewModel = new ViewModelProvider(this).get(AddEditReviewViewModel.class);
        mViewModel.setInitData(getArguments().getInt(ARG_REVIEW_ID), getArguments().getInt(ARG_PRODUCT_ID), getArguments().getString(ARG_PRODUCT_NAME));
        mViewModel.getState().observe(this, new Observer<State>() {
            @Override
            public void onChanged(State state) {
                mBinding.setViewModel(mViewModel);
                if (state == State.COMPLETE)
                    mNavController.navigateUp();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit_review, container, false);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = Navigation.findNavController(view);
    }
}