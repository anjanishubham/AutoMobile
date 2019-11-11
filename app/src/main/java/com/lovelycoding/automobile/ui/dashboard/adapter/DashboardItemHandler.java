package com.lovelycoding.automobile.ui.dashboard.adapter;

import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.lovelycoding.automobile.R;
import com.lovelycoding.automobile.datamodel.Product;
import com.lovelycoding.automobile.repository.SaveDataIntoFireBase;

public class DashboardItemHandler {

    private static final String TAG = "DashboardItemHandler";

    public void onClickMinusProduct(View view, Product mProduct) {
        SaveDataIntoFireBase.getInstance().updateProductItemCount(mProduct,mProduct.getProductCount()-1);
        

    }

    public void onClickAddProduct(View view,Product mProduct)
    {
        Log.d(TAG, "onClickAddProduct: ");
        SaveDataIntoFireBase.getInstance().updateProductItemCount(mProduct,mProduct.getProductCount()+1);

    }
}
