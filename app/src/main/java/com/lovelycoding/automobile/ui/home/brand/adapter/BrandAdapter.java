package com.lovelycoding.automobile.ui.home.brand.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lovelycoding.automobile.R;
import com.lovelycoding.automobile.databinding.BrandRvItemBinding;
import com.lovelycoding.automobile.datamodel.BrandRvItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BrandAdapter extends RecyclerView.Adapter<BrandViewHolder> implements Filterable {
    private static final String TAG = "BrandAdapter";
    List<BrandRvItem> brandRvItemList;
    List<BrandRvItem> brandFullList;
    private BrandListener mListener;
    public BrandAdapter(BrandListener mListener) {
        brandRvItemList = new ArrayList<>();
        this.mListener=mListener;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BrandRvItemBinding rowItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.brand_rv_item,parent,false);
        return new BrandViewHolder(rowItemBinding,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {

        ((BrandViewHolder)holder).dataBinding.setModel(brandRvItemList.get(position));


        //if(selectBrandItemList.contains(brandRvItemList.get(position)))
    }

    @Override
    public int getItemCount() {
        return brandRvItemList.size();
    }

    public void setBrandRvItemList(List<BrandRvItem> brandRvItemList) {
        this.brandRvItemList = brandRvItemList;
        brandFullList =new ArrayList<>(brandRvItemList);

    }

    @Override
    public Filter getFilter() {
        return resultBrandList;
    }

    private Filter resultBrandList=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<BrandRvItem> mFilterBrandList = new ArrayList<>();
            String filterPattern=charSequence.toString().trim();
            Log.d(TAG, "performFiltering: "+filterPattern);
            if(filterPattern.isEmpty() || filterPattern==null){
                mFilterBrandList= brandFullList;
            }
            else {
                filterPattern.toLowerCase();
                for (BrandRvItem item : brandFullList) {
                   // Log.d(TAG, "performFiltering: "+item.getBrandName().toLowerCase().contains(filterPattern));
                    if(item.getBrandName().toLowerCase().contains(filterPattern))
                    {
                        mFilterBrandList.add(item);
                        Log.d(TAG, "performFiltering 1: "+item.getBrandName());

                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=mFilterBrandList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            brandRvItemList.clear();
            brandRvItemList.addAll((List)filterResults.values);
           Log.d(TAG, "performFiltering: "+brandRvItemList.size());
            notifyDataSetChanged();
        }
    };
}
