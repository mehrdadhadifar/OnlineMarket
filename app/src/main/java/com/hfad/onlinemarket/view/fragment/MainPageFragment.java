package com.hfad.onlinemarket.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.adapters.ProductAdapter;
import com.hfad.onlinemarket.data.model.product.Product;
import com.hfad.onlinemarket.data.model.remote.retrofit.NetworkParams;
import com.hfad.onlinemarket.data.model.remote.retrofit.RetrofitInstance;
import com.hfad.onlinemarket.data.model.remote.retrofit.WooCommerceAPI;
import com.hfad.onlinemarket.databinding.FragmentMainPageBinding;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class MainPageFragment extends Fragment {
    FragmentMainPageBinding mBinding;
    WooCommerceAPI mWooCommerceAPI;
    CompositeDisposable mDisposable;
    private final MutableLiveData<List<Product>> mLatestProductsLiveData = new MutableLiveData<>();


    public MainPageFragment() {
        // Required empty public constructor
    }

    public static MainPageFragment newInstance() {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit retrofit = RetrofitInstance.getInstance();
        mWooCommerceAPI = retrofit.create(WooCommerceAPI.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_main_page,
                container,
                false);

        mBinding.newProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


/*        mWooCommerceAPI.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    mLatestProductsLiveData.setValue(response.body());
                    ProductAdapter adapter = new ProductAdapter(getActivity(), mLatestProductsLiveData.getValue());
                    mBinding.newProductsRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });*/


/*        mWooCommerceAPI.getAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Product>>() {
                    @Override
                    public void accept(List<Product> products) {
                        ProductAdapter adapter = new ProductAdapter(getActivity(), products);
                        mBinding.newProductsRecyclerView.setAdapter(adapter);
                    }
                });*/

        mWooCommerceAPI.getProducts(NetworkParams.getProducts(30,2,"date"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Product>>() {
                    @Override
                    public void accept(List<Product> products) {
                        ProductAdapter adapter = new ProductAdapter(getActivity(), products);
                        mBinding.newProductsRecyclerView.setAdapter(adapter);
                    }
                });
        return mBinding.getRoot();
    }
}