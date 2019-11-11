package com.lovelycoding.automobile.ui.home.brand.bike;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lovelycoding.automobile.datamodel.BrandRvItem;
import com.lovelycoding.automobile.repository.GetDataFromFireBase;

import java.util.List;

public class BrandActivityViewModel extends ViewModel {
    private GetDataFromFireBase mRepo;
    private MutableLiveData<List<BrandRvItem>> mutableLiveData = new MutableLiveData<>();

    public BrandActivityViewModel() {
        mRepo=GetDataFromFireBase.getInstance();
    }

   /* public MutableLiveData<List<BrandRvItem>> getBrandList(String motorName, BrandAdapter mAdapter) {
        mutableLiveData=mRepo.getBrandList(motorName,mAdapter);
        return mutableLiveData;
    }*/
}
