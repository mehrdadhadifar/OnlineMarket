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
import com.hfad.onlinemarket.data.model.Options;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.databinding.FragmentProductListBinding;
import com.hfad.onlinemarket.viewmodel.MainPageViewModel;
import com.hfad.onlinemarket.viewmodel.ProductListViewModel;

import java.util.List;


public class ProductListFragment extends Fragment {
    public static final String TAG = "Product List Fragment";
    public static final String ARGS_OPTIONS = "OPTIONS";
    private FragmentProductListBinding mBinding;
    private ProductListViewModel mViewModel;

    public ProductListFragment() {
        // Required empty public constructor
    }

    public static ProductListFragment newInstance() {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
        mViewModel.setOptions((Options) getArguments().getSerializable(ARGS_OPTIONS));
        mViewModel.setInitialData();
        mViewModel.getProductByOptions().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                Log.d(TAG, "onChanged: "+products.size());
                Log.d(TAG, "onChanged: "+products.get(0).getName());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_product_list,
                container,
                false);

        return mBinding.getRoot();
    }
}