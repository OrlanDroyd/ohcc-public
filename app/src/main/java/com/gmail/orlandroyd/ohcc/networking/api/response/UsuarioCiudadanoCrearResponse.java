package com.gmail.orlandroyd.ohcc.networking.api.response;

import com.gmail.orlandroyd.ohcc.networking.api.model.Ciudadano;
import com.gmail.orlandroyd.ohcc.networking.api.model.Usuario;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OrlanDroyd on 16/05/2019.
 */
public class UsuarioCiudadanoCrearResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("data_usuario")
    private Usuario data_usuario;

    @SerializedName("data_ciudadano")
    private Ciudadano data_ciudadano;

    public UsuarioCiudadanoCrearResponse(boolean error, String message, Usuario data_usuario, Ciudadano data_ciudadano) {
        this.error = error;
        this.message = message;
        this.data_usuario = data_usuario;
        this.data_ciudadano = data_ciudadano;
    }

    public UsuarioCiudadanoCrearResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public UsuarioCiudadanoCrearResponse() {
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Usuario getData_usuario() {
        return data_usuario;
    }

    public Ciudadano getData_ciudadano() {
        return data_ciudadano;
    }
}
