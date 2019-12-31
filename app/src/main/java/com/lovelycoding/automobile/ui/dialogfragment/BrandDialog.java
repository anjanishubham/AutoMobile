package com.lovelycoding.automobile.ui.dialogfragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lovelycoding.automobile.R;
import com.lovelycoding.automobile.databinding.BrandDialogFragmentLayoutBinding;
import com.lovelycoding.automobile.databinding.BrandFragmentBinding;
import com.lovelycoding.automobile.ui.category.adapter.CategoryAdapter;
import com.lovelycoding.automobile.ui.home.brand.adapter.BrandAdapter;
import com.lovelycoding.automobile.ui.home.brand.adapter.GridSpacingItemDecoration;

public class BrandDialog extends DialogFragment {

    BrandDialogFragmentLayoutBinding dataBinding;
    CategoryAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.brand_dialog_fragment_layout, container, false);
        mAdapter = new CategoryAdapter();

        initRecycleView();
        return dataBinding.getRoot();
    }

    private void initRecycleView() {
        dataBinding.brandDialogFragmentRecycleView.setLayoutManager( new GridLayoutManager(getContext(),4));
        dataBinding.brandDialogFragmentRecycleView.addItemDecoration(new GridSpacingItemDecoration(4, dpToPx(10), false));
        dataBinding.brandDialogFragmentRecycleView.setNestedScrollingEnabled(false);
        dataBinding.brandDialogFragmentRecycleView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
