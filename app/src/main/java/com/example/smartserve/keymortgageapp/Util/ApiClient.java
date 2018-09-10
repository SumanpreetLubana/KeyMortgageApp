package com.example.smartserve.keymortgageapp.Util;

import com.example.smartserve.keymortgageapp.Util.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String API_BASE_URL = "http://keymortgage.co/app/api/document/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    public static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(150, TimeUnit.SECONDS).writeTimeout(150, TimeUnit.SECONDS).build();
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create());

    public static ApiService createService(Class<ApiService> serviceClass)
    {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}

