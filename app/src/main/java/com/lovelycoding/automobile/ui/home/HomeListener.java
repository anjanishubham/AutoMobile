package com.lovelycoding.automobile.ui.home;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import com.lovelycoding.automobile.databinding.FragmentHomeBinding;
import com.lovelycoding.automobile.datamodel.Product;
import com.lovelycoding.automobile.ui.home.brand.bike.BrandActivity;
import com.lovelycoding.automobile.ui.home.category.CategoryActivity;
import com.lovelycoding.automobile.util.App;
public class HomeListener {
    private static final String TAG = "HomeListener";
    RuntimePermission mPermission;
    private HomeFragmentModel mFModel;
    private FragmentHomeBinding binding;
    private SelectItemCallback callbackInterface;

    public interface RuntimePermission{
        public void checkRuntimePermission();
    }

    public HomeListener(Context context, SelectItemCallback callbackInterface, FragmentHomeBinding dataBinding, RuntimePermission mPermission) {
        mFModel=new HomeFragmentModel(context,dataBinding);
        this.mPermission=mPermission;
        this.binding=dataBinding;
        this.callbackInterface=callbackInterface;
    }

    public void onClickProductType(View view){

        Log.d(TAG, "getProductType: ");
    }

    public void onClickSaveToDatabase(View view) {
        if(mFModel.verifyInput()) {
            mFModel.saveProductImageToStorage(HomeFragment.getUriList());
        }
        Log.d(TAG, "saveProductDetailIntoDatabase: ");
    }

    private void saveProductIntoDatabase(Product product)
    {
        App.mRootDatabaseRef.child(App.mAuth.getUid().toString()).child(product.getProductId()).setValue(product);
    }


    public void onSelectBrand(View view) {
        Intent intent=new Intent(view.getContext(),BrandActivity.class);
        intent.putExtra("motorType",binding.spinnerMotorType.getSelectedItem().toString());
        view.getContext().startActivity(intent);
        BrandActivity.setBrandActivityInterface(callbackInterface);
    }

    public void onSelectCategory(View view) {
        Intent intent=new Intent(view.getContext(), CategoryActivity.class);
        intent.putExtra("motorType",binding.spinnerMotorType.getSelectedItem().toString());
        //intent.putExtra("selectItemCallback", (Parcelable) selectItemCallback);
        view.getContext().startActivity(intent);
        CategoryActivity.getCallBackInterfaceObject(callbackInterface);
    }

    public void addProductCount(View view) {

        int count =(Integer.valueOf(binding.productCount.getText().toString()));
        count++;
        binding.productCount.setText(count+"");
        if(binding.tvMinus.getVisibility()==View.GONE)
            binding.tvMinus.setVisibility(View.VISIBLE);

    }

    public void onClickOnRecentIV(View view) {
        Intent intent=new Intent(view.getContext(), CategoryActivity.class);
        intent.putExtra("motorType",binding.spinnerMotorType.getSelectedItem().toString());
        //intent.putExtra("selectItemCallback", (Parcelable) selectItemCallback);
        view.getContext().startActivity(intent);
    }

    public void minusProductCount(View view) {

        int count =(Integer.valueOf(binding.productCount.getText().toString()));
        if(count>1)
        {
            count--;
            binding.productCount.setText(count+"");

        }
        else if(count==1)
        {
            count--;
            binding.productCount.setText(count+"");
            binding.tvMinus.setVisibility(View.GONE);
        }
    }

    public void onImageSelect(View view) {
        mPermission.checkRuntimePermission();
    }
    //callback function
    public void onClickSelectImage(View View) {
        mPermission.checkRuntimePermission();
    }






}
