package com.hfad.onlinemarket.data.remote.retrofit;

import com.hfad.onlinemarket.data.model.product.Product;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import static com.hfad.onlinemarket.data.remote.NetworkParams.API_KEY;
import static com.hfad.onlinemarket.data.remote.NetworkParams.BASE_URL;

public interface WooCommerceAPI {

    @GET("products")
    Call<List<Product>> getProducts(@QueryMap Map<String, String> options);

    @GET("products")
    Call<List<Product>> getAllProducts();

    @GET("products")
    Call<List<Product>> getPopularProduct();

}
