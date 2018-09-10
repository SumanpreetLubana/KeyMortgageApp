package com.example.smartserve.keymortgageapp.Util;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.POST;

public interface ApiService {

    // Call<ResponseBody> postMeme(@Body RequestBody files);
//    @POST("createJob")
//    Call<ResponseBody> updateAlbum(@Body RequestBody files, @FieldMap Map<String, String> ablumids);
    @POST("docUpload")
    Call<ResponseBody> postMeme(@Body RequestBody files);
}