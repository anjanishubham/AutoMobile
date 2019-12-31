package com.lovelycoding.automobile.ui.category.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lovelycoding.automobile.R;
import com.lovelycoding.automobile.databinding.CategoryRvItemBinding;
import com.lovelycoding.automobile.datamodel.PcategoryRvItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> implements Filterable {
    private static final String TAG = "CategoryAdapter";
    List<PcategoryRvItem> mCategoryList;
    CategoryListener mListener;
    List<PcategoryRvItem> mCategoryFilterList;

    public CategoryAdapter() {
        mCategoryList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryRvItemBinding databind= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.category_rv_item,parent,false);
        return  new CategoryViewHolder(databind,mListener);
    }
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        ((CategoryViewHolder)holder).dataBinding.setModel(mCategoryList.get(position));
    }
    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public void setCategoryList(List<PcategoryRvItem> mList) {
        this.mCategoryList =mList;
        mCategoryFilterList = new ArrayList<>(mList);
    }

    public PcategoryRvItem getCurrentSelectedItem(int position) {
        if(mCategoryList.size()>=position){
            return mCategoryList.get(position);
        }
        else return null;
    }

    @Override
    public Filter getFilter() {


        return filterResult;
    }
    private Filter filterResult=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<PcategoryRvItem> filterList = new ArrayList<>();

            String filterPattern=charSequence.toString();

            Log.d(TAG, "performFiltering: "+filterPattern +"boolean"+filterPattern.isEmpty());
            if (filterPattern.isEmpty() || filterPattern==null) {
                filterList=mCategoryFilterList;
            }
            else {
                filterPattern.toLowerCase().trim();
                for(PcategoryRvItem item:mCategoryFilterList){
                    if(item.getCategoryItemName().toLowerCase().contains(filterPattern)){
                        filterList.add(item);
                    }
                }

            }

            FilterResults results=new FilterResults();
            results.values=filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mCategoryList.clear();
            mCategoryList.addAll((List)filterResults.values);
            Log.d(TAG, "performFiltering: "+mCategoryList.size());

            notifyDataSetChanged();
        }
    };
}
