package com.example.blockchainapp.Utils;

import com.example.blockchainapp.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    public static Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    public static BlockchainInterface blockchainInterface = retrofit.create(BlockchainInterface.class);

    public static 

}
