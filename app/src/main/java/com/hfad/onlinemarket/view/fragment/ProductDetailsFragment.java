package com.hfad.onlinemarket.view.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.adapters.ImageSliderAdapter;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.databinding.FragmentProductDetailsBinding;
import com.hfad.onlinemarket.utils.SliderImageDecorator;
import com.hfad.onlinemarket.viewmodel.ProductDetailsViewModel;


public class ProductDetailsFragment extends Fragment {
    public static final String ARG_PRODUCT_ID = "ProductId";
    public static final String ARG_PRODUCT_NAME = "name";
    private FragmentProductDetailsBinding mBinding;
    private ProductDetailsViewModel mViewModel;
    private ImageSliderAdapter mImageSliderAdapter;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initImageSliderAdapter();
        setObservers();
    }

    private void setObservers() {
        mViewModel.getSelectedProduct().observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                mImageSliderAdapter.setSliderItems(product.getImages());
                mImageSliderAdapter.notifyDataSetChanged();
                updateUI();
            }
        });
    }

    private void initImageSliderAdapter() {
        mImageSliderAdapter = new ImageSliderAdapter();
    }

    private void initData() {
        int productID = getArguments().getInt(ARG_PRODUCT_ID);
        mViewModel = new ViewModelProvider(this).get(ProductDetailsViewModel.class);
        mViewModel.setSelectedProduct(productID);
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
        mBinding.imageViewPager.setAdapter(mImageSliderAdapter);
        //Add strike for regular price textView
        mBinding.regularPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        Handel the arrival UI
//        updateUI();
        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void updateUI() {
        SliderImageDecorator.SliderImageDecorator(mBinding.imageViewPager);
        mBinding.setProductDetailsViewModel(mViewModel);
    }


}
