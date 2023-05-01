package com.gmail.orlandroyd.ohcc.networking.api.response;

import com.gmail.orlandroyd.ohcc.networking.api.model.Ciudadano;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OrlanDroyd on 16/05/2019.
 */
public class CiudadanoResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private Ciudadano data;

    public CiudadanoResponse(boolean error, Ciudadano data) {
        this.error = error;
        this.data = data;
    }

    public CiudadanoResponse() {
    }

    public boolean isError() {
        return error;
    }

    public Ciudadano getData() {
        return data;
    }
}
