package com.example.blockchainapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BlockchainInterface {

    @POST("/transaction")
    Call<Boolean> ExecutePostTransaction(@Body Transaction transaction);

    @GET("/balance")
    Call<Float> ExecuteGetBalance(@Query("key") String privateKey);

    @POST("/register")
    Call<UserKey> ExecutePostRegister(@Body UserAccount account);

}
