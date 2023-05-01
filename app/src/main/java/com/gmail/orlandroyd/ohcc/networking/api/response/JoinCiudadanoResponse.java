package com.gmail.orlandroyd.ohcc.networking.api.response;

import com.gmail.orlandroyd.ohcc.networking.api.model.JoinCiudadano;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by OrlanDroyd on 16/05/2019.
 */
public class JoinCiudadanoResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private List<JoinCiudadano> data;

    public JoinCiudadanoResponse() {
    }

    public JoinCiudadanoResponse(boolean error, List<JoinCiudadano> data) {
        this.error = error;
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public List<JoinCiudadano> getData() {
        return data;
    }
}
