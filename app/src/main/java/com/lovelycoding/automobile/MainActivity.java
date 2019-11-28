package com.lovelycoding.automobile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.lovelycoding.automobile.repository.SaveDataIntoFireBase;
import com.lovelycoding.automobile.ui.login.LoginActivity;
import com.lovelycoding.automobile.util.DatabaseRef;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity  {
    Toolbar toolbar;
    private static final String TAG = "MainActivity";
    public static RuntimePermission permission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            Intent intent=new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else {
            DatabaseRef.fireBaseInstance(MainActivity.this);
            Log.d(TAG, "onCreate: "+ DatabaseRef.mAuth);
            Log.d(TAG, "onCreate: "+ DatabaseRef.mAuth);
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_settings)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);
        }
      //  getDataFromFireBase();

    }



}
