package com.lovelycoding.automobile.ui.home.brand.bike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;

import com.lovelycoding.automobile.R;
import com.lovelycoding.automobile.databinding.ActivityBrandBinding;
import com.lovelycoding.automobile.datamodel.BrandRvItem;
import com.lovelycoding.automobile.repository.GetDataFromFireBase;
import com.lovelycoding.automobile.ui.home.brand.adapter.BrandAdapter;
import com.lovelycoding.automobile.ui.home.brand.adapter.BrandListener;
import com.lovelycoding.automobile.ui.home.brand.adapter.GridSpacingItemDecoration;
import com.lovelycoding.automobile.ui.dialogfragment.AddNewBrad;
import com.lovelycoding.automobile.ui.home.SelectItemCallback;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BrandActivity extends AppCompatActivity implements BrandListener, BrandListCallback, SearchView.OnQueryTextListener {
    private static final String TAG = "BrandActivity";
    ActivityBrandBinding dataBinding;
    BrandActivityViewModel mViewModel;
    Toolbar toolbar;
    ProgressBar mProgress;
    private RecyclerView mRecycleView;
    private BrandAdapter mAdapter;
    private static List<BrandRvItem> list = new ArrayList<>();
    private  List<BrandRvItem> mBrandList =new ArrayList<>();
    private static Set<String> brnadNameSet = new HashSet<>();
    private static SelectItemCallback mCallback;
    public static String motorType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding= DataBindingUtil.setContentView(this,R.layout.activity_brand);
        mViewModel= ViewModelProviders.of(this).get(BrandActivityViewModel.class);
        mProgress=dataBinding.progressbar;
        mAdapter = new BrandAdapter(this);
        getIntentValue();
        setBrandActivityInterface(mCallback);
        initToolbar();
        initRecycleView();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    public static void setBrandActivityInterface(SelectItemCallback callback) {
        mCallback=callback;
    }


    private void getIntentValue() {
        motorType=getIntent().getStringExtra("motorType");
        downloadDataFromDatabase();
    }

    private void downloadDataFromDatabase() {
        showProgressBar();
        GetDataFromFireBase.getInstance().getBrandListFromDatabase(this,this,motorType);
    }

    private void initToolbar() {
        toolbar=dataBinding.brandToolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }



    private void initRecycleView() {

        mRecycleView=dataBinding.rvBrand;
        mAdapter.setBrandRvItemList(mBrandList);
        mRecycleView.setLayoutManager(new GridLayoutManager(this,4));
        mRecycleView.addItemDecoration(new GridSpacingItemDecoration(4, dpToPx(10), false));
        mRecycleView.setNestedScrollingEnabled(false);
        mRecycleView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


    }
    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.barnd_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search_item);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);


       /* SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
       SearchView searchView = (SearchView) menu.findItem(R.id.search_item)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);*/
        if(searchView!=null) {
            searchView.setOnQueryTextListener(this);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.add_new_brand:
            DialogFragment dialogFragment=new AddNewBrad("Add Brand");
            dialogFragment.show(getSupportFragmentManager().beginTransaction(),"dialog fragment");
            break;
            case R.id.search_item:
            {
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
   @Override
    protected void onResume() {
        super.onResume();

    }
    private void showProgressBar() {
        mProgress.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void itemClicked(int position) {

        mCallback.getSelectBrandItem(mBrandList.get(position));
        finish();
    }

    @Override
    public void getOnBrandList(List<BrandRvItem> mBrandList) {

        this.mBrandList=mBrandList;
        initRecycleView();
        hideProgressBar();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //mAdapter
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
