package com.example.blockchainapp.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blockchainapp.Constants;
import com.example.blockchainapp.R;
import com.example.blockchainapp.Utils.RetrofitUtils;

import java.text.NumberFormat;
import java.util.Locale;

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
        balanceTV = findViewById(R.id.tv_currentBalance);
        GetBalance();
    }

    private void GetBalance() {
        // TODO: Change private key
        // Log.d("Key", Constants.PUBLIC_KEY);
        // PublicKey key = new PublicKey(Constants.PUBLIC_KEY);

        /*
        Call<Long> balanceCall = RetrofitUtils.blockchainInterface.ExecuteGetBalance(key);
        balanceCall.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {

                if (response.code() == 200) {
                    Locale locale = new Locale("vi", "VN");
                    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
                    balanceTV.setText(currencyFormatter.format(response.body()) + " VNĐ");
                } else if (response.code() == 404) {
                    Toast.makeText(AccountActivity.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AccountActivity.this, "Unknown errors", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Toast.makeText(AccountActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

         */
    }

    public void HandleRefreshBalance(View view) {
        GetBalance();
    }
}