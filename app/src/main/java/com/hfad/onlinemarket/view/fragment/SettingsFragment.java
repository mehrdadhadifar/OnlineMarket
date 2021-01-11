package com.hfad.onlinemarket.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.databinding.FragmentSettingsBinding;
import com.hfad.onlinemarket.viewmodel.SettingViewModel;

import static com.hfad.onlinemarket.utils.SnakeBar.showAddSnakeBar;

public class SettingsFragment extends Fragment {
    public static final String TAG = "SettingFragment";
    public static final int REQUEST_CODE_TIME_PICKER = 0;
    public static final String DIALOG_FRAGMENT_TAG = "Dialog";
    public static final String EXTRA_DELAY_EXACT_NOTIFICATION = "com.hfad.onlinemarket.delayTime";

    private FragmentSettingsBinding mBinding;
    private SettingViewModel mViewModel;


    public SettingsFragment() {
        // Required empty public constructor
    }


    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingViewModel.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        mBinding.setViewModel(mViewModel);
        mBinding.setSettingFragment(this);

        return mBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onPause() {
        super.onPause();
        if (mViewModel.setSettings()) {
            Snackbar snackbar = Snackbar.make(mBinding.getRoot(), R.string.setting_updatd, BaseTransientBottomBar.LENGTH_LONG);
            showAddSnakeBar(snackbar, getActivity());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_TIME_PICKER) {
            Log.d(TAG, "onActivityResult: " + data.getLongExtra(EXTRA_DELAY_EXACT_NOTIFICATION, 0));
            mViewModel.setDelayMilliSecond(data.getLongExtra(EXTRA_DELAY_EXACT_NOTIFICATION, 0));
        }
    }
}