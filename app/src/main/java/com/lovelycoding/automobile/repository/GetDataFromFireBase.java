package com.lovelycoding.automobile.repository;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.lovelycoding.automobile.datamodel.BrandRvItem;
import com.lovelycoding.automobile.datamodel.PcategoryRvItem;
import com.lovelycoding.automobile.datamodel.Product;
import com.lovelycoding.automobile.datamodel.UserProfile;
import com.lovelycoding.automobile.ui.home.brand.bike.BrandListCallback;
import com.lovelycoding.automobile.ui.home.category.adapter.CategoryAdapter;
import com.lovelycoding.automobile.ui.callback_interface.RecentCategoryInterface;
import com.lovelycoding.automobile.ui.dashboard.callback.DashboardCallback;
import com.lovelycoding.automobile.ui.settings.SettingUserProfile;
import com.lovelycoding.automobile.ui.settings.SettingsViewModel;
import com.lovelycoding.automobile.util.DatabaseRef;

import java.util.ArrayList;
import java.util.List;

public class GetDataFromFireBase {
     private static final String TAG = "GetDataFromFireBase";
    private List<BrandRvItem> mBrandList;
    private MutableLiveData<List<BrandRvItem>> mLiveDatList = new MutableLiveData<>();
    private static GetDataFromFireBase mInstance;
    private RecentCategoryInterface mCallback;
    public static GetDataFromFireBase getInstance() {
        if(mInstance==null){
            mInstance=new GetDataFromFireBase();
        }
        return mInstance;
    }

    private GetDataFromFireBase() {
        mBrandList = new ArrayList<>();
    }

   /* public MutableLiveData<List<BrandRvItem>> getBrandList(String brandName, final BrandAdapter mAdapter) {
        mBrandList.clear();


        DatabaseRef.mBrandDatabaseRef.child(brandName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildAdded: "+dataSnapshot.getValue(BrandRvItem.class));
                mBrandList.add(dataSnapshot.getValue(BrandRvItem.class));
                mAdapter.setBrandRvItemList(mBrandList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
        return mLiveDatList;
    }*/

    public void getBrandListFromDatabase(final BrandListCallback mCallback, Context context, String motorType) {
        if(DatabaseRef.isNetworkAvailable()) {

            final List<BrandRvItem> mBrandList = new ArrayList<>();
            DatabaseRef.mBrandDatabaseRef.child(motorType).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    mBrandList.add(dataSnapshot.getValue(BrandRvItem.class));
                    Log.d(TAG, "onChildAdded: "+mBrandList.size());
                    mCallback.getOnBrandList(mBrandList);

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
        }
        else {

           // Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
             Toast.makeText(context, "Please check netwrok connection ", Toast.LENGTH_SHORT).show();
        }
    }

    public void getCategoryListFromFireBaseDatabase(final CategoryAdapter mAdapter, String motorType, final ProgressBar mProgressBar) {
        final List<PcategoryRvItem> mList = new ArrayList<>();
        DatabaseRef.mCategoryDatabaseRef.child(motorType).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                PcategoryRvItem ob= dataSnapshot.getValue(PcategoryRvItem.class);
                mList.add(ob);
                mAdapter.setCategoryList(mList);
                mAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
               // Log.d(TAG, "onChildAdded: brandlist"+ mList.size());
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getRecentCategoryDataFromDataBase(final RecentCategoryInterface mCallback) {
        final List<PcategoryRvItem> mCategoryList = new ArrayList<>();
        DatabaseRef.mCurrentUserRecentCategoryDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mCategoryList.add(dataSnapshot.getValue(PcategoryRvItem.class));
                Log.d(TAG, "onChildAdded: recent"+(dataSnapshot.getValue(PcategoryRvItem.class)));
                mCallback.readRecentCategoryData(mCategoryList);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildAdded: recent changed"+(dataSnapshot.getValue(PcategoryRvItem.class)).getCategoryItemName());

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildAdded: recent moved"+(dataSnapshot.getValue(PcategoryRvItem.class)).getCategoryItemName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getRecentBrandDataFromDatabase(final RecentCategoryInterface mCallback) {
        final List<BrandRvItem> mBrandList = new ArrayList<>();

        DatabaseRef.mCurrentUserRecentBrandDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                mBrandList.add(dataSnapshot.getValue(BrandRvItem.class));
                Log.d(TAG, "onChildAdded:brand add "+(dataSnapshot.getValue(BrandRvItem.class).getBrandName()));
                mCallback.readRecentBrandData(mBrandList);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Log.d(TAG, "onChildAdded:brand changed  "+(dataSnapshot.getValue(BrandRvItem.class).getBrandName()));
                mCallback.updateRecentBrandData(dataSnapshot.getValue(BrandRvItem.class));

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG, "onChildAdded:brand removed "+(dataSnapshot.getValue(BrandRvItem.class).getBrandName()));

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getProductList(final DashboardCallback mCallback) {
        //final MutableLiveData<List<Product>> mLiveDataList = new MutableLiveData<>();
        final List<Product> mList = new ArrayList<>();
        DatabaseRef.mCurrentUserDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildAdded: "+s);
                mList.add(dataSnapshot.getValue(Product.class));
                Log.d(TAG, "onChildAdded: product list"+dataSnapshot.getValue(Product.class).getProductCount());
                mCallback.getProductList(mList);
                //mLiveDataList.postValue(mList);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Log.d(TAG, "onChildChanged: "+s);
                Log.d(TAG, "onChildAdded: product list changed"+dataSnapshot.getValue(Product.class).getProductCount());
                mCallback.updateProductCount(dataSnapshot.getValue(Product.class));

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildAdded: product list delete"+dataSnapshot.getValue(Product.class).getProductCount());
                mCallback.deleteProduct(dataSnapshot.getValue(Product.class));
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //return mLiveDataList;
    }

    public void getUserProfile(final SettingUserProfile mUserProfile) {
        DatabaseRef.mCurrentUserProfileDatabaseRef.child(DatabaseRef.mAuth.getUid()).addChildEventListener(new ChildEventListener() {
            UserProfile userProfile=new UserProfile();
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

               String str=dataSnapshot.getValue(String.class);
                Log.d(TAG, "onChildAdded: "+str);

                if(dataSnapshot.getKey().equals("userName")){
                     userProfile.setUserName(str);
                }
                else if (dataSnapshot.getKey().equals("userPhone")){
                        userProfile.setUserPhone(str);
                 } else if (dataSnapshot.getKey().equals("userProfileUrl")) {
                        userProfile.setUserProfileUrl(str);
                 }
                mUserProfile.getUserProfile(userProfile);
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String str=dataSnapshot.getValue(String.class);
                Log.d(TAG, "onChildAdded: changed  "+str);

                if(dataSnapshot.getKey().equals("userName")){
                    userProfile.setUserName(str);
                }
                else if (dataSnapshot.getKey().equals("userPhone")){
                    userProfile.setUserPhone(str);
                } else if (dataSnapshot.getKey().equals("userProfileUrl")) {
                    userProfile.setUserProfileUrl(str);
                }
                mUserProfile.getUserProfile(userProfile);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
