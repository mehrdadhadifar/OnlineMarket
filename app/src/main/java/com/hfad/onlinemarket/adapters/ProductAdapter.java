package com.hfad.onlinemarket.adapters;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.databinding.ListItemProductBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    public static final String TAG = "Product Adapter";
    private List<Product> mItems;
    private OnProductListener mProductListener;

    public List<Product> getItems() {
        return mItems;
    }

    public void setItems(List<Product> items) {
        mItems = items;
    }

    public ProductAdapter(OnProductListener listener) {
        mItems = new ArrayList<>();
        mProductListener = listener;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemProductBinding listItemProductBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_product,
                parent,
                false);

        return new ProductHolder(listItemProductBinding, mProductListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.bindProduct(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        private ListItemProductBinding mListItemProductBinding;

        public ProductHolder(ListItemProductBinding listItemProductBinding, OnProductListener listener) {
            super(listItemProductBinding.getRoot());
            mListItemProductBinding = listItemProductBinding;
            mListItemProductBinding.productRegularPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            mListItemProductBinding.setListener(listener);
        }

        public void bindProduct(Product product) {
            mListItemProductBinding.setProduct(product);
            mListItemProductBinding.executePendingBindings();
            Picasso.get()
                    .load(product.getFeaturedImageUrl())
                    .placeholder(R.drawable.logo)
                    .into(mListItemProductBinding.productImage);
//            mListItemProductBinding.productTitle.setText(product.getName());
        }
    }

    public interface OnProductListener {
        public void onProductClicked(Product product);
    }
}
