package com.hfad.onlinemarket.viewmodel;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.onlinemarket.data.model.Options;
import com.hfad.onlinemarket.data.model.OrderBy;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.data.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

//You have to setOptions before call any other methods.
public class ProductListViewModel extends ViewModel {
    private ProductRepository mRepository;
    private Options mOptions;
    private final List<String> orderList;

    public ProductListViewModel() {
        mRepository = ProductRepository.getInstance();
        orderList = new ArrayList<>();
        orderList.add("جدیدترین");
        orderList.add("پرفروش ترین");
        orderList.add("ارزان ترین");
        orderList.add("گران ترین");
    }


    public List<String> getOrderList() {
        return orderList;
    }

    public Options getOptions() {
        return mOptions;
    }

    public void setOptions(Options options) {
        mOptions = options;
    }

    public void setInitialData() {
        mRepository.setProductByOptionsLiveData(mOptions);
    }

    public LiveData<List<Product>> getProductByOptions() {
        return mRepository.getProductByOptionsLiveData();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setOrderedProducts(int position) {
        switch (position) {
            case 0:
                mOptions.setOrderBy(OrderBy.date);
                setInitialData();
                break;
            case 1:
                mRepository.sortProductsByTotalSales();
                break;
            case 2:
                mOptions.setOrderBy(OrderBy.price);
                mRepository.setProductByOptionsLiveData(mOptions.getAcsOrder());
                break;
            case 3:
                mOptions.setOrderBy(OrderBy.price);
                setInitialData();
                break;
            default:
                return;
        }
    }


}
