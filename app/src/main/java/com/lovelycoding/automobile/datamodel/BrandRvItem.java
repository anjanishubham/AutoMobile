package com.lovelycoding.automobile.datamodel;

import android.media.Image;
import android.util.Log;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.firebase.database.Exclude;

public class BrandRvItem {
    private static final String TAG = "BrandRvItem";
   private String brandImageUrl;
    private String brandName;

    public BrandRvItem() {


    }

    public BrandRvItem(String brandImageUrl, String brandName) {
        this.brandImageUrl=brandImageUrl;
        this.brandName=brandName;
    }

    public String getBrandImageUrl() {
        return brandImageUrl;
    }

    public void setBrandImageUrl(String brandImageUrl) {
        this.brandImageUrl = brandImageUrl;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @BindingAdapter("brandImageUrl")
    public static void loadImage(ImageView view, String brandImageUrl) {

        Log.d(TAG, "loadImage:image loaded int view  "+brandImageUrl);
        Glide.with(view.getContext()).load(brandImageUrl).into(view);

    }

}
