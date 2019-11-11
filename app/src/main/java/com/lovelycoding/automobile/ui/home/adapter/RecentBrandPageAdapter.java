package com.lovelycoding.automobile.ui.home.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.lovelycoding.automobile.R;
import com.lovelycoding.automobile.datamodel.BrandRvItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecentBrandPageAdapter extends PagerAdapter {
    private List<BrandRvItem> mBrandList;

    public RecentBrandPageAdapter() {
        mBrandList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mBrandList.size();
    }

    public void setBrandList(List<BrandRvItem> mBrandList) {
        this.mBrandList=mBrandList;
        Log.d(TAG, "setBrandList: "+this.mBrandList);
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
        Picasso.with(container.getContext()).load(mBrandList.get(position).getBrandImageUrl()).into(imageView);
        textView.setText(mBrandList.get(position).getBrandName());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
