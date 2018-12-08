package com.tgc.zhihudaily.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static final String BASE_URL = "https://news-at.zhihu.com/";
    public ApiService service;
    private static Api instance = new Api();

    // 饿汉式
    public static Api getInstance() {
        return instance;
    }

    private Api() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(8, TimeUnit.SECONDS)
                .connectTimeout(8, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        service = retrofit.create(ApiService.class);
    }
}
