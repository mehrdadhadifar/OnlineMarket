package com.hfad.onlinemarket.data.remote.retrofit;

import com.hfad.onlinemarket.data.model.coupon.Coupon;
import com.hfad.onlinemarket.data.model.customer.Customer;
import com.hfad.onlinemarket.data.model.order.Order;
import com.hfad.onlinemarket.data.model.product.Category;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.data.model.review.Review;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

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

    @GET("products/reviews")
    Call<List<Review>> getReviews(@QueryMap Map<String, String> options);

    @DELETE("products/reviews/{reviewId}")
    Call<Review> deleteReview(@Path("reviewId") int reviewId);

    @GET("products/reviews/{reviewId}")
    Call<Review> getReview(@Path("reviewId") int reviewId);

    @PATCH("products/reviews/{reviewId}")
    Call<Review> updateReview(@Path("reviewId") int reviewId, @Body Review review);

    @POST("products/reviews")
    Call<Review> postReviews(@Body Review review);


}
