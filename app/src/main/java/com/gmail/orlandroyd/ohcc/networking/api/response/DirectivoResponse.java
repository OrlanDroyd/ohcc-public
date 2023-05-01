package com.gmail.orlandroyd.ohcc.networking.api.response;

import com.gmail.orlandroyd.ohcc.networking.api.model.Directivo;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OrlanDroyd on 16/05/2019.
 */
public class DirectivoResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private Directivo data;

    public DirectivoResponse(boolean error, Directivo data) {
        this.error = error;
        this.data = data;
    }

    public DirectivoResponse() {
    }

    public boolean isError() {
        return error;
    }

    public Directivo getData() {
        return data;
    }
}
