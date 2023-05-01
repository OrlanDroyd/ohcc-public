package com.gmail.orlandroyd.ohcc.networking.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by OrlanDroyd on 20/05/2019.
 */
public class Directivo {

    @SerializedName("id_directivo")
    private int id_directivo;

    @SerializedName("cargo")
    private String cargo;

    public Directivo(int id_directivo, String cargo) {
        this.id_directivo = id_directivo;
        this.cargo = cargo;
    }

    public Directivo() {
    }

    public int getId_directivo() {
        return id_directivo;
    }

    public String getCargo() {
        return cargo;
    }
}