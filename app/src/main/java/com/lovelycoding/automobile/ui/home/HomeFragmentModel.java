package com.lovelycoding.automobile.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lovelycoding.automobile.databinding.FragmentHomeBinding;
import com.lovelycoding.automobile.datamodel.Product;
import com.lovelycoding.automobile.ui.home.HomeFragment;
import com.lovelycoding.automobile.util.App;

import java.util.List;

public class HomeFragmentModel {
    private static final String TAG = "HomeFragmentModel";
    private Context context;
    private ProgressDialog progressDialog;
    private Product product;
    private FragmentHomeBinding dataBinding;

    public HomeFragmentModel(Context context,FragmentHomeBinding dataBinding) {

        this.context=context;
        this.dataBinding=dataBinding;
        progressDialog = new ProgressDialog(context);
        product=new Product();


    }


    public void saveProductImageToStorage(final List<Uri> imageUri) {

        progressDialog.show();
        for (int i = 0; i <imageUri.size() ; i++) {
            final StorageReference filepath = App.mRootStorageRef.child(product.getProductId()).child(String.valueOf(System.currentTimeMillis()) + ".PNG");
            filepath.putFile(imageUri.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    if (taskSnapshot.getMetadata().getReference() != null) {
                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();
                                product.addProductImageUrl(url);
                                Log.d(TAG, "onSuccess: " + url);
                               // if(i==imageUri.size()-1)
                                saveProductIntoDatabase(product);
                                cleanText(dataBinding);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: " + e.getLocalizedMessage());
                }
            });


        }
        progressDialog.dismiss();
    }

    private void saveProductIntoDatabase(Product product) {
        App.mCurrentUserDatabaseRef.child(product.getProductId()).setValue(product);
    }


    public  void cleanText(FragmentHomeBinding dataBinding) {
      //  dataBinding.productImage.setImageBitmap(null);
        dataBinding.productImageViewpager.setVisibility(View.GONE);
        dataBinding.productImageView.setVisibility(View.VISIBLE);
        dataBinding.addImage.setVisibility(View.GONE);
        dataBinding.productName.setText("");
        dataBinding.productDescription.setText("");
        dataBinding.productMrp.setText("");
        dataBinding.productSp.setText("");
        dataBinding.productCount.setText("");
    }

    public boolean verifyInput() {

        product.setMotorName(dataBinding.spinnerMotorType.getSelectedItem().toString());
        product.setMotorBrandName(dataBinding.recentBrandLayout.ivSelect.tvRecentItemName.getText().toString());
        product.setProductCategory(dataBinding.recentCategoryLayout.ivSelect.tvRecentItemName.getText().toString());
        if(!dataBinding.productName.getText().toString().isEmpty())
        {
            product.setProductName(dataBinding.productName.getText().toString());
        }
        else {
            dataBinding.tilProductName.setError("product name is empty !!!");
            return false;
        }
        if(!dataBinding.productDescription.getText().toString().isEmpty())
        {
            product.setProductDescription(dataBinding.productDescription.getText().toString());
        }
        else {
            dataBinding.tilProductDescripton.setError("product Description  is empty !!!");
            return false;
        }
        if(!dataBinding.productMrp.getText().toString().isEmpty())
        {
            product.setProductMRP(Long.valueOf(dataBinding.productMrp.getText().toString()));
        }
        else {
            dataBinding.tilProductMrp.setError("product Mrp  is empty !!!");
            return false;
        }

        if(!dataBinding.productSp.getText().toString().isEmpty())
        {
            product.setProductSP(Long.valueOf(dataBinding.productSp.getText().toString()));
            if(product.getProductMRP()<product.getProductSP())
            {
                dataBinding.tilProductDescripton.setError("Selling price should be smaller than MRP !!!");
                return false;
            }
        }
        else {
            dataBinding.tilProductDescripton.setError("product SP  is empty !!!");
            return false;
        }

        if(!dataBinding.productCount.getText().toString().isEmpty())
        {
            product.setProductCount(Integer.valueOf(dataBinding.productCount.getText().toString()));
        }
        else {
            dataBinding.productCount.setError("product Mrp  is empty !!!");
            return false;
        }

        return true;

    }
}
