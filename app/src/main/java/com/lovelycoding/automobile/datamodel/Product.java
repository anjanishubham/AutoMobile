package com.lovelycoding.automobile.datamodel;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String productId;
    private String motorName;
    private String motorBrandName;
    private String productName;
    private String productCategory;
    private String productDescription;
    private int productCount;
    private long productMRP;
    private long productSP;
    private List<String> productImageUrl;


    public Product() {
        productId="pId"+System.currentTimeMillis();
        productImageUrl = new ArrayList<>();

    }

    public String getMotorName() {
        return motorName;
    }

    public void setMotorName(String motorName) {
        this.motorName = motorName;
    }

    public String getMotorBrandName() {
        return motorBrandName;
    }

    public void setMotorBrandName(String motorBrandName) {
        this.motorBrandName = motorBrandName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public List<String> getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(List<String> productImageUrl) {
        this.productImageUrl = productImageUrl;
    }
    public void addProductImageUrl(String stringUrl)
    {
        productImageUrl.add(stringUrl);
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public long getProductMRP() {
        return productMRP;
    }

    public void setProductMRP(long productMRP) {
        this.productMRP = productMRP;
    }

    public long getProductSP() {
        return productSP;
    }

    public void setProductSP(long productSP) {
        this.productSP = productSP;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productMRP=" + productMRP +
                ", productSP=" + productSP +
                ", productCount=" + productCount +
                ", productImageUrl=" + productImageUrl +
                ", motorName='" + motorName + '\'' +
                ", motorBrandName='" + motorBrandName + '\'' +
                ", productCategory='" + productCategory + '\'' +
                '}';
    }

    @BindingAdapter("ImageUrl")
    public static void setImageUrl(ImageView view, String imageUrl) {
        Picasso.with(view.getContext()).load(imageUrl).into(view);
    }
}

