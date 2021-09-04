package com.example.blockchainapp.Utils;

import com.example.blockchainapp.Account.PublicKey;
import com.example.blockchainapp.Account.UserAccount;
import com.example.blockchainapp.Account.UserKey;
import com.example.blockchainapp.Transaction.Transaction;
import com.example.blockchainapp.Log.TransactionLogList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BlockchainInterface {

    @POST("/transaction")
    Call<Boolean> ExecutePostTransaction(@Body Transaction transaction);

    @POST("/balance")
    Call<Long> ExecuteGetBalance(@Body PublicKey address);

    //@POST("/register")
    //Call<UserKey> ExecutePostRegister(@Body UserAccount account);

    //@POST("/login")
    //Call<UserKey> ExecutePostLogin(@Body UserAccount account);

    @GET("/transactionsLog")
    Call<TransactionLogList> ExecuteGetTransactionLog();

}
