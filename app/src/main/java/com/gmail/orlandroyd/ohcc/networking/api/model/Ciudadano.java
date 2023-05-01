package com.gmail.orlandroyd.ohcc.networking.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by OrlanDroyd on 09/05/2019.
 */
public class Ciudadano {

    @SerializedName("id_usuario")
    private int id_usuario;

    @SerializedName("ci")
    private String ci;

    @SerializedName("dir_particular")
    private String dir_particular;

    @SerializedName("telef")
    private String telef;

    @SerializedName("email")
    private String email;

    @SerializedName("id_tipo_ciudadano")
    private int id_tipo_ciudadano;

    public Ciudadano(int id_usuario, String ci, String dir_particular, String telef, String email, int id_tipo_ciudadano) {
        this.id_usuario = id_usuario;
        this.ci = ci;
        this.dir_particular = dir_particular;
        this.telef = telef;
        this.email = email;
        this.id_tipo_ciudadano = id_tipo_ciudadano;
    }

    public Ciudadano() {
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public String getCi() {
        return ci;
    }

    public String getDir_particular() {
        return dir_particular;
    }

    public String getTelef() {
        return telef;
    }

    public String getEmail() {
        return email;
    }

    public int getId_tipo_ciudadano() {
        return id_tipo_ciudadano;
    }
}
