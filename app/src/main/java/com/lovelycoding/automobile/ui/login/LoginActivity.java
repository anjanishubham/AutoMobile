package com.lovelycoding.automobile.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.lovelycoding.automobile.MainActivity;
import com.lovelycoding.automobile.R;
import com.lovelycoding.automobile.databinding.ActivityLoginBinding;
import com.lovelycoding.automobile.util.App;

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
