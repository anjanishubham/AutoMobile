package com.lovelycoding.automobile.ui.home.brand.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lovelycoding.automobile.databinding.BrandRvItemBinding;

public class BrandViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private BrandListener mListener;
    public  BrandRvItemBinding dataBinding;
    public BrandViewHolder(@NonNull BrandRvItemBinding itemView,BrandListener mListener) {
        super(itemView.getRoot());
        dataBinding=itemView;
        this.mListener=mListener;
        dataBinding.getRoot().setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

            dataBinding.checkbox.setVisibility(View.VISIBLE);
            mListener.itemClicked(getAdapterPosition());
    }
}
