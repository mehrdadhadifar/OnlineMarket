package com.hfad.onlinemarket.data.model.remote.retrofit;

import com.hfad.onlinemarket.data.model.product.Product;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import static com.hfad.onlinemarket.data.model.remote.retrofit.NetworkParams.API_KEY;
import static com.hfad.onlinemarket.data.model.remote.retrofit.NetworkParams.BASE_URL;

public interface WooCommerceAPI {

    @GET(BASE_URL + "products" +API_KEY)
    Observable<List<Product>> getProducts(@QueryMap Map<String, String> options);

    @GET(BASE_URL + "products" + API_KEY)
    Observable<List<Product>> getAllProducts();

}
