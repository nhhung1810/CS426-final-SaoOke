package com.example.blockchainapp.Utils;

import com.example.blockchainapp.Account.UserKey;
import com.example.blockchainapp.Campaign.Campaign;
import com.example.blockchainapp.HelpRequest.HelpRequest;
import com.example.blockchainapp.Transaction.Transaction;
import com.example.blockchainapp.Log.TransactionLogList;
import com.example.blockchainapp.Transaction.TransactionPackage;

import java.security.PublicKey;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BlockchainInterface {

    @POST("/transaction")
    Call<Boolean> ExecutePostTransaction(@Body TransactionPackage transactionPackage);

    @POST("/balance/{username}")
    Call<Long> ExecuteGetBalance(@Path("username") String username);

    @POST("/register")
    Call<Object> ExecutePostRegister(@Body UserKey account);

    //@POST("/login")
    //Call<UserKey> ExecutePostLogin(@Body UserAccount account);

    //@GET("/campaign")
    //Call<>

    @GET("/transactionsLog")
    Call<TransactionLogList> ExecuteGetTransactionLog();

    @POST("/cpncreate")
    Call<Object> ExecutePostCampaign(@Body Campaign campaign);

    @POST("/requesthelp")
    Call<Boolean> ExecutePostHelpRequest(@Body HelpRequest helpRequest);
}
