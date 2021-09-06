package com.example.blockchainapp.Utils;

import android.util.Log;

import com.example.blockchainapp.Account.PublicKey;
import com.example.blockchainapp.Campaign.Campaign;
import com.example.blockchainapp.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import jnr.constants.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitUtils {

    public static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    public static BlockchainInterface blockchainInterface = retrofit.create(BlockchainInterface.class);

    public static void GetBalance() {
        //Constants.BALANCE = Long.valueOf(1000);

        Call<Long> balanceCall = RetrofitUtils.blockchainInterface.ExecuteGetBalance(Constants.USERNAME);
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
                System.out.println(t.getMessage());
                Constants.BALANCE = Long.valueOf(1000);
            }
        });
    }

    public static void LoadCampaignNames(String username) {


        Call<Campaign[]> logCall = RetrofitUtils.blockchainInterface.ExecuteGetCampaignsByUser(username);
        logCall.enqueue(new Callback<Campaign[]>() {
            @Override
            public void onResponse(Call<Campaign[]> call, Response<Campaign[]> response) {
                if (response.code() == 200) {
                    // Log.d("log callback",response.body().toString());
                    Campaign[] campaigns = response.body();
                    ArrayList<String> campaignNames = new ArrayList<>();
                    for (int i = 0; i < campaigns.length; ++i) {
                        campaignNames.add(campaigns[i].getCampaignName());
                    }
                    Constants.USER_CAMPAIGN_LIST = new String[campaignNames.size()];
                    campaignNames.toArray(Constants.USER_CAMPAIGN_LIST);
                }
                else {
                    try {
                        System.out.println("Load cpn names: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            @Override
            public void onFailure(Call<Campaign[]> call, Throwable t) {
                System.out.println("Get campaign names: " + t.getMessage());
            }
        });

//        recyclerView.setAdapter(new CampaignListAdapter());
//        recyclerView.setLayoutManager(new LinearLayoutManager(CampaignListActivity.this));
    }

    public static void GetRealPublicKey() {
        Call<PublicKey> publicKeyCall = RetrofitUtils.blockchainInterface.ExecuteGetPublicKey(Constants.USERNAME);
        publicKeyCall.enqueue(new Callback<PublicKey>() {
            @Override
            public void onResponse(Call<PublicKey> call, Response<PublicKey> response) {
                if (response.code() == 200) {
                    Constants.REAL_PUBLIC_KEY = response.body().publicKey;
                    System.out.println("Real just login: " + Constants.REAL_PUBLIC_KEY);
                } else {
                    try {
                        System.out.println("Get all cpns: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PublicKey> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public static void LoadAllCampaigns() {


        Call<Campaign[]> logCall = RetrofitUtils.blockchainInterface.ExecuteGetAllCampaign();
        logCall.enqueue(new Callback<Campaign[]>() {
            @Override
            public void onResponse(Call<Campaign[]> call, Response<Campaign[]> response) {
                if (response.code() == 200) {
                    Log.d("log callback",response.body().toString());
                    Constants.ALL_CAMPAIGN_LIST = Arrays.copyOf(response.body(), response.body().length);
                }
                else {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
            @Override
            public void onFailure(Call<Campaign[]> call, Throwable t) {
                System.out.println("Get all campaigns:" + t.getMessage());
            }
        });

//        recyclerView.setAdapter(new CampaignListAdapter());
//        recyclerView.setLayoutManager(new LinearLayoutManager(CampaignListActivity.this));
    }

    public static String GetUserByCampaign(String campaignName) {
        // System.out.println("Test");
        for (int i = 0; i < Constants.ALL_CAMPAIGN_LIST.length; ++i) {
            // System.out.println(i);
            if (Constants.ALL_CAMPAIGN_LIST[i].getCampaignName().equals(campaignName)) {
                return Constants.ALL_CAMPAIGN_LIST[i].getOwnerName();
            }
        }
        System.out.println("Cannot find any matching owner");
        return "";
    }


}
