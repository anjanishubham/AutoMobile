package com.lovelycoding.automobile.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.lovelycoding.automobile.R;
import com.lovelycoding.automobile.databinding.ActivityPhoneLoginBinding;

import java.util.concurrent.TimeUnit;

public class PhoneLoginActivity extends AppCompatActivity  {
    private static final String TAG = "PhoneLoginActivity";
    private ActivityPhoneLoginBinding mDataBinding;
    TextInputEditText etPhoneNumber,etOtpCode;
    MaterialButton btnOtp,btnLogin;
    TextInputLayout tilPhoneNumber,tilOtpCode;

    //var
    private String mVerificationId;
    private FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private ProgressDialog mProgress;
    PhoneAuthProvider.ForceResendingToken mResendToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_phone_login);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_phone_login);
        mAuth=FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        initView();
        initProgressBar();

        btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyPhoneNumber()) {
                    mProgress.show();
                    sentVerificationCode();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void initProgressBar() {
        mProgress.setTitle("OTP");
        mProgress.setMessage("Please Wait for OTP...");
        mProgress.setCanceledOnTouchOutside(false);
    }

    private void signInWithPhoneAuthCredential(String code) {

        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,code);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(PhoneLoginActivity.this, "LoginSucessfull ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sentVerificationCode()
    {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                Log.d(TAG, "onVerificationCompleted:" + credential);
                String code=credential.getSmsCode();
                //  mProgress.dismiss();
                if(code!=null){
                    etOtpCode.setText(code);
                    signInWithPhoneAuthCredential(code);
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                Toast.makeText(PhoneLoginActivity.this, "Code has been sent, please check and verify...", Toast.LENGTH_SHORT).show();
                mVerificationId = verificationId;
                mResendToken=token;
                mProgress.dismiss();


            }
        };
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+etPhoneNumber.getText().toString().trim(),        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }


    private boolean verifyPhoneNumber() {
        if(etPhoneNumber.getText().toString().trim().length()<9){
            tilPhoneNumber.setError("Please enter valid phone number ");
            etPhoneNumber.setFocusable(true);
            return false;
        }
        tilPhoneNumber.setError("");
        return true;



    }

    private void initView() {
        etPhoneNumber=mDataBinding.etPhoneNumber;
        etOtpCode=mDataBinding.etVerificationCode;
        btnOtp=mDataBinding.btVerifyOtp;
        btnLogin=mDataBinding.btLogin;
        tilPhoneNumber=mDataBinding.tilPhoneLoginEmail;
        tilOtpCode=mDataBinding.tilOtpVerify;
    }
}
