package com.hfad.onlinemarket.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.databinding.ActivityMainPageBinding;
import com.hfad.onlinemarket.databinding.FragmentMainPageBinding;
import com.hfad.onlinemarket.view.fragment.MainPageFragment;

public class MainPageActivity extends AppCompatActivity {
    private ActivityMainPageBinding mBinding;
    private NavController mNavController;


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainPageActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.Theme_OnlineMarket);
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_main_page);

        mNavController= Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(mBinding.bottomNavigation,mNavController);



    }
}