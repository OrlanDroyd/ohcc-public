package com.gmail.orlandroyd.ohcc.networking.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by OrlanDroyd on 09/05/2019.
 */
public class JoinEspecialista {

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

    @SerializedName("id_especialista")
    private int id_especialista;

    @SerializedName("categoria")
    private String categoria;

    public JoinEspecialista() {
    }

    public JoinEspecialista(int id_usuario, String name, String lastName1, String lastName2, String user_name, String password, int id_rol, int id_especialista, String categoria) {
        this.id_usuario = id_usuario;
        this.name = name;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.user_name = user_name;
        this.password = password;
        this.id_rol = id_rol;
        this.id_especialista = id_especialista;
        this.categoria = categoria;
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

    public int getId_especialista() {
        return id_especialista;
    }

    public String getCategoria() {
        return categoria;
    }
}
