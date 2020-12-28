package com.hfad.onlinemarket.data.remote;

import com.hfad.onlinemarket.data.model.Options;

import java.util.HashMap;
import java.util.Map;

public class NetworkParams {
    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONSUMER_KEY = "ck_c861fcab5b71d1251a46ae06f976b58e142084db";
    public static final String CONSUMER_SECRET = "cs_a0c53a079b4416512c5bc6db7cb311b640f75cde";

    public static final String API_KEY = "?consumer_key=" +
            CONSUMER_KEY +
            "&consumer_secret=" +
            CONSUMER_SECRET;


    public static Map<String, String> BASE_OPTIONS = new HashMap<String, String>() {{
        put("consumer_key", CONSUMER_KEY);
        put("consumer_secret", CONSUMER_SECRET);
    }};


    public static Map<String, String> getProducts(int perPage, int page, String orderBy) {
        Map<String, String> queryOptions = new HashMap<>();
        queryOptions.putAll(BASE_OPTIONS);
        queryOptions.put("per_page", String.valueOf(perPage));
        queryOptions.put("page", String.valueOf(page));
        queryOptions.put("orderby", orderBy);

        return queryOptions;
    }


    public static Map<String, String> getProductsByOptions(Options options, int perPage, int page) {
        if (options == null) {
            return BASE_OPTIONS;
        }
        Map<String, String> queryOptions = new HashMap<>();
        queryOptions.putAll(BASE_OPTIONS);
        queryOptions.put("per_page", String.valueOf(perPage));
        queryOptions.put("page", String.valueOf(page));
        queryOptions.put("orderby", options.getOrderBy().name());
        queryOptions.put("category", options.getCategoryId());
        queryOptions.put("search", options.getSearchQuery());
        queryOptions.put("order",options.getOrder());
        return queryOptions;
    }

}
