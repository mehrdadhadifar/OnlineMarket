package com.hfad.onlinemarket.data.repository;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hfad.onlinemarket.data.model.Options;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.data.remote.NetworkParams;
import com.hfad.onlinemarket.data.remote.retrofit.RetrofitInstance;
import com.hfad.onlinemarket.data.remote.retrofit.WooCommerceAPI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    public static final String TAG = "Product Repository";
    private static ProductRepository sInstance;

    private final MutableLiveData<List<Product>> mAllProductsLiveData;
    private final MutableLiveData<List<Product>> mOnSaleProductsLiveData;
    private final MutableLiveData<List<Product>> mLatestProductsLiveData;
    private final MutableLiveData<List<Product>> mTopRatedProductsLiveData;
    private final MutableLiveData<List<Product>> mPopularProductsLiveData;
    private final MutableLiveData<List<Product>> mProductByOptionsLiveData;

    private final MutableLiveData<Product> mSelectedProductLiveData;


    private WooCommerceAPI mWooCommerceAPI;


    public static synchronized ProductRepository getInstance() {
        if (sInstance == null)
            sInstance = new ProductRepository();
        return sInstance;
    }

    private ProductRepository() {
        mWooCommerceAPI = RetrofitInstance.getInstance().create(WooCommerceAPI.class);

        mAllProductsLiveData = new MutableLiveData<>();
        mOnSaleProductsLiveData = new MutableLiveData<>();
        mLatestProductsLiveData = new MutableLiveData<>();
        mTopRatedProductsLiveData = new MutableLiveData<>();
        mPopularProductsLiveData = new MutableLiveData<>();
        mSelectedProductLiveData = new MutableLiveData<Product>();
        mProductByOptionsLiveData = new MutableLiveData<>();

    }

    public MutableLiveData<List<Product>> getAllProductsLiveData() {
        return mAllProductsLiveData;
    }

    public MutableLiveData<List<Product>> getOnSaleProductsLiveData() {
        return mOnSaleProductsLiveData;
    }

    public MutableLiveData<List<Product>> getLatestProductsLiveData() {
        return mLatestProductsLiveData;
    }

    public MutableLiveData<List<Product>> getTopRatedProductsLiveData() {
        return mTopRatedProductsLiveData;
    }

    public MutableLiveData<List<Product>> getPopularProductsLiveData() {
        return mPopularProductsLiveData;
    }

    public LiveData<Product> getSelectedProductLiveData() {
        return mSelectedProductLiveData;
    }

    public MutableLiveData<List<Product>> getProductByOptionsLiveData() {
        return mProductByOptionsLiveData;
    }

    public void setAllProductsLiveData() {
        mWooCommerceAPI.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                mAllProductsLiveData.setValue(response.body());
                Log.d(TAG, "onResponse: " + response.body().size());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    public void setLatestProductsLiveData() {
        Log.d(TAG, "request for latest data");
        mWooCommerceAPI.getProducts(NetworkParams.getProducts(100, 1, "date"))
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        mLatestProductsLiveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {

                    }
                });
    }

    public void setPopularProductsLiveData() {
        mWooCommerceAPI.getProducts(NetworkParams.getProducts(100, 1, "popularity"))
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        mPopularProductsLiveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {

                    }
                });
    }

    public void setTopRatedProductsLiveData() {
        mWooCommerceAPI.getProducts(NetworkParams.getProducts(100, 1, "rating"))
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        mTopRatedProductsLiveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {

                    }
                });
    }

    public void setOnSaleProductsLiveData(Options options) {
        mWooCommerceAPI.getProducts(NetworkParams.getProductsByOptions(options, 100, 1))
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        mOnSaleProductsLiveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {

                    }
                });
    }

    public void setSelectedProductLiveData(int productId) {
        Log.d(TAG, "setSelectedProductLiveData: " + productId);
        mWooCommerceAPI.getProductById(productId)
                .enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        mSelectedProductLiveData.setValue(response.body());
                        Log.d(TAG, "onResponse: " + response.body().getName());
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {

                    }
                });
    }


    public void setProductByOptionsLiveData(Options options) {
        Log.d(TAG, "setProductByOptionsLiveData: " + options);
        mWooCommerceAPI.getProducts(NetworkParams.getProductsByOptions(options, 100, 1))
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        mProductByOptionsLiveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {

                    }
                });
    }
    public Call<List<Product>> getLatestProduct(){
        return mWooCommerceAPI.getAllProducts();
    }

    //This method sorts mProductByOptionsLiveData base on total sales
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortProductsByTotalSales() {
        List<Product> sortedList = new ArrayList<>();
        sortedList.addAll(mProductByOptionsLiveData.getValue());
        sortedList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getTotalSales() < o2.getTotalSales() ? 1 : -1;
            }
        });
        mProductByOptionsLiveData.setValue(sortedList);
    }


}
