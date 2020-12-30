package com.hfad.onlinemarket.utils;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

public class SliderImageDecorator {
    public static void SliderImageDecorator(ViewPager2 viewPager2){
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(1);
        viewPager2.setPadding(128, 0, 128, 0);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(transformer);
        Handler sliderHandler = new Handler(Looper.getMainLooper());
        Runnable slideRunnable = new Runnable() {
            @Override
            public void run() {
                if (viewPager2.getCurrentItem() == viewPager2.getAdapter().getItemCount() - 1)
                    viewPager2.setCurrentItem(0);
                else
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        };
        sliderHandler.postDelayed(slideRunnable, 3000);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(slideRunnable);
                sliderHandler.postDelayed(slideRunnable, 3000);
            }
        });

    }
}
