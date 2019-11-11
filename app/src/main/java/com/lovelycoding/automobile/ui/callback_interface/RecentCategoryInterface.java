package com.lovelycoding.automobile.ui.callback_interface;

import com.lovelycoding.automobile.datamodel.BrandRvItem;
import com.lovelycoding.automobile.datamodel.PcategoryRvItem;

import java.util.List;

public interface RecentCategoryInterface {

    public void readRecentCategoryData(List<PcategoryRvItem> mRecentCategoryList);

    public void readRecentBrandData(List<BrandRvItem> mRecentBrandList);
    public void updateRecentBrandData(BrandRvItem mBrandItem);
    public void updateRecentCategoryData(PcategoryRvItem mCategoryItem);

}
