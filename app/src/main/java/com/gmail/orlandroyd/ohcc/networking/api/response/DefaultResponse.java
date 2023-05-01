package com.gmail.orlandroyd.ohcc.networking.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by OrlanDroyd on 16/05/2019.
 */
public class DefaultResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public DefaultResponse() {
    }

    public DefaultResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}