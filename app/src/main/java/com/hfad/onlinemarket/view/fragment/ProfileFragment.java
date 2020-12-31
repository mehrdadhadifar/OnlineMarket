package com.hfad.onlinemarket.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.data.model.customer.Customer;
import com.hfad.onlinemarket.databinding.FragmentProfileBinding;
import com.hfad.onlinemarket.utils.QueryPreferences;
import com.hfad.onlinemarket.viewmodel.ProfileViewModel;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {
    public static final String TAG = "Profile Fragment";
    private FragmentProfileBinding mBinding;
    private ProfileViewModel mProfileViewModel;


    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProfileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        mProfileViewModel.setCustomerLiveData(QueryPreferences.getCustomerEmail(getActivity()));

        mProfileViewModel.getCustomerLiveData().observe(this, new Observer<Customer>() {
            @Override
            public void onChanged(Customer customer) {
                if (customer != null) {
                    Log.d(TAG, "onChanged: customer email: " + customer.getEmail());
                    QueryPreferences.setCustomerEmail(getContext(), customer.getEmail());
                    mBinding.setProfileViewModel(mProfileViewModel);
//                    Picasso.get().load(customer.getAvatarUrl()).placeholder(R.drawable.avatar).into(mBinding.avatarImageView);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);


        setListeners();

        mBinding.setProfileViewModel(mProfileViewModel);
        return mBinding.getRoot();
    }

    private void setListeners() {
        mBinding.signInSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.editTextEmail.getText() != null) {
                    mProfileViewModel.setCustomerLiveData(mBinding.editTextEmail.getText().toString());
                }
            }
        });
        mBinding.exitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfileViewModel.signOut();
                mBinding.setProfileViewModel(mProfileViewModel);
            }
        });
    }
}