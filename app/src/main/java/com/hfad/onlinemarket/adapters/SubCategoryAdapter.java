package com.hfad.onlinemarket.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.data.model.product.Category;
import com.hfad.onlinemarket.databinding.ListItemSubCategoryBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryHolder> {
    private static final String TAG = "Sub Category Adapter";
    private List<Category> mItems;
    private OnCategoryListener mCategoryListener;

    public List<Category> getItems() {
        return mItems;
    }

    public void setItems(List<Category> items) {
        mItems = items;
    }

    public SubCategoryAdapter(OnCategoryListener listener) {
        mItems = new ArrayList<>();
        mCategoryListener=listener;
    }

    @NonNull
    @Override
    public SubCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemSubCategoryBinding listItemSubCategoryBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_sub_category,
                parent,
                false
        );
        return new SubCategoryHolder(listItemSubCategoryBinding,mCategoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryHolder holder, int position) {
        holder.bindSubCategory(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class SubCategoryHolder extends RecyclerView.ViewHolder {
        private ListItemSubCategoryBinding mBinding;

        public SubCategoryHolder(ListItemSubCategoryBinding listItemSubCategoryBinding,OnCategoryListener listener) {
            super(listItemSubCategoryBinding.getRoot());
            mBinding = listItemSubCategoryBinding;
            mBinding.setListener(listener);
        }

        public void bindSubCategory(Category category) {
            mBinding.setCategory(category);
            mBinding.executePendingBindings();
            Picasso.get()
                    .load(category.getImage().getSrc())
                    .placeholder(R.drawable.logo)
                    .into(mBinding.categoryImage);
            Log.d(TAG, "bindSubCategory: " + category.getName());
        }
    }
    public interface OnCategoryListener {
        public void onCategoryClicked(int categoryId,String categoryName);
    }
}
