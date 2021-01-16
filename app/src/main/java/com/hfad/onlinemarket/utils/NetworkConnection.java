package com.hfad.onlinemarket.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class NetworkConnection {
    private MutableLiveData<Boolean> mNetworkStateLiveData;
    private Context mContext;
    private ConnectivityManager mConnectivityManager;
    private ConnectivityManager.NetworkCallback mNetworkCallback;


    public NetworkConnection(Context context) {
        mContext = context;
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        mNetworkCallback=new ConnectivityManager.NetworkCallback();
        mNetworkStateLiveData = new MutableLiveData<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onActive() {
        updateConnection();
        connectivityManagerCallback();
        mContext.registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void onInactive() {
        mContext.unregisterReceiver(networkReceiver);
    }

    public LiveData<Boolean> getNetworkState() {
        return mNetworkStateLiveData;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void connectivityManagerCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mConnectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    mNetworkStateLiveData.postValue(true);
                }

                @Override
                public void onLost(Network network) {
                    mNetworkStateLiveData.postValue(false);
                }
            });
        }
    }

    public void updateConnection() {
        NetworkInfo activeNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null)
            mNetworkStateLiveData.postValue(activeNetworkInfo.isConnected());
    }

    public BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateConnection();
        }
    };

    public boolean isNetworkConnected() {
        if (mNetworkStateLiveData.getValue() == null || !mNetworkStateLiveData.getValue())
            return false;
        return true;
    }
}