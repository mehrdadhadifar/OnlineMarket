package com.hfad.onlinemarket.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.databinding.FragmentNotificationTimePickerBinding;
import com.hfad.onlinemarket.viewmodel.NotificationTimePickerViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NotificationTimePickerFragment extends DialogFragment {
    private FragmentNotificationTimePickerBinding mBinding;
    private NotificationTimePickerViewModel mViewModel;

    public static NotificationTimePickerFragment newInstance() {
        NotificationTimePickerFragment fragment = new NotificationTimePickerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NotificationTimePickerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NotificationTimePickerViewModel.class);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification_time_picker, null, false);
        return new AlertDialog.Builder(getActivity())
                .setTitle("زمان اعلان")
                .setIcon(R.drawable.ic_notification)
                .setView(mBinding.getRoot())
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    long milliSecondsLeft = getSecondsLeftToSelectedTime();
                    mViewModel.setResult(NotificationTimePickerFragment.this, milliSecondsLeft);
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private long getSecondsLeftToSelectedTime() {
        long result = 0;
        int hour = mBinding.notificationTimePicker.getHour();
        int minute = mBinding.notificationTimePicker.getMinute();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Log.d(SettingsFragment.TAG, "getSecondsLeftToSelectedTime: selected " + hour + ":" + minute);
        Log.d(SettingsFragment.TAG, "getSecondsLeftToSelectedTime: now " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        try {
            Date selectedDate = sdf.parse(hour + ":" + minute);
            Date currentDate = sdf.parse(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
            result = currentDate.getTime() - selectedDate.getTime();
            Log.d(SettingsFragment.TAG, "getSecondsLeftToSelectedTime: result " + result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Math.abs(result);
    }
}
