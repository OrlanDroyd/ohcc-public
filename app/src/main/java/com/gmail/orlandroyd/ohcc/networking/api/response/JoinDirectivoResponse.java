package com.gmail.orlandroyd.ohcc.networking.api.response;

import com.gmail.orlandroyd.ohcc.networking.api.model.JoinDirectivo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by OrlanDroyd on 16/05/2019.
 */
public class JoinDirectivoResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private List<JoinDirectivo> data;

    public JoinDirectivoResponse() {
    }

    public JoinDirectivoResponse(boolean error, List<JoinDirectivo> data) {
        this.error = error;
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public List<JoinDirectivo> getData() {
        return data;
    }
}
