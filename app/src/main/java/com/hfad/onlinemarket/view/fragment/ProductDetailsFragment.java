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
import com.hfad.onlinemarket.viewmodel.ProductDetailsViewModel;


public class ProductDetailsFragment extends Fragment {
    public static final String ARG_PRODUCT_ID = "ProductId";
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
//        mProduct = mViewModel.getSelectedProduct();
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
        setImageSlider();
        mBinding.setProductDetailsViewModel(mViewModel);
    }

    private void setImageSlider() {
        mBinding.imageViewPager.setClipToPadding(false);
        mBinding.imageViewPager.setClipChildren(false);
        mBinding.imageViewPager.setOffscreenPageLimit(1);
        mBinding.imageViewPager.setPadding(128, 0, 128, 0);
        mBinding.imageViewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        mBinding.imageViewPager.setPageTransformer(transformer);
        Handler sliderHandler = new Handler(Looper.getMainLooper());
        Runnable slideRunnable = new Runnable() {
            @Override
            public void run() {
                if (mBinding.imageViewPager.getCurrentItem() == mViewModel.getNumberOfImages() - 1)
                    mBinding.imageViewPager.setCurrentItem(0);
                else
                    mBinding.imageViewPager.setCurrentItem(mBinding.imageViewPager.getCurrentItem() + 1);
            }
        };
        sliderHandler.postDelayed(slideRunnable, 3000);
        mBinding.imageViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(slideRunnable);
                sliderHandler.postDelayed(slideRunnable, 3000);
            }
        });
    }
}
