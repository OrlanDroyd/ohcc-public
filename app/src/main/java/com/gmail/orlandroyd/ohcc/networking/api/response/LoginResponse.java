package com.gmail.orlandroyd.ohcc.networking.api.response;

import com.gmail.orlandroyd.ohcc.networking.api.model.Usuario;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OrlanDroyd on 16/05/2019.
 */
public class LoginResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Usuario data;

    public LoginResponse(boolean error, String message, Usuario data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public LoginResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public LoginResponse() {
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Usuario getData() {
        return data;
    }
}
