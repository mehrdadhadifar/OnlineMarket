package com.hfad.onlinemarket.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.data.model.product.Category;
import com.hfad.onlinemarket.databinding.ListItemDefaultCategoryBinding;
import com.hfad.onlinemarket.viewmodel.CategoriesViewModel;

import java.util.ArrayList;
import java.util.List;

public class DefaultCategoryAdapter extends RecyclerView.Adapter<DefaultCategoryAdapter.DefaultCategoryHolder> {
    private static final String TAG = "Defaul Category Adapter";
    private List<Category> mItems;
    private CategoriesViewModel mCategoriesViewModel;
    private LifecycleOwner mOwner;
    private SubCategoryAdapter.OnCategoryListener mCategoryListener;


    public List<Category> getItems() {
        return mItems;
    }

    public void setItems(List<Category> items) {
        mItems = items;
    }

    public DefaultCategoryAdapter(LifecycleOwner owner, CategoriesViewModel viewModel, SubCategoryAdapter.OnCategoryListener listener) {
        mItems = new ArrayList<>();
        mOwner = owner;
        mCategoriesViewModel = viewModel;
        mCategoryListener = listener;
    }

    @NonNull
    @Override
    public DefaultCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemDefaultCategoryBinding listItemDefaultCategoryBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_default_category,
                parent,
                false
        );
        return new DefaultCategoryHolder(listItemDefaultCategoryBinding, mCategoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultCategoryHolder holder, int position) {
        holder.bindDefaultCategory(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class DefaultCategoryHolder extends RecyclerView.ViewHolder {
        ListItemDefaultCategoryBinding mBinding;
        public SubCategoryAdapter mSubCategoryAdapter;

        public DefaultCategoryHolder(ListItemDefaultCategoryBinding listItemDefaultCategoryBinding, SubCategoryAdapter.OnCategoryListener listener) {
            super(listItemDefaultCategoryBinding.getRoot());
            mBinding = listItemDefaultCategoryBinding;
            mBinding.setListener(listener);
            mBinding.subCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(mCategoriesViewModel.getApplication(), RecyclerView.HORIZONTAL, true));
            mSubCategoryAdapter = new SubCategoryAdapter(mCategoryListener);
            mBinding.subCategoryRecyclerView.setAdapter(mSubCategoryAdapter);
        }

        public void bindDefaultCategory(Category category) {
            mBinding.setCategory(category);
            mBinding.executePendingBindings();
            Log.d(TAG, "bindDefaultCategory: " + category.getCount());
            List<Category> subCategories = new ArrayList<>();
            mCategoriesViewModel.getSubCategories().observe(mOwner,
                    new Observer<List<Category>>() {
                        @Override
                        public void onChanged(List<Category> categories) {
                            for (int i = 0; i < categories.size(); i++) {
                                if (categories.get(i).getParent() == category.getId())
                                    subCategories.add(categories.get(i));
                            }
                            if (subCategories.size() == 0)
                                subCategories.add(category);
                            mSubCategoryAdapter.setItems(subCategories);
                            mSubCategoryAdapter.notifyDataSetChanged();
                        }
                    });
        }
    }
}
