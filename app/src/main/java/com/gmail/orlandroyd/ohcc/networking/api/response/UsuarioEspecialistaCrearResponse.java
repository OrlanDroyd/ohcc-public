package com.gmail.orlandroyd.ohcc.networking.api.response;

import com.gmail.orlandroyd.ohcc.networking.api.model.Especialista;
import com.gmail.orlandroyd.ohcc.networking.api.model.Usuario;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OrlanDroyd on 19/05/2019.
 */
public class UsuarioEspecialistaCrearResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("data_usuario")
    private Usuario usuario;

    @SerializedName("data_especialista")
    private Especialista especialista;

    public UsuarioEspecialistaCrearResponse(boolean error, String message, Usuario usuario, Especialista especialista) {
        this.error = error;
        this.message = message;
        this.usuario = usuario;
        this.especialista = especialista;
    }

    public UsuarioEspecialistaCrearResponse() {
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Especialista getEspecialista() {
        return especialista;
    }
}
