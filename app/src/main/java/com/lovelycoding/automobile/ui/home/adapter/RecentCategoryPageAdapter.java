package com.lovelycoding.automobile.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.lovelycoding.automobile.R;
import com.lovelycoding.automobile.datamodel.BrandRvItem;
import com.lovelycoding.automobile.datamodel.PcategoryRvItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecentCategoryPageAdapter extends PagerAdapter {
    private List<PcategoryRvItem> mCategoryList;

    public RecentCategoryPageAdapter() {
        mCategoryList = new ArrayList<>();
    }

    public void setRecentCategoryList(List<PcategoryRvItem> mCategoryList) {
        this.mCategoryList = mCategoryList;
    }


    @Override
    public int getCount() {
        return mCategoryList.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = LayoutInflater.from(container.getContext());
        View view = layoutInflater.inflate(R.layout.recent_view_item, container, false);
        AppCompatImageView imageView = view.findViewById(R.id.iv_select);
        AppCompatTextView textView = view.findViewById(R.id.tv_recent_item_name);
        Picasso.with(container.getContext()).load(mCategoryList.get(position).getCategoryItemUrl()).into(imageView);
        textView.setText(mCategoryList.get(position).getCategoryItemName());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
