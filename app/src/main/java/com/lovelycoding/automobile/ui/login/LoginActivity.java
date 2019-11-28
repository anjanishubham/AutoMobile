package com.lovelycoding.automobile.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.lovelycoding.automobile.R;
import com.lovelycoding.automobile.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding loginBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        MyLoginClickHandler myLoginClickHandler = new MyLoginClickHandler(this, loginBinding);
        loginBinding.setHandler(myLoginClickHandler);

    }
}
