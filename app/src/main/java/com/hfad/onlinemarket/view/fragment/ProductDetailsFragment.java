package com.hfad.onlinemarket.view.fragment;

import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.adapters.ImageSliderAdapter;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.data.repository.CartRepository;
import com.hfad.onlinemarket.data.room.entities.Cart;
import com.hfad.onlinemarket.databinding.FragmentProductDetailsBinding;
import com.hfad.onlinemarket.utils.SliderImageDecorator;
import com.hfad.onlinemarket.viewmodel.ProductDetailsViewModel;

import java.util.List;

import static com.hfad.onlinemarket.utils.SnakeBar.showAddSnakeBar;


public class ProductDetailsFragment extends Fragment {
    public static final String ARG_PRODUCT_ID = "ProductId";
    public static final String ARG_PRODUCT_NAME = "name";
    private FragmentProductDetailsBinding mBinding;
    private ProductDetailsViewModel mViewModel;
    private ImageSliderAdapter mImageSliderAdapter;
    private NavController mNavController;


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
        mViewModel.getSelectedProductLiveData().observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                mViewModel.setSelectedProduct(product);
                mImageSliderAdapter.setSliderItems(product.getImages());
                mImageSliderAdapter.notifyDataSetChanged();
                updateUI();
            }
        });
        mViewModel.getCartsLiveData().observe(this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                Log.d(CartRepository.TAG, "addTooCart: number of carts: " + carts.size());
                if (carts.size() > 0)
                    Log.d(CartRepository.TAG, "addTooCart: number of carts: " + carts.get(0).toString());
                mViewModel.setCartsSubject(carts);
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
        mViewModel.setSelectedProductLiveData(productID);
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
        initUI();
        setListeners();
//        Handel the arrival UI
//        updateUI();
        return mBinding.getRoot();
    }

    private void initUI() {
        mBinding.setProductDetailsViewModel(mViewModel);
        mBinding.setLifecycleOwner(this);
        mBinding.imageViewPager.setAdapter(mImageSliderAdapter);
        //Add strike for regular price textView
        mBinding.regularPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void setListeners() {
        mBinding.addToCard.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (!mViewModel.isProductInCart()) {
                    mViewModel.addTooCart();
                    Snackbar snackbar = Snackbar.make(mBinding.getRoot(), R.string.add_to_cart_done, BaseTransientBottomBar.LENGTH_LONG);
                    showAddSnakeBar(snackbar, getActivity());
                } else
                    mNavController.navigate(R.id.action_productDetailsFragment_to_cartFragment);
            }
        });
        mBinding.commentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(ReviewsFragment.ARG_PRODUCT_ID, getArguments().getInt(ARG_PRODUCT_ID));
                mNavController.navigate(R.id.action_productDetailsFragment_to_reviewsFragment, bundle);
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = Navigation.findNavController(view);
    }

    private void updateUI() {
        SliderImageDecorator.SliderImageDecorator(mBinding.imageViewPager);
        mBinding.setProductDetailsViewModel(mViewModel);
    }


}
