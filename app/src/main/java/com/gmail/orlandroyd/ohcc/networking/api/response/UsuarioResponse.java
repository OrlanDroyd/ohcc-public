package com.gmail.orlandroyd.ohcc.networking.api.response;

import com.gmail.orlandroyd.ohcc.networking.api.model.Usuario;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by OrlanDroyd on 16/05/2019.
 */
public class UsuarioResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private List<Usuario> data;

    public UsuarioResponse(boolean error, List<Usuario> data) {
        this.error = error;
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public List<Usuario> getData() {
        return data;
    }

    public UsuarioResponse() {
    }
}
