package com.hfad.onlinemarket.utils;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.google.android.material.snackbar.Snackbar;
import com.hfad.onlinemarket.R;

public class SnakeBar {
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void showAddSnakeBar(Snackbar snackbar, Context context) {
        snackbar.getView().setForegroundGravity(View.TEXT_ALIGNMENT_CENTER);
        snackbar.getView().setMinimumHeight(200);
        TextView snackBarTextView = (TextView) snackbar.getView().findViewById(R.id.snackbar_text);
        snackBarTextView.setTextSize(30);
        snackBarTextView.setTextColor(context.getResources().getColor(R.color.logo));
        snackbar.show();
    }
}
