package com.gmail.orlandroyd.ohcc.networking.api.generator;

import com.gmail.orlandroyd.ohcc.networking.Routes;
import com.gmail.orlandroyd.ohcc.networking.api.services.ServiceApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by OrlanDroyd on 15/04/2019.
 */
public class RetrofitClient {
    private static final String BASE_URL = Routes.BASE_URL;
    private static RetrofitClient mInstance;
    private static Retrofit retrofit;

    private RetrofitClient() {
        Gson gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setPrettyPrinting()
                .setVersion(1.0)
                .create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(90, TimeUnit.SECONDS)
                .connectTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .cache(null);


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) mInstance = new RetrofitClient();
        return mInstance;
    }

    public ServiceApi getApi() {
        return retrofit.create(ServiceApi.class);
    }
}
