package com.lovelycoding.automobile.ui.settings;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.lovelycoding.automobile.R;
import com.lovelycoding.automobile.databinding.FragmentSettingsBinding;
import com.lovelycoding.automobile.datamodel.UserProfile;
import com.lovelycoding.automobile.repository.SaveDataIntoFireBase;
import com.lovelycoding.automobile.ui.login.LoginActivity;
import com.lovelycoding.automobile.util.DatabaseRef;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private  static final String TAG = "SettingsFragment";
    private SettingsViewModel settingViewModel;
    private AppCompatTextView tvSettingUserName,tvSettingUserPhone;
    private AppCompatEditText etSettingUserName;
    private AppCompatImageButton btSettingEditUserName,btSettingSaveUserName,btSettingAddProfileImage;
    private CircleImageView ivSettingProfileImage;
    private ContentLoadingProgressBar mProgressBar;

    // var
    private FragmentSettingsBinding mBinding;
    private static final int PICK_FROM_GALLERY = 1;
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT=10;
    Bitmap bitmap=null;
    Uri uri;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       // View root = inflater.inflate(R.layout.fragment_settings, container, false);
        mBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_settings,container,false);
        settingViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        initView(mBinding);
        setHasOptionsMenu(true);
        btSettingSaveUserName.setOnClickListener(this);
        btSettingEditUserName.setOnClickListener(this);
        btSettingAddProfileImage.setOnClickListener(this);

        return mBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProgressBar.show();
        settingViewModel.getUserProfile();
        settingViewModel.setUserProfile().observe(this, new Observer<UserProfile>() {
            @Override
            public void onChanged(UserProfile userProfile) {
                if(userProfile!=null){
                    loadUserProfile(userProfile);
                }
                else {
                    mProgressBar.hide();
                }
            }
        });

    }

    private void loadUserProfile(UserProfile userProfile) {
        Picasso.with(getContext()).load(userProfile.getUserProfileUrl()).into(ivSettingProfileImage);
        tvSettingUserName.setText(userProfile.getUserName());
        tvSettingUserPhone.setText(userProfile.getUserPhone());
        mProgressBar.hide();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_activity_menu,menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sign_out: {
                DatabaseRef.mAuth.signOut();
                Toast.makeText(getContext(), "Logout My account !!" , Toast.LENGTH_SHORT).show();
                gotoLoginActivity();
                break;
            }
        }
        return  true;
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

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FROM_GALLERY);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && data != null) {

            if (data.getData() != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                   // ivSettingProfileImage.setImageBitmap(bitmap);
                    uri=data.getData();
                    SaveDataIntoFireBase.getInstance().updateUserProfileImage(uri);
                    mProgressBar.show();
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
    }

    public void gotoLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);

    }
// ClickListener event
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_add_profile_image:
                getRuntimePermission();
                break;
            case R.id.setting_edit_btn:
                Log.d(TAG, "onClick: edit button clicked");
                tvSettingUserName.setVisibility(View.GONE);
                btSettingEditUserName.setVisibility(View.GONE);
                etSettingUserName.setVisibility(View.VISIBLE);
                btSettingSaveUserName.setVisibility(View.VISIBLE);
                etSettingUserName.setText(tvSettingUserName.getText().toString().trim());
                break;
            case R.id.setting_save_btn:
                Log.d(TAG, "onClick: save button click");
                String userName=etSettingUserName.getText().toString().trim();
                if(!TextUtils.isEmpty(userName)) {
                    tvSettingUserName.setVisibility(View.VISIBLE);
                    btSettingEditUserName.setVisibility(View.VISIBLE);
                    etSettingUserName.setVisibility(View.GONE);
                    btSettingSaveUserName.setVisibility(View.GONE);
                   // tvSettingUserName.setText(userName);
                    SaveDataIntoFireBase.getInstance().updateUserName(userName);
                    mProgressBar.show();
                }
                else {
                    Toast.makeText(getContext(), "Please enter user name ", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private void initView(FragmentSettingsBinding mBinding) {
        btSettingAddProfileImage=mBinding.settingAddProfileImage;
        btSettingEditUserName=mBinding.settingEditBtn;
        btSettingSaveUserName=mBinding.settingSaveBtn;
        tvSettingUserName=mBinding.settingUserNameTv;
        tvSettingUserPhone=mBinding.settingUserPhoneTv;
        ivSettingProfileImage=mBinding.settingUserProfileImage;
        etSettingUserName=mBinding.settingUserNameEt;
        mProgressBar=mBinding.progress;

    }

}