package com.hfad.onlinemarket.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.data.model.Options;
import com.hfad.onlinemarket.databinding.BottomFilterSheetBinding;

import java.io.Serializable;

public class BottomSheetFilterDialogFragment extends BottomSheetDialogFragment {
    public static final String ARGS_FILTERED_OPTIONS = "filteroptions";
    public static final String TAG = "Bottom Sheet Filter";
    public static final String ARGS_CALLBACK = "callback";
    private BottomFilterSheetBinding mBinding;
    private Options mOptions;
    private FilterCallback mCallback;


    public BottomSheetFilterDialogFragment() {
    }

    public static BottomSheetFilterDialogFragment getInstance() {
        return new BottomSheetFilterDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.bottom_filter_sheet, container, false
        );

        getInitData();
        initUI();
        setListeners();

        return mBinding.getRoot();
    }

    private void getInitData() {
        mOptions = (Options) getArguments().getSerializable(ARGS_FILTERED_OPTIONS);
        try {
            mCallback = (FilterCallback) getArguments().getSerializable(ARGS_CALLBACK);
        } catch (ClassCastException e) {
            throw new ClassCastException("the callback is sent to this fragment should implement FilterCallback interface");
        }
    }

    private void setListeners() {
        mBinding.editTextMaxPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    mBinding.editTextMaxPrice.setText("");
                else {
                    mOptions.setMaxPrice(mBinding.editTextMaxPrice.getText().toString());
                    String price = mBinding.editTextMaxPrice.getText().toString();
                    String result = priceFormatter(price);
                    mBinding.editTextMaxPrice.setText(result);
                }
            }
        });
        mBinding.editTextMinPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    mBinding.editTextMinPrice.setText("");
                else {
                    mOptions.setMinPrice(mBinding.editTextMinPrice.getText().toString());
                    String price = mBinding.editTextMinPrice.getText().toString();
                    String result = priceFormatter(price);
                    mBinding.editTextMinPrice.setText(result);
                }
            }
        });
        mBinding.onSaleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mOptions.setOsSale(isChecked);
            }
        });
        mBinding.inStockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mOptions.setInStock(isChecked);
            }
        });
        mBinding.dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mBinding.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.filterProductsCallback(mOptions);
                dismiss();
            }
        });
    }

    private void initUI() {
        mBinding.onSaleSwitch.setChecked(mOptions.isOsSale());
        mBinding.inStockSwitch.setChecked(mOptions.isInStock());

        mBinding.editTextMinPrice.setText(priceFormatter(mOptions.getMinPrice()));
        mBinding.editTextMaxPrice.setText(priceFormatter(mOptions.getMaxPrice()));

        for (int i = 0; i < mOptions.getTagsName().size(); i++) {
            Chip chip = new Chip(getActivity());
            chip.setText(mOptions.getTagsName().get(i));
            chip.setCheckable(true);
            chip.setChipBackgroundColorResource(R.color.logo);
            chip.setTextColor(getResources().getColor(R.color.white));
            int finalI = i;
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOptions.setFilteredTagId(mOptions.getTagsId().get(finalI));
                    Log.d(TAG, "onClick: tag Id: " + mOptions.getTagsId().get(finalI));
                }
            });
            mBinding.chipGroupFilters.addView(chip, i);
        }
    }

    private String priceFormatter(String price) {
        String result = "";
        while (price.length() > 3) {
            result = "," + price.substring(price.length() - 3).concat(result);
            price = price.substring(0, price.length() - 3);
        }
        result = price.concat(result);
        return result+" تومن";
    }


    public interface FilterCallback extends Serializable {
        void filterProductsCallback(Options options);
    }

}
