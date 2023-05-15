package com.android.mymusic.service;

import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String base_url){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                    .readTimeout(100000, TimeUnit.MILLISECONDS)
                                    .writeTimeout(100000,TimeUnit.MILLISECONDS)
                                    .connectTimeout(100000,TimeUnit.MILLISECONDS)
                                    .retryOnConnectionFailure(true)
                                    .protocols(Arrays.asList(Protocol.HTTP_1_1))
                                    .build();

        new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                        .baseUrl(base_url)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        return retrofit;
    }
}
