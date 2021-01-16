package com.hfad.onlinemarket.view.activity;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.databinding.ActivitySingleFragmentBinding;
import com.hfad.onlinemarket.utils.NetworkConnection;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    public static final String TAG = "Single FragmentActivity";
    public static final String FRAGMENT_TAG = "Single Fragment Activity";

    public ActivitySingleFragmentBinding mBinding;
    private NavController mNavController;
    private AppBarConfiguration mAppBarConfiguration;
    NetworkConnection mNetworkConnection;


    public abstract Fragment createFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_OnlineMarket);
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_single_fragment);
        mNetworkConnection = new NetworkConnection(getApplicationContext());
        mBinding.setNetworkConnection(mNetworkConnection);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentById(R.id.nav_host_fragment);
        if (fragment == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.nav_host_fragment, createFragment(), FRAGMENT_TAG)
                    .commit();
        }

        //Set bottom navigation
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(mBinding.bottomNavigation, mNavController);
        //Set navigation up button
        mAppBarConfiguration = new AppBarConfiguration.Builder(mNavController.getGraph())
                .setOpenableLayout(mBinding.drawerLayout)
//                .setDrawerLayout(mBinding.drawerLayout)
                .build();
        NavigationUI.setupActionBarWithNavController(this,
                mNavController,
                mBinding.drawerLayout);
        //set drawer navigation
        NavigationUI.setupWithNavController(mBinding.slideNavigation, mNavController);

        //check Internet connectivity
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mNetworkConnection.onActive();
        }
        mNetworkConnection.getNetworkState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                mBinding.setNetworkConnection(mNetworkConnection);
                Log.d(TAG, "onChanged: " + aBoolean);
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(mNavController, mAppBarConfiguration);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}