package com.example.blockchainapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransactionActivity extends AppCompatActivity {

    private EditText toAddressET;
    private EditText amountET;
    private EditText messageET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Initialize();
    }

    private void Initialize() {
        toAddressET = findViewById(R.id.et_address);
        amountET = findViewById(R.id.et_amount);
        messageET = findViewById(R.id.et_message);

    }

    public void HandleTransaction(View view) {
        String toAddress = toAddressET.getText().toString();
        Float amount = Float.parseFloat(amountET.getText().toString());
        String message = messageET.getText().toString();

        // TODO: change private key to user's current session
        Transaction transaction = new Transaction(Constants.PRIVATE_KEY, toAddress, amount, message);
        Call<Boolean> transactionCall = RetrofitUtils.blockchainInterface.ExecutePostTransaction(transaction);
        transactionCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200) {
                    Toast.makeText(TransactionActivity.this,
                            "Transaction carried out successfully!", Toast.LENGTH_LONG).show();
                } else if (response.code() == 404) {
                    Toast.makeText(TransactionActivity.this,
                            "Invalid credentials", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(TransactionActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}