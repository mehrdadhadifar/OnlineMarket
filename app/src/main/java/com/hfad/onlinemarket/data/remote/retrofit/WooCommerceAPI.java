package com.hfad.onlinemarket.data.remote.retrofit;

import com.hfad.onlinemarket.data.model.coupon.Coupon;
import com.hfad.onlinemarket.data.model.customer.Customer;
import com.hfad.onlinemarket.data.model.order.Order;
import com.hfad.onlinemarket.data.model.product.Category;
import com.hfad.onlinemarket.data.model.product.Product;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;

public interface WooCommerceAPI {

    @GET("products")
    Call<List<Product>> getProducts(@QueryMap Map<String, String> options);

    @GET("products")
    Call<List<Product>> getAllProducts();

    @GET("products/{productId}")
    Call<Product> getProductById(@Path("productId") int productId);

    @GET("products/categories?per_page=100")
    Call<List<Category>> getCategories(@QueryMap Map<String, String> options);

    @GET("customers")
    Call<List<Customer>> getCustomers(@QueryMap Map<String, String> options);

    @POST("customers")
    Call<Customer> postCustomers(@QueryMap Map<String, String> options, @Body Customer customer);

    @POST("orders")
    Call<Order> postOrder(@Body Order order);

    @GET("coupons")
    Call<List<Coupon>> getCoupons(@QueryMap Map<String, String> options);

}
