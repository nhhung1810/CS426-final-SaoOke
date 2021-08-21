package com.example.blockchainapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BlockchainInterface {

    @POST("/transaction")
    Call<Boolean> ExecutePostTransaction(@Body Transaction transaction);

    @GET("/balance")
    Call<Float> ExecuteGetBalance(@Body String privateKey);

    @POST("/register")
    Call<UserKey> ExecutePostRegister(@Body UserAccount account);

}
