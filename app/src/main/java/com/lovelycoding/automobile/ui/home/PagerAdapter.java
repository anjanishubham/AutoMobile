package com.lovelycoding.automobile.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.lovelycoding.automobile.R;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {

    List<Bitmap> bitmapList;
    private Context context;
    LayoutInflater layoutInflater;
    public PagerAdapter(Context context) {
        this.context=context;
        bitmapList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return bitmapList.size();
    }
    public void setUriList(List<Bitmap> uriList) {
        this.bitmapList=uriList;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = LayoutInflater.from(container.getContext());
        View view = layoutInflater.inflate(R.layout.viewpager_item, container, false);
        AppCompatImageView imageView=view.findViewById(R.id.iv_view_pager_item);
        imageView.setImageBitmap(bitmapList.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {


        container.removeView((View)object);
    }


}
