package com.gmail.orlandroyd.ohcc.networking.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by OrlanDroyd on 09/05/2019.
 */
public class JoinCiudadano {

    @SerializedName("id_usuario")
    private int id_usuario;

    @SerializedName("nombre")
    private String name;

    @SerializedName("apellido_1")
    private String lastName1;

    @SerializedName("apellido_2")
    private String lastName2;

    @SerializedName("nombre_usuario")
    private String user_name;

    @SerializedName("password")
    private String password;

    @SerializedName("id_rol")
    private int id_rol;

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

    @SerializedName("latitud_vivienda")
    private String latitud_vivienda;

    @SerializedName("longitud_vivienda")
    private String longitud_vivienda;

    public JoinCiudadano() {
    }

    public JoinCiudadano(int id_usuario, String name, String lastName1, String lastName2, String user_name, String password, int id_rol, String ci, String dir_particular, String telef, String email, int id_tipo_ciudadano, String latitud_vivienda, String longitud_vivienda) {
        this.id_usuario = id_usuario;
        this.name = name;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.user_name = user_name;
        this.password = password;
        this.id_rol = id_rol;
        this.ci = ci;
        this.dir_particular = dir_particular;
        this.telef = telef;
        this.email = email;
        this.id_tipo_ciudadano = id_tipo_ciudadano;
        this.latitud_vivienda = latitud_vivienda;
        this.longitud_vivienda = longitud_vivienda;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public String getName() {
        return name;
    }

    public String getLastName1() {
        return lastName1;
    }

    public String getLastName2() {
        return lastName2;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }

    public int getId_rol() {
        return id_rol;
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

    public String getLatitud_vivienda() {
        return latitud_vivienda;
    }

    public String getLongitud_vivienda() {
        return longitud_vivienda;
    }
}
