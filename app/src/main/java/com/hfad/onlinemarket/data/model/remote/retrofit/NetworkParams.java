package com.hfad.onlinemarket.data.model.remote.retrofit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Query;

public class NetworkParams {
    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONSUMER_KEY = "ck_c861fcab5b71d1251a46ae06f976b58e142084db";
    public static final String CONSUMER_SECRET = "cs_a0c53a079b4416512c5bc6db7cb311b640f75cde";

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

}
