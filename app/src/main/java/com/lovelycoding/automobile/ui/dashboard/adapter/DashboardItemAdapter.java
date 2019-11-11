package com.lovelycoding.automobile.ui.dashboard.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lovelycoding.automobile.R;
import com.lovelycoding.automobile.databinding.DashboardItemBinding;
import com.lovelycoding.automobile.datamodel.Product;
import com.lovelycoding.automobile.ui.dashboard.callback.DashboardCallback;

import java.util.ArrayList;
import java.util.List;

public class DashboardItemAdapter extends RecyclerView.Adapter<DashboardItemViewHolder> {
    private static final String TAG = "DashboardItemAdapter";
    List<Product> mProductList;
    DashboardCallback mCallback;

    public DashboardItemAdapter(DashboardCallback mCallback) {
        mProductList = new ArrayList<>();
        this.mCallback=mCallback;
    }

    @NonNull
    @Override
    public DashboardItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        DashboardItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.dashboard_item, parent,false);

        return new DashboardItemViewHolder(itemBinding,mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardItemViewHolder holder, int position) {

        ((DashboardItemViewHolder)holder).itemBinding.setProduct(mProductList.get(position));
        Log.d(TAG, "onBindViewHolder: "+mProductList.get(position));
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public void setProductList(List<Product> mProductList) {
        this.mProductList=mProductList;
    }
}
