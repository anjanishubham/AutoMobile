package com.lovelycoding.automobile.ui.login;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lovelycoding.automobile.MainActivity;
import com.lovelycoding.automobile.databinding.ActivityLoginBinding;
import com.lovelycoding.automobile.datamodel.Product;
import com.lovelycoding.automobile.util.App;
import com.lovelycoding.automobile.util.Constant;

public class MyLoginClickHandler {
    private static final String TAG = "MyLoginClickHandler";
    Context context;
    ActivityLoginBinding databinding;
    public MyLoginClickHandler(Context context, ActivityLoginBinding adminLogin)
    {
        this.context=context;
        this.databinding=adminLogin;
    }

    public void onLoginButtonClick(View view){

        loginWithFirebase();
    }

    private void loginWithFirebase() {

        String emailId=databinding.tiedPhoneNumber.getText().toString().trim();
        String password=databinding.tiedPhonePassword.getText().toString().trim();

        if(!emailId.isEmpty()&&!password.isEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(emailId,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent=new Intent(context, MainActivity.class);
                                 context.startActivity(intent);
                            } else {
                                Log.d(TAG, "onComplete: " + task.getException().getLocalizedMessage());
                            }
                        }
                    });
        }
        else {
            if(emailId.isEmpty())
            {
                databinding.tilPhoneNumber.setError("Email is empty");
            }
            else {
                databinding.tiedPhonePassword.setError("Password is empty ");
            }
        }
    }


}
