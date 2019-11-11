package com.lovelycoding.automobile.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.lovelycoding.automobile.R;
import com.lovelycoding.automobile.databinding.FragmentHomeBinding;
import com.lovelycoding.automobile.datamodel.BrandRvItem;
import com.lovelycoding.automobile.datamodel.PcategoryRvItem;
import com.lovelycoding.automobile.repository.GetDataFromFireBase;
import com.lovelycoding.automobile.repository.SaveDataIntoFireBase;
import com.lovelycoding.automobile.ui.home.brand.adapter.BrandAdapter;
import com.lovelycoding.automobile.ui.home.brand.adapter.BrandListener;
import com.lovelycoding.automobile.ui.home.adapter.PagerAdapter;
import com.lovelycoding.automobile.ui.home.adapter.RecentBrandPageAdapter;
import com.lovelycoding.automobile.ui.home.adapter.RecentCategoryPageAdapter;
import com.lovelycoding.automobile.ui.callback_interface.RecentCategoryInterface;
import com.lovelycoding.automobile.util.MyApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements HomeListener.RuntimePermission , BrandListener ,SelectItemCallback, RecentCategoryInterface {

    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private ViewPager mProductImageViewPager;
    private ViewPager mRecentCategoryViewpager,mRecentBrandViewpager;
    private HomeListener handler;
    private FragmentHomeBinding binding;
    private static final int PICK_FROM_GALLERY = 1;
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static List<Bitmap> bitmapList = new ArrayList<>();
    private static List<Uri> uriList = new ArrayList<>();
    private PagerAdapter mPagerAdapter;
    private RecentBrandPageAdapter mRecentBrandPagerAdapter;
    private BrandAdapter mAdapter;
    private RecentCategoryPageAdapter mRecentCatPagerAdapter;
    private List<PcategoryRvItem> mRecentCategoryList = new ArrayList<>();
    private List<BrandRvItem> mRecentBrandList = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mProductImageViewPager=binding.productImageViewpager;
        mRecentCategoryViewpager=binding.recentCategoryLayout.viewPagerRecent;
        mRecentBrandViewpager=binding.recentBrandLayout.viewPagerRecent;
        mRecentBrandViewpager=binding.recentBrandLayout.viewPagerRecent;
        loadDataFromFirebseOnceAppStarted();
        handler=new HomeListener(getContext(),this,binding,this);
        binding.setHandlers(handler);
        mPagerAdapter=new PagerAdapter(getContext());
        mAdapter=new BrandAdapter(this);
        mRecentCatPagerAdapter =new RecentCategoryPageAdapter();
        mRecentBrandPagerAdapter=new RecentBrandPageAdapter();
        return binding.getRoot();
    }

    private void loadDataFromFirebseOnceAppStarted() {
        //checking network connection if network is there then load the recent View
        if(!MyApplication.isNetworkAvailable){
            Toast.makeText(getContext(), "Please check your network connection ", Toast.LENGTH_SHORT).show();
        }
        else {
            binding.progressCircular.setVisibility(View.VISIBLE);
            GetDataFromFireBase.getInstance().getRecentCategoryDataFromDataBase(this);
            GetDataFromFireBase.getInstance().getRecentBrandDataFromDatabase(this);
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_FROM_GALLERY);
    }

    private void initViewPager() {
        binding.productImageView.setVisibility(View.GONE);
        binding.addImage.setVisibility(View.VISIBLE);
        mProductImageViewPager.setVisibility(View.VISIBLE);
        mProductImageViewPager.setClipToPadding(false);
        mProductImageViewPager.setPadding(80, 0, 80, 0);
        mProductImageViewPager.setPageMargin(20);
        mPagerAdapter.setUriList(bitmapList);
        mProductImageViewPager.setAdapter(mPagerAdapter);
    }



    public static List<Uri> getUriList() {
        return uriList;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecentCategoryViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCategoryItem(mRecentCategoryList.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mRecentBrandViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setBrandItem(mRecentBrandList.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==EXTERNAL_STORAGE_PERMISSION_CONSTANT && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "onRequestPermissionsResult:onRequestPermission ");
            openGallery();
        }
        Log.d(TAG, "onRequestPermissionsResult: "+requestCode +"  "+permissions+"  "+grantResults);
    }

    @Override
    public void checkRuntimePermission() {

        if(ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
            openGallery();
        }
        else
            getRuntimePermission();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    // update the category UI
    private void setCategoryItem(PcategoryRvItem ob) {
        if(ob!=null) {
            Log.d(TAG, "setCategory: "+ob.toString());
            Glide.with(this).load(ob.getCategoryItemUrl()).into(binding.recentCategoryLayout.ivSelect.ivSelect);
            binding.recentCategoryLayout.ivSelect.tvRecentItemName.setText(ob.getCategoryItemName());

        }
    }

    private void setBrandItem(BrandRvItem ob) {
        Glide.with(this).load(ob.getBrandImageUrl()).into(binding.recentBrandLayout.ivSelect.ivSelect);
        binding.recentBrandLayout.ivSelect.tvRecentItemName.setText(ob.getBrandName());

    }

// retrieve from database and show in viewpager
    private void initRecentCategoryViewPager() {

        binding.recentCategoryLayout.tvEmptyViewPager.setVisibility(View.GONE);
        mRecentCategoryViewpager=binding.recentCategoryLayout.viewPagerRecent;
        mRecentCategoryViewpager.setPadding(80, 0, 80, 0);
        mRecentCategoryViewpager.setPageMargin(20);
        mRecentCatPagerAdapter.setRecentCategoryList(mRecentCategoryList);
        mRecentCategoryViewpager.setAdapter(mRecentCatPagerAdapter);
        //mRecentCategoryViewpager.
    }
     private void initRecentBrandViewPager() {

        binding.recentBrandLayout.tvEmptyViewPager.setVisibility(View.GONE);
        mRecentBrandViewpager=binding.recentBrandLayout.viewPagerRecent;
         mRecentBrandViewpager.setClipToPadding(false);
         mRecentBrandViewpager.setPadding(80, 0, 80, 0);
         mRecentBrandViewpager.setPageMargin(20);
        mRecentBrandPagerAdapter.setBrandList(mRecentBrandList);
         mRecentBrandViewpager.setAdapter(mRecentBrandPagerAdapter);
        //mRecentCategoryViewpager.
    }


    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    @Override
    public void onClick(View view) {

    }


    private void getRuntimePermission() {
        if(ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                Log.d(TAG, "getRuntimePermission: ");
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION_CONSTANT);

            }
            else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION_CONSTANT);

            }
        }
        else {
            openGallery();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_FROM_GALLERY && resultCode==RESULT_OK && data!=null ){

            if(data.getClipData()!=null) {
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    try {
                        uriList.add(data.getClipData().getItemAt(i).getUri());
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getClipData().getItemAt(i).getUri());
                        bitmapList.add(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if(data.getData()!=null){
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    uriList.add(data.getData());
                    bitmapList.add(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            initViewPager();
            Log.d(TAG, "onActivityResult: "+bitmapList.size() +" "+bitmapList);
        }

    }

    @Override
    public void readRecentCategoryData(List<PcategoryRvItem> mRecentCategoryList) {
        binding.progressCircular.setVisibility(View.GONE);
        Log.d(TAG, "readRecentCategoryData: "+mRecentCategoryList);
        this.mRecentCategoryList=mRecentCategoryList;
        if(mRecentCategoryList.size()>0) {
            binding.recentCategoryLayout.tvEmptyViewPager.setVisibility(View.GONE);

            initRecentCategoryViewPager();
            setCategoryItem(mRecentCategoryList.get(0));
        }
        else {
            binding.recentCategoryLayout.tvEmptyViewPager.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void readRecentBrandData(List<BrandRvItem> mRecentBrandList) {
        binding.progressCircular.setVisibility(View.GONE);
        Log.d(TAG, "readRecentBrandData: " + mRecentBrandList.size());
        this.mRecentBrandList=mRecentBrandList;
        if(mRecentBrandList.size()>0) {
            binding.recentBrandLayout.tvEmptyViewPager.setVisibility(View.GONE);

            initRecentBrandViewPager();
            setBrandItem(mRecentBrandList.get(0));
        }
        else {
            binding.recentBrandLayout.tvEmptyViewPager.setVisibility(View.VISIBLE);
        }

    }

        @Override
    public void itemClicked(int position) {

    }

    @Override
    public void getSelectCategoryItem(PcategoryRvItem ob) {
        setCategoryItem(ob);
        updateRecentCategoryList(ob);
    }

    @Override
    public void getSelectBrandItem(BrandRvItem ob) {
        setBrandItem(ob);
        updateRecentBrandList(ob);
    }
    @Override
    public void updateRecentBrandData(BrandRvItem mBrandItem) {

    }

    @Override
    public void updateRecentCategoryData(PcategoryRvItem mCategoryItem) {
        Log.d(TAG, "updateRecentCategoryData: "+mCategoryItem.getCategoryItemName());
    }

    public void updateRecentBrandList(BrandRvItem mBrandItem){
        mRecentBrandList.add(0,mBrandItem);
        Log.d(TAG, "updateRecentBrandList: "+mRecentBrandList.size());
        if (mRecentBrandList.size() > 4) {
            mRecentBrandList.remove(4);
        }
        SaveDataIntoFireBase.getInstance().saveRecentBrandDataIntoDatabase(mRecentBrandList);
        mRecentBrandPagerAdapter.setBrandList(mRecentBrandList);
        mRecentBrandViewpager.setAdapter(mRecentBrandPagerAdapter);
        mRecentBrandPagerAdapter.notifyDataSetChanged();
    }
    public void updateRecentCategoryList(PcategoryRvItem mCategory){
        mRecentCategoryList.add(0,mCategory);
        Log.d(TAG, "updateRecentBrandList: "+mRecentCategoryList.size());
        if (mRecentCategoryList.size() > 4) {
            mRecentCategoryList.remove(4);
        }
        SaveDataIntoFireBase.getInstance().saveRecentCategoryDataIntoDatabase(mRecentCategoryList);
        mRecentCatPagerAdapter.setRecentCategoryList(mRecentCategoryList);
        mRecentCategoryViewpager.setAdapter(mRecentCatPagerAdapter);
        mRecentCatPagerAdapter.notifyDataSetChanged();
    }


}