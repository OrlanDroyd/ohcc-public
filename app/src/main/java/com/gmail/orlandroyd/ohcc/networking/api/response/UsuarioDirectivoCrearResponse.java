package com.gmail.orlandroyd.ohcc.networking.api.response;

import com.gmail.orlandroyd.ohcc.networking.api.model.Directivo;
import com.gmail.orlandroyd.ohcc.networking.api.model.Usuario;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OrlanDroyd on 16/05/2019.
 */
public class UsuarioDirectivoCrearResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("data_usuario")
    private Usuario data_usuario;

    @SerializedName("data_directivo")
    private Directivo data_directivo;

    public UsuarioDirectivoCrearResponse(boolean error, String message, Usuario data_usuario, Directivo data_directivo) {
        this.error = error;
        this.message = message;
        this.data_usuario = data_usuario;
        this.data_directivo = data_directivo;
    }

    public UsuarioDirectivoCrearResponse() {
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

    public Directivo getData_directivo() {
        return data_directivo;
    }
}
