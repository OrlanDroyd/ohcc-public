package com.gmail.orlandroyd.ohcc.glide.model;

import java.io.Serializable;

/**
 * Created by OrlanDroyd on 2/3/2019.
 */
public class Image implements Serializable {
    private String title;
    private String grado;
    private int dir;

    public String getGrado() {
        return grado;
    }

    public String getTitle() {
        return title;
    }

    public int getDir() {
        return dir;
    }

    public Image(String title, String grado, int dir) {
        this.title = title;
        this.grado = grado;
        this.dir = dir;
    }
}
