package com.lovelycoding.automobile.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lovelycoding.automobile.datamodel.UserProfile;
import com.lovelycoding.automobile.repository.GetDataFromFireBase;

public class SettingsViewModel extends ViewModel implements SettingUserProfile {

    private MutableLiveData<String> mText;
    private MutableLiveData<UserProfile> mUserProfileLiveData = new MutableLiveData<>();

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");

    }

    public LiveData<String> getText() {
        return mText;
    }


    public void getUserProfile() {
        GetDataFromFireBase.getInstance().getUserProfile(this);
    }
    @Override
    public void getUserProfile(UserProfile userProfile) {
        mUserProfileLiveData.setValue(userProfile);
    }

    public LiveData<UserProfile> setUserProfile() {
        return mUserProfileLiveData;
    }
}