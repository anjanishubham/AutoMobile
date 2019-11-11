package com.lovelycoding.automobile.ui.home.category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.lovelycoding.automobile.R;
import com.lovelycoding.automobile.databinding.ActivityCategoryBinding;
import com.lovelycoding.automobile.datamodel.PcategoryRvItem;
import com.lovelycoding.automobile.ui.home.brand.adapter.GridSpacingItemDecoration;
import com.lovelycoding.automobile.ui.home.brand.dialogfragment.AddNewBrad;
import com.lovelycoding.automobile.ui.home.brand.dialogfragment.NetworkNotFound;
import com.lovelycoding.automobile.ui.home.category.adapter.CategoryAdapter;
import com.lovelycoding.automobile.ui.home.category.adapter.CategoryListener;
import com.lovelycoding.automobile.ui.home.SelectItemCallback;
import com.lovelycoding.automobile.util.App;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements CategoryListener, SearchView.OnQueryTextListener {
    private static final String TAG = "CategoryActivity";
    private ActivityCategoryBinding binding;
    private RecyclerView mRecycleView;
    private CategoryAdapter mAdapter;
    private Toolbar mToolbar;
    public static String motorType;
    private CategoryViewModel mViewModel;
    private ProgressBar mProgressBar;

    private static SelectItemCallback mCallback;

    List<PcategoryRvItem> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_category);
        mProgressBar=binding.progressbar;
        mViewModel= ViewModelProviders.of(this).get(CategoryViewModel.class);
        getIntentValue();
        mRecycleView=binding.rvProductCategory;
        mAdapter=new CategoryAdapter(this);
        initRecycleView();
        iniToolbar();
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void iniToolbar() {
        mToolbar=binding.brandCategoryToolbar;
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("Category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initRecycleView() {
        showProgressBar();
        mAdapter.setCategoryList(mList);
        mRecycleView.setLayoutManager(new GridLayoutManager(getBaseContext(),4));
        mRecycleView.addItemDecoration(new GridSpacingItemDecoration(4, dpToPx(10), false));
        mRecycleView.setNestedScrollingEnabled(false);
        mRecycleView.setAdapter(mAdapter);
        if(App.isNetworkAvailable()){
            mViewModel.getCategoryList(mAdapter,motorType,mProgressBar);
        }
        else {
            NetworkNotFound networkNotFound = new NetworkNotFound(this);
            networkNotFound.show(getSupportFragmentManager(),"network");
        }
    }

    private void getIntentValue() {
        motorType=getIntent().getStringExtra("motorType");
    }

    public static void getCallBackInterfaceObject(SelectItemCallback callback) {
        mCallback=callback;
    }

   // recycleView callback function return selected posing
    @Override
    public void getCategoryPosition(int position) {
        //Log.d(TAG, "getCategoryPosition: ");

        mCallback.getSelectCategoryItem(mAdapter.getCurrentSelectedItem(position));
        finish();
    }
    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.barnd_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search_item);
        SearchView searchView=(SearchView)menuItem.getActionView();
        if (searchView != null) {
            searchView.setOnQueryTextListener(this);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.add_new_brand){
            DialogFragment dialogFragment=new AddNewBrad("Add Category ");
            dialogFragment.show(getSupportFragmentManager().beginTransaction(),"dialog fragment");
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "onQueryTextSubmit: "+query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(TAG, "onQueryTextChange: "+newText);
        mAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
