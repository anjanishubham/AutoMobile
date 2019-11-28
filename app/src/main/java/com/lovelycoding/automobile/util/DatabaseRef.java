package com.lovelycoding.automobile.util;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DatabaseRef {
    public static  FirebaseAuth mAuth;
    public static DatabaseReference mRootDatabaseRef;
    public static DatabaseReference mBrandDatabaseRef;
    public static DatabaseReference mCategoryDatabaseRef;
    public static StorageReference mRootStorageRef;
    public static StorageReference mBrandImageStorageRef;
    public static StorageReference mCategoryImageStorageRef;
    public static DatabaseReference mCurrentUserDatabaseRef;
    public static DatabaseReference mCurrentUserRecentBrandDatabaseRef;
    public static DatabaseReference mCurrentUserRecentCategoryDatabaseRef;
    public static DatabaseReference mCurrentUserProfileDatabaseRef;
    public static StorageReference mCurrentUserProfileImageStorageRef;
    private static Context context;

    public static void fireBaseInstance(Context context1){
        context=context1;
        mAuth=FirebaseAuth.getInstance();
        mRootDatabaseRef= FirebaseDatabase.getInstance().getReference(Constant.ROOT_DATABASE);
        mRootStorageRef=FirebaseStorage.getInstance().getReference(Constant.PRODUCT_IMAGE_STORAGE);
        mBrandDatabaseRef=mRootDatabaseRef.child(Constant.BRAND_DATABASE);
        mBrandImageStorageRef=FirebaseStorage.getInstance().getReference(Constant.PRODUCT_BRAND_IMAGE_STORAGE);
        mCategoryDatabaseRef = mRootDatabaseRef.child(Constant.PRODUCT_CATEGORY_IMAGE_DATABASE);
        mCategoryImageStorageRef=FirebaseStorage.getInstance().getReference(Constant.PRODUCT_CATEGORY_IMAGE_STORAGE);
        mCurrentUserDatabaseRef=mRootDatabaseRef.child(mAuth.getUid()).child("_product");
        mCurrentUserRecentBrandDatabaseRef=mRootDatabaseRef.child(mAuth.getUid()).child("_recent_brand");
        mCurrentUserRecentCategoryDatabaseRef=mRootDatabaseRef.child(mAuth.getUid()).child("_recent_category");
        mCurrentUserProfileDatabaseRef=mRootDatabaseRef.child(Constant.USER_PROFILE_DATABASE);
        mCurrentUserProfileImageStorageRef=FirebaseStorage.getInstance().getReference(Constant.USER_PROFILE_IMAGE_STORAGE);

        mRootDatabaseRef.keepSynced(true);


        //

       // BrandDemoData.createCustomValue();
    }

    public static boolean isNetworkAvailable() {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}
