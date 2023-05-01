package com.gmail.orlandroyd.ohcc.adapters.adapter_models;


import com.gmail.orlandroyd.ohcc.util.ResourcesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OrlanDroyd on 10/04/2019.
 */
public class IndexItem {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static List<IndexItem> getList() {
        List<IndexItem> dataList = new ArrayList<>();
        String[] titles = ResourcesUtil.titlesDocumentsIndex;
        for (int i = 0; i < ResourcesUtil.titlesDocumentsIndex.length; i++) {
            IndexItem item = new IndexItem();
            item.setTitle(titles[i]);
            dataList.add(item);
        }
        return dataList;
    }
}
