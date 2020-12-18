package com.hfad.onlinemarket.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.adapters.ImageSliderAdapter;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.databinding.FragmentProductDetailsBinding;
import com.hfad.onlinemarket.databinding.ListItemProductBinding;


public class ProductDetailsFragment extends Fragment {
    private FragmentProductDetailsBinding mBinding;


    public ProductDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_product_details,
                container,
                false
        );

//        mBinding.imageViewPager.
        Product product= (Product) getArguments().getSerializable("product");
        ImageSliderAdapter adapter=new ImageSliderAdapter(product.getImages());
        mBinding.imageViewPager.setAdapter(adapter);
        return mBinding.getRoot();
    }
}