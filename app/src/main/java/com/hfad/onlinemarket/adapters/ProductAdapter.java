package com.hfad.onlinemarket.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.databinding.ListItemProductBinding;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private Context mContext;
    private List<Product> mItems;

    public List<Product> getItems() {
        return mItems;
    }

    public void setItems(List<Product> items) {
        mItems = items;
    }

    public ProductAdapter(Context context, List<Product> items) {
        mContext = context;
        mItems = items;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemProductBinding listItemProductBinding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.list_item_product,
                parent,
                false);

        return new ProductHolder(listItemProductBinding);
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
        private Product mProduct;

        public ProductHolder(ListItemProductBinding listItemProductBinding) {
            super(listItemProductBinding.getRoot());
            mListItemProductBinding = listItemProductBinding;
        }

        public void bindProduct(Product product) {
            mProduct = product;
            mListItemProductBinding.productTitle.setText(product.getName());
        }
    }
}
