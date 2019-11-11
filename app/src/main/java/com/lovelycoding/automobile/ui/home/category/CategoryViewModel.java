package com.lovelycoding.automobile.ui.home.category;

import android.widget.ProgressBar;

import androidx.lifecycle.ViewModel;

import com.lovelycoding.automobile.repository.GetDataFromFireBase;
import com.lovelycoding.automobile.ui.home.category.adapter.CategoryAdapter;

public class CategoryViewModel extends ViewModel {


    public void getCategoryList(CategoryAdapter mAdapter, String motorType, ProgressBar mProgressBar) {
        GetDataFromFireBase.getInstance().getCategoryListFromFireBaseDatabase(mAdapter,motorType,mProgressBar);
    }
}
