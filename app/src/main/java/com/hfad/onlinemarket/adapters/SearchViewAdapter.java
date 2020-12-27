package com.hfad.onlinemarket.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.databinding.ListItemSearchedProductsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchViewAdapter extends RecyclerView.Adapter<SearchViewAdapter.ProductHolder>{
    public static final String TAG = "Search View Adapter";
    private List<Product> mItems;
    private SearchViewAdapter.OnProductListener mProductListener;

    public List<Product> getItems() {
        return mItems;
    }

    public void setItems(List<Product> items) {
        mItems = items;
    }

    public SearchViewAdapter(SearchViewAdapter.OnProductListener listener) {
        mItems = new ArrayList<>();
        mProductListener = listener;
    }

    @NonNull
    @Override
    public SearchViewAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemSearchedProductsBinding listItemSearchedProductsBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_searched_products,
                parent,
                false);

        return new SearchViewAdapter.ProductHolder(listItemSearchedProductsBinding, mProductListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewAdapter.ProductHolder holder, int position) {
        holder.bindProduct(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        private ListItemSearchedProductsBinding mListItemSearchedProductsBinding;

        public ProductHolder(ListItemSearchedProductsBinding listItemSearchedProductsBinding, SearchViewAdapter.OnProductListener listener) {
            super(listItemSearchedProductsBinding.getRoot());
            mListItemSearchedProductsBinding = listItemSearchedProductsBinding;
            mListItemSearchedProductsBinding.setListener(listener);
        }

        public void bindProduct(Product product) {
            mListItemSearchedProductsBinding.setProduct(product);
            mListItemSearchedProductsBinding.executePendingBindings();
            Picasso.get()
                    .load(product.getFeaturedImageUrl())
                    .placeholder(R.drawable.logo)
                    .into(mListItemSearchedProductsBinding.searchItemImageView);
//            mListItemProductBinding.productTitle.setText(product.getName());
        }
    }

    public interface OnProductListener {
        public void onProductClicked(Product product);
    }
}
