package com.gmail.orlandroyd.ohcc.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuario")
public class UsuarioEntity {

    @PrimaryKey(autoGenerate = true)
    private int id_usuario;

    private String nombre;
    private String apellido1;
    private String apellido2;
    private String nombreUsuario;
    private String password;
    private int idRol;

    public UsuarioEntity(String nombre, String apellido1, String apellido2, String nombreUsuario, String password, int idRol) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.idRol = idRol;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public int getIdRol() {
        return idRol;
    }
}
