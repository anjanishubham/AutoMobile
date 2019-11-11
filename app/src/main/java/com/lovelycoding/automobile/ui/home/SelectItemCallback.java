package com.lovelycoding.automobile.ui.home;

import com.lovelycoding.automobile.datamodel.BrandRvItem;
import com.lovelycoding.automobile.datamodel.PcategoryRvItem;

public interface SelectItemCallback {
    public void getSelectCategoryItem(PcategoryRvItem ob);
    public void getSelectBrandItem(BrandRvItem ob);
}
