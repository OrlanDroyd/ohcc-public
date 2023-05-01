package com.gmail.orlandroyd.ohcc.networking.api.response;

import com.gmail.orlandroyd.ohcc.networking.api.model.JoinEspecialista;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by OrlanDroyd on 16/05/2019.
 */
public class JoinEspecialistaResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private List<JoinEspecialista> data;

    public JoinEspecialistaResponse() {
    }

    public JoinEspecialistaResponse(boolean error, List<JoinEspecialista> data) {
        this.error = error;
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public List<JoinEspecialista> getData() {
        return data;
    }
}
