package com.hfad.onlinemarket.view.fragment;

import android.os.Bundle;
import android.util.Log;
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
import com.hfad.onlinemarket.adapters.AddressAdapter;
import com.hfad.onlinemarket.databinding.FragmentFinishShoppingBinding;
import com.hfad.onlinemarket.viewmodel.FinishShoppingViewModel;

public class FinishShoppingFragment extends Fragment {
    public static final String TAG = "Finish ShoppingFragment";
    private FragmentFinishShoppingBinding mBinding;
    private FinishShoppingViewModel mViewModel;
    private AddressAdapter mAddressAdapter;
    private NavController mNavController;


    public FinishShoppingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FinishShoppingViewModel.class);
        mAddressAdapter = new AddressAdapter(mViewModel);
        setObservers();

    }

    private void setObservers() {
        mViewModel.getAddresses().observe(this, myAddresses -> {
            Log.d(TAG, "onChanged: " + myAddresses.size());
            mAddressAdapter.setItems(myAddresses);
            mAddressAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_finish_shopping, container, false);
        mBinding.addressRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.addressRecyclerView.setAdapter(mAddressAdapter);

        setListeners();

        return mBinding.getRoot();
    }

    private void setListeners() {
        mBinding.addAddressButton.setOnClickListener(v -> mNavController
                .navigate(R.id.action_finishShoppingFragment_to_addAddressMapsFragment));
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