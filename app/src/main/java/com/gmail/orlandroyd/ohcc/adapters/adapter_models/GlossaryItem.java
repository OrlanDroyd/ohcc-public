package com.gmail.orlandroyd.ohcc.adapters.adapter_models;


/**
 * Created by OrlanDroyd on 10/04/2019.
 */
public class GlossaryItem {
    private String title;
    private String content;

    public GlossaryItem(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public GlossaryItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
