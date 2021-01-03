package com.hfad.onlinemarket.data.remote;

import android.util.Log;

import com.hfad.onlinemarket.data.model.Options;

import java.util.HashMap;
import java.util.Map;

public class NetworkParams {
    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String TAG = "Network params";



    public static Map<String, String> getProducts(int perPage, int page, String orderBy) {
        Map<String, String> queryOptions = new HashMap<>();
        queryOptions.put("per_page", String.valueOf(perPage));
        queryOptions.put("page", String.valueOf(page));
        queryOptions.put("orderby", orderBy);

        return queryOptions;
    }


    public static Map<String, String> getProductsByOptions(Options options, int perPage, int page) {
        Map<String, String> queryOptions = new HashMap<>();
        if (options == null) {
            return queryOptions;
        }
        queryOptions.put("per_page", String.valueOf(perPage));
        queryOptions.put("page", String.valueOf(page));
        queryOptions.put("orderby", options.getOrderBy().name());
        queryOptions.put("category", options.getCategoryId());
        queryOptions.put("search", options.getSearchQuery());
        queryOptions.put("order", options.getOrder());
        queryOptions.put("tag", options.getFilteredTagId());

        if (options.isOsSale())
            queryOptions.put("on_sale", "true");
        if (options.isInStock())
            queryOptions.put("stock_status", "instock");
        if (options.getMinPrice() != null)
            queryOptions.put("min_price", options.getMinPrice());
        if (options.getMaxPrice() != null)
            queryOptions.put("max_price", options.getMaxPrice());
        Log.d(TAG, "getProductsByOptions: " + options.toString());

        return queryOptions;
    }

    public static Map<String, String> getCustomer(String email) {
        Map<String, String> queryOptions = new HashMap<>();
        queryOptions.put("email", email);
        return queryOptions;
    }
}
