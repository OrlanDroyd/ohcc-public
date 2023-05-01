package com.gmail.orlandroyd.ohcc.networking.api.response;

import com.gmail.orlandroyd.ohcc.networking.api.model.Especialista;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OrlanDroyd on 16/05/2019.
 */
public class EspecialistaResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private Especialista data;

    public boolean isError() {
        return error;
    }

    public Especialista getData() {
        return data;
    }

    public EspecialistaResponse(boolean error, Especialista data) {
        this.error = error;
        this.data = data;
    }
}
