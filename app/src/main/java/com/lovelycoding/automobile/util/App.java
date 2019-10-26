package com.lovelycoding.automobile.util;

import android.app.Application;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class App extends Application {
    public static  FirebaseAuth mAuth;
    public static DatabaseReference mRootDatabaseRef;
    public static StorageReference mRootStorageRef;

    @Override
    public void onCreate() {
        super.onCreate();
        mAuth=FirebaseAuth.getInstance();
        mRootDatabaseRef= FirebaseDatabase.getInstance().getReference(Constant.ROOT_DATABASE);
        mRootStorageRef=FirebaseStorage.getInstance().getReference(Constant.PRODUCT_IMAGE_STORAGE);

       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mRootDatabaseRef.keepSynced(true);
    }
}
