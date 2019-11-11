package com.lovelycoding.automobile.ui.home.brand.dialogfragment;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.lovelycoding.automobile.R;
import com.lovelycoding.automobile.datamodel.BrandRvItem;
import com.lovelycoding.automobile.datamodel.PcategoryRvItem;
import com.lovelycoding.automobile.repository.SaveDataIntoFireBase;
import com.lovelycoding.automobile.ui.home.brand.bike.BrandActivity;
import com.lovelycoding.automobile.ui.home.category.CategoryActivity;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewBrad extends DialogFragment implements View.OnClickListener {


    MaterialButton btnAddBrand;
    AppCompatImageView imageView;
    TextInputEditText etBrandName;
    TextInputLayout tilBrandName;
    Toolbar mToolbar;
    public static ProgressBar mProgressBar;

    private static final String TAG = "AddNewBrad";
    private static final int PICK_FROM_GALLERY = 1;
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    Bitmap bitmap=null;
    Uri uri;
    String toolbarTitle;

    public AddNewBrad(String toolbarTitle) {
        // Required empty public constructor
        this.toolbarTitle=toolbarTitle;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_brad, container, false);
        imageView = view.findViewById(R.id.iv_brand_image);
        etBrandName = view.findViewById(R.id.tied_brand_name);
        tilBrandName = view.findViewById(R.id.til_brand_name);
        btnAddBrand = view.findViewById(R.id.bt_add_brand);
        mProgressBar=view.findViewById(R.id.progress_circular);
        mToolbar = view.findViewById(R.id.toolbar);
        initToolbar();
        imageView.setOnClickListener(this);
        btnAddBrand.setOnClickListener(this);
        return view;
    }
    private void initToolbar() {
        mToolbar.setTitle(toolbarTitle);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("My Title");
        return dialog;

    }
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FROM_GALLERY);
    }

    private void getRuntimePermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Log.d(TAG, "getRuntimePermission: ");
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);

            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);

            }
        } else {
            openGallery();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && data != null) {

            if (data.getData() != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    imageView.setImageBitmap(bitmap);
                    uri=data.getData();
                    // uriList.add(data.getData());
                    //bitmapList.add(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CONSTANT && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onRequestPermissionsResult:onRequestPermission ");
            openGallery();
        }
        Log.d(TAG, "onRequestPermissionsResult: " + requestCode + "  " + permissions + "  " + grantResults);

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.iv_brand_image:

                getRuntimePermission();
                imageView.setImageBitmap(bitmap);
                break;
            case R.id.bt_add_brand:
                if(verifyInput()){
                    if(toolbarTitle.equals("Add Brand")){
                        BrandRvItem ob=new BrandRvItem();
                        ob.setBrandName(etBrandName.getText().toString());
                        SaveDataIntoFireBase.getInstance().saveBrandImageFireBaseStorage(ob,uri, BrandActivity.motorType);
                        getDialog().cancel();
                    }
                    else {
                        PcategoryRvItem ob = new PcategoryRvItem();
                        ob.setCategoryItemName(etBrandName.getText().toString());
                        SaveDataIntoFireBase.getInstance().saveCateGoryImageIntoFireBaseStroage(ob,uri, CategoryActivity.motorType);
                        getDialog().cancel();
                    }
                    mProgressBar.setVisibility(View.VISIBLE);


                    break;
                }
        }
    }

    private boolean verifyInput() {

        if(TextUtils.isEmpty(etBrandName.getText().toString())){
            tilBrandName.setError("Please enter Brand Name ");
            return false;
        }
        else {
            tilBrandName.setError("");
        }
        if(bitmap==null){
            Toast.makeText(getContext(), "Add an Brand Image ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
