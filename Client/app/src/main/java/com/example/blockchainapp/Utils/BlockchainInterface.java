package com.example.blockchainapp.Utils;

import com.example.blockchainapp.Account.UserKey;
import com.example.blockchainapp.Transaction.Transaction;
import com.example.blockchainapp.Log.TransactionLogList;
import com.example.blockchainapp.Transaction.TransactionPackage;

import java.security.PublicKey;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BlockchainInterface {

    @POST("/transaction")
    Call<Boolean> ExecutePostTransaction(@Body TransactionPackage transactionPackage);

    @POST("/balance")
    Call<Long> ExecuteGetBalance(@Body PublicKey address);

    @POST("/register")
    Call<Object> ExecutePostRegister(@Body UserKey account);

    //@POST("/login")
    //Call<UserKey> ExecutePostLogin(@Body UserAccount account);

    @GET("/transactionsLog")
    Call<TransactionLogList> ExecuteGetTransactionLog();

}
