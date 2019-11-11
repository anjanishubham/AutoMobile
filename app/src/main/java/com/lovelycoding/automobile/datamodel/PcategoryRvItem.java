package com.lovelycoding.automobile.datamodel;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class PcategoryRvItem {
    private String pCategoryItemUrl;
    private String pCategoryItemName;

    public PcategoryRvItem() {

    }

    public PcategoryRvItem(String pCategoryItemUrl, String pCategoryItemName) {
        this.pCategoryItemUrl = pCategoryItemUrl;
        this.pCategoryItemName = pCategoryItemName;
    }

    public String getCategoryItemUrl() {
        return pCategoryItemUrl;
    }

    public void setCategoryItemUrl(String pCategoryItemUrl) {
        this.pCategoryItemUrl = pCategoryItemUrl;
    }

    public String getCategoryItemName() {
        return pCategoryItemName;
    }

    public void setCategoryItemName(String pCategoryItemName) {
        this.pCategoryItemName = pCategoryItemName;
    }
    @BindingAdapter("pCategoryItemUrl")
    public static void loadImage(AppCompatImageView view, String pCategoryItemUrl){
        Glide.with(view.getContext()).load(pCategoryItemUrl).into(view);
    }

    @Override
    public String toString() {
        return "PcategoryRvItem{" +
                "pCategoryItemUrl='" + pCategoryItemUrl + '\'' +
                ", pCategoryItemName='" + pCategoryItemName + '\'' +
                '}';
    }
}
