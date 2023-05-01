package com.gmail.orlandroyd.ohcc.networking.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by OrlanDroyd on 09/05/2019.
 */
public class Especialista {

    @SerializedName("id_especialista")
    private int id_especialista;

    @SerializedName("categoria")
    private String categoria;

    public Especialista(int id_especialista, String categoria) {
        this.id_especialista = id_especialista;
        this.categoria = categoria;
    }

    public int getId_especialista() {
        return id_especialista;
    }

    public String getCategoria() {
        return categoria;
    }
}
