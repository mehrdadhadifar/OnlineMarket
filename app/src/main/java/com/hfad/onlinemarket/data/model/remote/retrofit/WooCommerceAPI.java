package com.hfad.onlinemarket.data.model.remote.retrofit;

import com.hfad.onlinemarket.data.model.product.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface WooCommerceAPI {


    @GET("." + "products")
    Observable<List<Product>> getProducts(@QueryMap Map<String, String> options);
}
