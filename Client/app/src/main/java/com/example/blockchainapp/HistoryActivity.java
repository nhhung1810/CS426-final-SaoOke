package com.example.blockchainapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.rv_transaction);

    }

    private void LoadTransactionLog() {
        Call<TransactionLogList> logCall = RetrofitUtils.blockchainInterface.ExecuteGetTransactionLog();
        logCall.enqueue(new Callback<TransactionLogList>() {
            @Override
            public void onResponse(Call<TransactionLogList> call, Response<TransactionLogList> response) {
                LogAdapter adapter = new LogAdapter(response.body(), HistoryActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
            }

            @Override
            public void onFailure(Call<TransactionLogList> call, Throwable t) {

            }
        });
    }

}