package com.example.blockchainapp.Utils;

import com.example.blockchainapp.Constants;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    public static Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    public static BlockchainInterface blockchainInterface = retrofit.create(BlockchainInterface.class);

    public static void GetBalance() {
        //Constants.BALANCE = Long.valueOf(1000);

        Call<Long> balanceCall = RetrofitUtils.blockchainInterface.ExecuteGetBalance(Constants.PUBLIC_KEY);
        balanceCall.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {

                if (response.code() == 200) {
                    Constants.BALANCE = Long.valueOf(response.code());
                } else {
                    Constants.BALANCE = Long.valueOf(1000);
                    System.out.println("Balance: " + Constants.BALANCE);
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                System.out.println("Error obtaining balance.");
                Constants.BALANCE = Long.valueOf(1000);
            }
        });


    }

}
