package com.lovelycoding.automobile.ui.home.category.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.lovelycoding.automobile.databinding.CategoryRvItemBinding;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    CategoryListener mListener;
    CategoryRvItemBinding dataBinding;
    public CategoryViewHolder(CategoryRvItemBinding itemView,CategoryListener mListener) {
        super(itemView.getRoot());
        dataBinding=itemView;
        this.mListener=mListener;
        dataBinding.getRoot().setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mListener.getCategoryPosition(getAdapterPosition());
    }
}
