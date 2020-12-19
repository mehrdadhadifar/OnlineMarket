package com.hfad.onlinemarket.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.data.model.product.ImagesItem;
import com.hfad.onlinemarket.databinding.ImageSlideContainerBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.SliderViewHolder> {
    private List<ImagesItem> mSliderItems;

    public ImageSliderAdapter() {
        mSliderItems = new ArrayList<>();
    }

    public void setSliderItems(List<ImagesItem> sliderItems) {
        mSliderItems = sliderItems;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageSlideContainerBinding imageSlideContainerBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.image_slide_container,
                parent,
                false
        );
        return new SliderViewHolder(imageSlideContainerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(mSliderItems.get(position).getSrc());
    }

    @Override
    public int getItemCount() {
        return mSliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private ImageSlideContainerBinding mImageSlideContainerBinding;


        public SliderViewHolder(ImageSlideContainerBinding imageSlideContainerBinding) {
            super(imageSlideContainerBinding.getRoot());
            mImageSlideContainerBinding = imageSlideContainerBinding;
        }

        public void setImage(String src) {
            Picasso.get()
                    .load(src)
                    .placeholder(R.drawable.logo)
                    .into(mImageSlideContainerBinding.imageSlider);
        }
    }
}
