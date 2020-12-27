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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.adapters.ProductAdapter;
import com.hfad.onlinemarket.data.model.Options;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.databinding.FragmentProductListBinding;
import com.hfad.onlinemarket.viewmodel.MainPageViewModel;
import com.hfad.onlinemarket.viewmodel.ProductListViewModel;

import java.util.List;

import static com.hfad.onlinemarket.view.fragment.ProductDetailsFragment.ARG_PRODUCT_ID;
import static com.hfad.onlinemarket.view.fragment.ProductDetailsFragment.ARG_PRODUCT_NAME;


public class ProductListFragment extends Fragment implements ProductAdapter.OnProductListener {
    public static final String TAG = "Product List Fragment";
    public static final String ARGS_OPTIONS = "options";
    public static final String ARGS_TITLE = "name";
    private FragmentProductListBinding mBinding;
    private ProductListViewModel mViewModel;
    private ProductAdapter mProductAdapter;
    private NavController mNavController;

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
        initViewModel();
        initAdapters();
        setObservers();
    }

    private void initAdapters() {
        mProductAdapter=new ProductAdapter(this);
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
        mViewModel.setOptions((Options) getArguments().getSerializable(ARGS_OPTIONS));
        mViewModel.setInitialData();
    }

    private void setObservers() {
        mViewModel.getProductByOptions().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                Log.d(TAG, "onChanged: "+products.size());
                mProductAdapter.setItems(products);
                mProductAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_product_list,
                container,
                false);

        mBinding.productRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mBinding.productRecyclerView.setAdapter(mProductAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController= Navigation.findNavController(view);
    }

    @Override
    public void onProductClicked(Product product) {
        Bundle bundle=new Bundle();
        bundle.putInt(ARG_PRODUCT_ID,product.getId());
        bundle.putString(ARG_PRODUCT_NAME,product.getName());
        mNavController.navigate(R.id.action_productListFragment_to_productDetailsFragment,bundle);
    }
}