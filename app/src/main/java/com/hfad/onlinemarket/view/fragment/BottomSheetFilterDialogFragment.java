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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.data.model.Options;
import com.hfad.onlinemarket.databinding.BottomFilterSheetBinding;

import static com.hfad.onlinemarket.view.fragment.ProductListFragment.ARGS_OPTIONS;
import static com.hfad.onlinemarket.view.fragment.ProductListFragment.ARGS_TITLE;

public class BottomSheetFilterDialogFragment extends BottomSheetDialogFragment {
    public static final String ARGS_FILTERED_OPTIONS = "filteroptions";
    public static final String TAG = "Bottom Sheet Filter";
    private BottomFilterSheetBinding mBinding;
    private Options mOptions;
    private NavController mNavController;


    public BottomSheetFilterDialogFragment() {

    }

    public static BottomSheetFilterDialogFragment getInstance() {
        BottomSheetFilterDialogFragment fragment = new BottomSheetFilterDialogFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.bottom_filter_sheet, container, false
        );

        mOptions = (Options) getArguments().getSerializable(ARGS_FILTERED_OPTIONS);
        initUI();
        setListeners();


        return mBinding.getRoot();
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
                    mBinding.editTextMaxPrice.setText(result + " تومان");
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
                    mBinding.editTextMinPrice.setText(result + " تومان");
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
//        mBinding.chipGroupFilters.Listener
        mBinding.dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mBinding.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ARGS_OPTIONS, mOptions);
                bundle.putString(ARGS_TITLE, "نتایج فیلتر شده");
                mNavController.navigate(R.id.action_bottomSheetFilterDialogFragment_to_productListFragment, bundle);
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
        return result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = NavHostFragment.findNavController(this);
    }


}
