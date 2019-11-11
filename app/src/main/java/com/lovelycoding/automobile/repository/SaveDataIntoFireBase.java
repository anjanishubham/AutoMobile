package com.lovelycoding.automobile.repository;

import android.app.ProgressDialog;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lovelycoding.automobile.datamodel.BrandRvItem;
import com.lovelycoding.automobile.datamodel.PcategoryRvItem;
import com.lovelycoding.automobile.datamodel.Product;
import com.lovelycoding.automobile.ui.home.brand.dialogfragment.AddNewBrad;
import com.lovelycoding.automobile.util.App;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SaveDataIntoFireBase {
    private static SaveDataIntoFireBase instance;

    public static SaveDataIntoFireBase getInstance() {
        if (instance == null) {
            instance = new SaveDataIntoFireBase();
        }
        return instance;
    }

    public SaveDataIntoFireBase() {

    }

    private ProgressDialog mProgressDialog;
    public void saveBrandImageFireBaseStorage(final BrandRvItem ob, Uri uri, final String motorType) {
        final StorageReference filepath = App.mBrandImageStorageRef.child(ob.getBrandName()).child(String.valueOf(System.currentTimeMillis()) + ".PNG");
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                AddNewBrad.mProgressBar.setVisibility(View.VISIBLE);
                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url = uri.toString();
                        ob.setBrandImageUrl(url);
                        saveBrandIntoFireDataBase(ob, motorType);
                    }
                });
            }
        });
    }

    public void saveBrandIntoFireDataBase(BrandRvItem ob, String motorType) {

        App.mBrandDatabaseRef.child(motorType).child(ob.getBrandName()).setValue(ob);
        AddNewBrad.mProgressBar.setVisibility(View.GONE);
    }


    public void saveCateGoryImageIntoFireBaseStroage(final PcategoryRvItem ob, Uri uri, final String motorType) {
        Log.d(TAG, "onSuccess: "+motorType);

        final StorageReference filepath = App.mCategoryImageStorageRef.child(ob.getCategoryItemName()).child(String.valueOf(System.currentTimeMillis()) + ".PNG");
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                AddNewBrad.mProgressBar.setVisibility(View.VISIBLE);
                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url = uri.toString();
                        ob.setCategoryItemUrl(url);
                        saveCategoryIntoFireBaseDataBase(ob, motorType);
                    }
                });
            }
        });
    }

    private void saveCategoryIntoFireBaseDataBase(PcategoryRvItem ob, String motorType) {
        App.mCategoryDatabaseRef.child(motorType).child(ob.getCategoryItemName()).setValue(ob);
        AddNewBrad.mProgressBar.setVisibility(View.GONE);
    }

    public void saveRecentCategoryDataIntoDatabase(List<PcategoryRvItem> mCategoryList) {
        for (int i = 0; i <mCategoryList.size() ; i++) {

            App.mCurrentUserRecentCategoryDatabaseRef.child(i+"").setValue(mCategoryList.get(i));
        }
    }

    public void saveRecentBrandDataIntoDatabase(List<BrandRvItem> rvBrandItemList) {
        Map<String, BrandRvItem> mapList = new HashMap<>();
        for (int i = 0; i <rvBrandItemList.size() ; i++) {
          //  App.mCurrentUserRecentCategoryDatabaseRef.child(recent+"").setValue(ob);
            App.mCurrentUserRecentBrandDatabaseRef.child(i+"").setValue(rvBrandItemList.get(i));
        }
    }

    //Fetch the Recent category data form FireBase and update in UI

    public void updateProductItemCount(Product product,int count) {

        Map<String, Object> map = new HashMap<>();
        map.put("productCount",count);
        App.mCurrentUserDatabaseRef.child(product.getProductId()).updateChildren(map);
    }

}
