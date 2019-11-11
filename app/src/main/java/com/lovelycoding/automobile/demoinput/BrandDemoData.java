package com.lovelycoding.automobile.demoinput;

import com.lovelycoding.automobile.datamodel.BrandRvItem;
import com.lovelycoding.automobile.datamodel.PcategoryRvItem;

import java.util.ArrayList;
import java.util.List;

public class BrandDemoData {

    public static List<PcategoryRvItem> mList = new ArrayList<>();

    public static void createCustomValue() {
        for (int i = 0; i < 20; i++) {
            PcategoryRvItem ob=new PcategoryRvItem("https://firebasestorage.googleapis.com/v0/b/automobile-5b009.appspot.com/o/_product_brand_image%2FNew%20bike%2F1572317126565.PNG?alt=media&token=697516ea-d8de-4b8a-b00b-3a55d2f4ac7b","HERO"+i);
            mList.add(ob);
        }
    }
}
