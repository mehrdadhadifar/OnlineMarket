package com.hfad.onlinemarket.view.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.adapters.ProductAdapter;
import com.hfad.onlinemarket.data.model.Options;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.databinding.FragmentProductDetailsBinding;
import com.hfad.onlinemarket.databinding.FragmentProductListBinding;
import com.hfad.onlinemarket.viewmodel.ProductListViewModel;

import java.util.List;

import static com.hfad.onlinemarket.view.fragment.BottomSheetFilterDialogFragment.ARGS_CALLBACK;
import static com.hfad.onlinemarket.view.fragment.BottomSheetFilterDialogFragment.ARGS_FILTERED_OPTIONS;
import static com.hfad.onlinemarket.view.fragment.ProductDetailsFragment.ARG_PRODUCT_ID;
import static com.hfad.onlinemarket.view.fragment.ProductDetailsFragment.ARG_PRODUCT_NAME;


public class ProductListFragment extends Fragment implements ProductAdapter.OnProductListener, BottomSheetFilterDialogFragment.FilterCallback {
    public static final String TAG = "Product List Fragment";
    public static final String ARGS_OPTIONS = "options";
    public static final String ARGS_TITLE = "name";
    private FragmentProductListBinding mBinding;
    private ProductListViewModel mViewModel;
    private ProductAdapter mProductAdapter;
    private NavController mNavController;
    private BottomSheetFilterDialogFragment.FilterCallback mFilterCallbackInstance;

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
        Log.d(TAG, "initViewModel: " + getArguments().getSerializable(ARGS_OPTIONS).toString());
        mFilterCallbackInstance = this;
        initViewModel();
        initAdapters();
        setObservers();
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
        mViewModel.setOptions((Options) getArguments().getSerializable(ARGS_OPTIONS));
        mViewModel.setInitialData();
    }

    private void initAdapters() {
        mProductAdapter = new ProductAdapter(this);
    }

    private void setObservers() {
        mViewModel.getProductByOptions().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                Log.d(TAG, "onChanged: " + products.size());
                if (products.size() == 0)
                    mBinding.errorTextView.setVisibility(View.VISIBLE);
                if (products.size() > 0)
                    mBinding.errorTextView.setVisibility(View.GONE);
                mProductAdapter.setItems(products);
                mProductAdapter.notifyDataSetChanged();
                mBinding.productListProgressBar.setVisibility(View.GONE);
                mBinding.productRecyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentProductListBinding.inflate(inflater);


        setInitView();
        setListeners();

        return mBinding.getRoot();
    }

    private void setListeners() {
        mBinding.orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.setOrderedProducts(position);
                if (position != 1) {
                    mBinding.productListProgressBar.setVisibility(View.VISIBLE);
                    mBinding.productRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mBinding.filterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.setInitDataForFilters();
                Bundle bundle = new Bundle();
                bundle.putSerializable(ARGS_FILTERED_OPTIONS, mViewModel.getOptions());
                bundle.putSerializable(ARGS_CALLBACK, mFilterCallbackInstance);
                Log.d(TAG, "onClick: filter" + mViewModel.getOptions().toString());
                mNavController.navigate(R.id.action_productListFragment_to_bottomSheetFilterDialogFragment, bundle);
            }
        });
    }

    private void setInitView() {
        mBinding.setViewModel(mViewModel);
        mBinding.productRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mBinding.productRecyclerView.setAdapter(mProductAdapter);
        setSpinnerPosition();
    }

    private void setSpinnerPosition() {
        int position = 0;
        switch (mViewModel.getOptions().getOrderBy()) {
            case price:
                if (mViewModel.getOptions().getOrder() == "esc")
                    position = 3;
                else
                    position = 2;
                break;
            default:
                position = 0;
                break;
        }
        mBinding.orderSpinner.setSelection(position);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = Navigation.findNavController(view);
    }

    @Override
    public void onProductClicked(Product product) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_PRODUCT_ID, product.getId());
        bundle.putString(ARG_PRODUCT_NAME, product.getName());
        mNavController.navigate(R.id.action_productListFragment_to_productDetailsFragment, bundle);
    }

    @Override
    public void filterProductsCallback(Options options) {
        mViewModel.setOptions(options);
        setSpinnerPosition();
        mViewModel.setInitialData();
    }
}