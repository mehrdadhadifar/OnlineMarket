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
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.adapters.AddressAdapter;
import com.hfad.onlinemarket.data.room.entities.MyAddress;
import com.hfad.onlinemarket.databinding.FragmentFinishShoppingBinding;
import com.hfad.onlinemarket.viewmodel.FinishShoppingViewModel;

import java.util.List;

public class FinishShoppingFragment extends Fragment {
    public static final String TAG = "Finish ShoppingFragment";
    private FragmentFinishShoppingBinding mBinding;
    private FinishShoppingViewModel mViewModel;
    private AddressAdapter mAddressAdapter;
    private NavController mNavController;


    public FinishShoppingFragment() {
        // Required empty public constructor
    }

    public static FinishShoppingFragment newInstance() {
        FinishShoppingFragment fragment = new FinishShoppingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FinishShoppingViewModel.class);
        mAddressAdapter = new AddressAdapter(mViewModel);
        setObservers();

    }

    private void setObservers() {
        mViewModel.getAddresses().observe(this, new Observer<List<MyAddress>>() {
            @Override
            public void onChanged(List<MyAddress> myAddresses) {
                Log.d(TAG, "onChanged: " + myAddresses.size());
                mAddressAdapter.setItems(myAddresses);
                mAddressAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_finish_shopping, container, false);
        mBinding.addressRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.addressRecyclerView.setAdapter(mAddressAdapter);

        setListeners();

        return mBinding.getRoot();
    }

    private void setListeners() {
        mBinding.addAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(R.id.action_finishShoppingFragment_to_addAddressMapsFragment);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = Navigation.findNavController(view);
    }

    @Override
    public void onStop() {
        mViewModel.deselectAllAddresses();
        super.onStop();
    }
}