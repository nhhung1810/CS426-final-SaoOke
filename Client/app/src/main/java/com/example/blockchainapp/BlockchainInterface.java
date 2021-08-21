package com.example.blockchainapp;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BlockchainInterface {

    @POST("/transaction")
    Call<Boolean> ExecutePostTransaction(@Body Transaction transaction);

    @POST("/balance")
    Call<Long> ExecuteGetBalance(@Body PublicKey address);

    @POST("/register")
    Call<UserKey> ExecutePostRegister(@Body UserAccount account);

}
