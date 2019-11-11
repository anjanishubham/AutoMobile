package com.lovelycoding.automobile.ui.dashboard.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lovelycoding.automobile.databinding.DashboardItemBinding;
import com.lovelycoding.automobile.ui.dashboard.callback.DashboardCallback;

public class DashboardItemViewHolder extends RecyclerView.ViewHolder {
    DashboardItemBinding itemBinding;
    DashboardItemHandler itemHandler;
    DashboardCallback mCallback;
    public DashboardItemViewHolder(@NonNull  DashboardItemBinding itemBinding, final DashboardCallback mCallback) {
        super(itemBinding.getRoot());
        itemHandler=new DashboardItemHandler();
        this.mCallback=mCallback;
        this.itemBinding=itemBinding;
        itemBinding.setItemHandler(itemHandler);

    }
}
