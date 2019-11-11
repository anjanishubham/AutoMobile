package com.lovelycoding.automobile.ui.dashboard;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lovelycoding.automobile.datamodel.Product;
import com.lovelycoding.automobile.repository.GetDataFromFireBase;
import com.lovelycoding.automobile.ui.dashboard.adapter.DashboardItemAdapter;
import com.lovelycoding.automobile.ui.dashboard.callback.DashboardCallback;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel implements DashboardCallback {
    private static final String TAG = "DashboardViewModel";
    private Context context;
    private RecyclerView mRecycleView;
    private DashboardItemAdapter mAdapter;
    private List<Product> mProductList;
    private int currentSelectedPosition;


    public DashboardViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();
        mAdapter = new DashboardItemAdapter(this);

    }


    public void setView(RecyclerView mRecycleView) {

        this.mRecycleView = mRecycleView;


    }

    public void getProductList() {
        GetDataFromFireBase.getInstance().getProductList(this);

    }

    // public MutableLiveData<List<Product>> mProductListLiveData = new MutableLiveData<>();

    /*public MutableLiveData<List<Product>> getProductList() {

         return GetDataFromFireBase.getInstance().getProductList(this);
    }*/

    @Override
    public void getProductList(List<Product> mProductList) {

        Log.d(TAG, "getProductList: " + mProductList);
        if(mProductList.size()>0){
            this.mProductList=mProductList;
        }
        initRecycleView();
    }

    @Override
    public void updateProductCount(Product mProduct) {
            getCurrentClickItemPosition(mProduct);
            mProductList.get(currentSelectedPosition).setProductCount(mProduct.getProductCount());
            mAdapter.notifyItemChanged(currentSelectedPosition);
    }

    @Override
    public void deleteProduct(Product mProduct) {

    }

    @Override
    public void getCurrentPosition(int position) {
        Log.d(TAG, "getCurrentPosition: "+position);
    }

    public void getCurrentClickItemPosition(Product mProduct) {

        for (Product product : mProductList) {
            if(product.getProductId().equals(mProduct.getProductId())){
               currentSelectedPosition= mProductList.indexOf(product);

            }
        }
    }

    private void initRecycleView() {
        mAdapter.setProductList(mProductList);
        mRecycleView.setLayoutManager(new GridLayoutManager(context, 2));
        mRecycleView.setVerticalScrollBarEnabled(true);
        mRecycleView.setNestedScrollingEnabled(true);
        mRecycleView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }




}