package com.gmail.orlandroyd.ohcc.adapters.adapter_models;

import com.gmail.orlandroyd.ohcc.util.ResourcesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OrlanDroyd on 10/04/2019.
 */
public class LibraryItem {
    private int imgID;
    private String title;

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static List<LibraryItem> getList() {
        List<LibraryItem> dataList = new ArrayList<>();
        int[] images = ResourcesUtil.thumnailDocuments;
        String[] titles = ResourcesUtil.titlesDocuments;
        for (int i = 0; i < images.length; i++) {
            LibraryItem item = new LibraryItem();
            item.setImgID(images[i]);
            item.setTitle(titles[i]);
            dataList.add(item);
        }
        return dataList;
    }
}
