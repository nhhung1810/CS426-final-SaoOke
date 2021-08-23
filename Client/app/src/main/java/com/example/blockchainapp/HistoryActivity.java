package com.example.blockchainapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
        LoadTransactionLog();
    }

    private void LoadTransactionLog() {
        Call<TransactionLogList> logCall = RetrofitUtils.blockchainInterface.ExecuteGetTransactionLog();
        logCall.enqueue(new Callback<TransactionLogList>() {
            @Override
            public void onResponse(Call<TransactionLogList> call, Response<TransactionLogList> response) {
                if (response.code() == 200) {
                    Log.d("log callback",response.body().toString());
                    LogAdapter adapter = new LogAdapter(response.body(), HistoryActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
                }

            }
            @Override
            public void onFailure(Call<TransactionLogList> call, Throwable t) {
                Toast.makeText(HistoryActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}