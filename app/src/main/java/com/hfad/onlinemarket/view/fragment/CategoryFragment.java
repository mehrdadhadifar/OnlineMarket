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
import com.hfad.onlinemarket.data.model.product.Category;
import com.hfad.onlinemarket.databinding.FragmentCategoryBinding;
import com.hfad.onlinemarket.viewmodel.CategoriesViewModel;

import java.util.List;


public class CategoryFragment extends Fragment {
    public static final String TAG = "Category Fragment";
    private FragmentCategoryBinding mBinding;
    private CategoriesViewModel mViewModel;


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
        mViewModel=new ViewModelProvider(requireActivity()).get(CategoriesViewModel.class);
        mViewModel.setAllCategories();

        mViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                for (int i = 0; i < categories.size() ; i++) {
//                    Log.d(TAG, "onChanged: "+ categories.get(i).getName());
                    Log.d(TAG, "onChanged: "+categories.get(i).toString());

                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_category,
                container,
                false
        );

        return mBinding.getRoot();
    }
}