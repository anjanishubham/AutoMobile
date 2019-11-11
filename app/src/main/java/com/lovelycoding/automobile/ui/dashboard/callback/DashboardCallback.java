package com.lovelycoding.automobile.ui.dashboard.callback;

import com.lovelycoding.automobile.datamodel.Product;

import java.util.List;

public interface DashboardCallback {
    public void getProductList(List<Product> mProductList);

    public void updateProductCount(Product mProduct);

    public void deleteProduct(Product mProduct);

    public void getCurrentPosition(int position);
}
