package com.lovelycoding.automobile.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.lovelycoding.automobile.R;
import com.lovelycoding.automobile.databinding.FragmentHomeBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements HomeListener.RuntimePermission {

    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private ViewPager mViewPager;
    HomeListener handler;
    FragmentHomeBinding binding;
    private static final int PICK_FROM_GALLERY = 1;
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static Uri imageUri=null;
    private static List<Bitmap> bitmapList = new ArrayList<>();
    private static List<Uri> uriList = new ArrayList<>();
    private PagerAdapter mPagerAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        mViewPager=binding.productImageViewpager;
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        handler=new HomeListener(getContext(),binding,this);
        binding.setHandlers(handler);
        mPagerAdapter=new PagerAdapter(getContext());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_FROM_GALLERY);
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

    private void initViewPager() {
        binding.productImageView.setVisibility(View.GONE);
        binding.addImage.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.VISIBLE);
        mViewPager.setClipToPadding(false);
        mViewPager.setPadding(80, 0, 80, 0);
        mViewPager.setPageMargin(20);
        mPagerAdapter.setUriList(bitmapList);
        mViewPager.setAdapter(mPagerAdapter);

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

    public static List<Uri> getUriList() {
        return uriList;
    }
}