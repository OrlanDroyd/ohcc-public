package com.gmail.orlandroyd.ohcc.networking.api.response;

import com.gmail.orlandroyd.ohcc.networking.api.model.Solicitud;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by OrlanDroyd on 16/05/2019.
 */
public class SolicitudGetAllResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private List<Solicitud> data;

    public SolicitudGetAllResponse() {
    }

    public SolicitudGetAllResponse(boolean error, List<Solicitud> data) {
        this.error = error;
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public List<Solicitud> getData() {
        return data;
    }
}