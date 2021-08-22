package com.example.blockchainapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    private TextView balanceTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Initialize();
    }

    private void Initialize() {
        balanceTV = findViewById(R.id.tv_balance);
        GetBalance();
    }

    private void GetBalance() {
        // TODO: Change private key
        Call<Float> balanceCall = RetrofitUtils.blockchainInterface.ExecuteGetBalance(Constants.PRIVATE_KEY);
        balanceCall.enqueue(new Callback<Float>() {
            @Override
            public void onResponse(Call<Float> call, Response<Float> response) {
                if (response.code() == 200) {
                    balanceTV.setText(response.body().toString() + " VNĐ");
                } else if (response.code() == 404) {
                    Toast.makeText(AccountActivity.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Float> call, Throwable t) {
                Toast.makeText(AccountActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void HandleRefreshBalance(View view) {
        GetBalance();
    }
}