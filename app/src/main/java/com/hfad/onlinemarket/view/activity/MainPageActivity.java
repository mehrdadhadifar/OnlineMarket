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

public class MainPageActivity extends SingleFragmentActivity {
    private ActivityMainPageBinding mBinding;


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainPageActivity.class);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return MainPageFragment.newInstance();
    }

}