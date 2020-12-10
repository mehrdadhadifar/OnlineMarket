package com.hfad.onlinemarket.utils;

import android.widget.ImageView;

import com.hfad.onlinemarket.R;
import com.squareup.picasso.Picasso;

public class LoadImagesPicasso {
    public static void loadImage(String src, ImageView target) {
        Picasso.get()
                .load(src)
                .placeholder(R.drawable.logo)
                .into(target);
    }
}
