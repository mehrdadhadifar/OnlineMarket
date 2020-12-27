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
import com.hfad.onlinemarket.adapters.DefaultCategoryAdapter;
import com.hfad.onlinemarket.adapters.SubCategoryAdapter;
import com.hfad.onlinemarket.data.model.Options;
import com.hfad.onlinemarket.data.model.product.Category;
import com.hfad.onlinemarket.databinding.FragmentCategoryBinding;
import com.hfad.onlinemarket.viewmodel.CategoriesViewModel;

import java.util.List;

import static com.hfad.onlinemarket.view.fragment.ProductListFragment.ARGS_TITLE;
import static com.hfad.onlinemarket.view.fragment.ProductListFragment.ARGS_OPTIONS;


public class CategoryFragment extends Fragment implements SubCategoryAdapter.OnCategoryListener {
    public static final String TAG = "Category Fragment";
    private FragmentCategoryBinding mBinding;
    private CategoriesViewModel mViewModel;
    private DefaultCategoryAdapter mDefaultCategoryAdapter;
    private NavController mNavController;


    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(CategoriesViewModel.class);
        mViewModel.setAllCategories();
        mDefaultCategoryAdapter = new DefaultCategoryAdapter(this, mViewModel,this);

        mViewModel.getDefaultCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                mDefaultCategoryAdapter.setItems(categories);
                mDefaultCategoryAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_category,
                container,
                false
        );
        mBinding.defaultCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.defaultCategoriesRecyclerView.setAdapter(mDefaultCategoryAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = Navigation.findNavController(view);
    }


    @Override
    public void onCategoryClicked(int categoryId,String categoryName) {
        Log.d(TAG, "onCategoryClicked: " + categoryId);
        Bundle bundle=new Bundle();
        Options options = new Options(categoryId);
        bundle.putSerializable(ARGS_OPTIONS, options);
        bundle.putString(ARGS_TITLE,categoryName);
        mNavController.navigate(R.id.action_categoryFragment_to_productListFragment, bundle);
    }
}