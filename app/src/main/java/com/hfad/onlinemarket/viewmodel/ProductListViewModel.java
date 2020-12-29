package com.hfad.onlinemarket.viewmodel;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.onlinemarket.data.model.Options;
import com.hfad.onlinemarket.data.model.OrderBy;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.data.repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
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
                mOptions.setDescOrder();
                setInitialData();
                break;
            case 1:
                mRepository.sortProductsByTotalSales();
                break;
            case 2:
                mOptions.setOrderBy(OrderBy.price);
                mOptions.setAscOrder();
                setInitialData();
                break;
            case 3:
                mOptions.setOrderBy(OrderBy.price);
                mOptions.setDescOrder();
                setInitialData();
                break;
            default:
                return;
        }
    }

    //this method provides required data for filtering and set them to options
    public void setInitDataForFilters() {
        HashMap<String, String> tagsMap = new HashMap<>();
        String minPrice = "";
        String maxPrice = "";
        double minPriceDouble = Double.MAX_VALUE;
        double maxPriceDouble = 0;
        List<Product> fetchedList = getProductByOptions().getValue();
        for (int i = 0; i < fetchedList.size(); i++) {
            if (fetchedList.get(i).getTags() != null) {
                for (int j = 0; j < fetchedList.get(i).getTags().size(); j++) {
                    tagsMap.put(String.valueOf(fetchedList.get(i).getTags().get(j).getId())
                            , fetchedList.get(i).getTags().get(j).getName());
                }
            }
            if (!fetchedList.get(i).getRegularPrice().isEmpty()) {
                if (minPriceDouble > fetchedList.get(i).getDoublePrice()) {
                    minPrice = fetchedList.get(i).getUnformattedPrice();
                    minPriceDouble = fetchedList.get(i).getDoublePrice();
                    Log.d("mehrdad", "setInitDataForFilters: " + i);
                }
                if (maxPriceDouble < fetchedList.get(i).getDoublePrice()) {
                    maxPrice = fetchedList.get(i).getUnformattedPrice();
                    maxPriceDouble = fetchedList.get(i).getDoublePrice();
                }
            }
        }
        mOptions.setTags(tagsMap);
        mOptions.setMinPrice(minPrice);
        mOptions.setMaxPrice(maxPrice);
    }
}
