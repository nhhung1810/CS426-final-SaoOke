package com.example.blockchainapp.Transaction;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blockchainapp.Account.RSAKey;
import com.example.blockchainapp.Constants;
import com.example.blockchainapp.R;
import com.example.blockchainapp.Utils.RetrofitUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonationActivity extends AppCompatActivity {

    private AutoCompleteTextView campaignET;
    private EditText amountET;
    private EditText messageET;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Initialize();
    }

    private void Initialize() {
        campaignET = findViewById(R.id.actv_campaign);
        amountET = findViewById(R.id.et_amount);
        messageET = findViewById(R.id.et_message);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, Constants.CAMPAIGN_LIST);

        campaignET.setThreshold(1);
        campaignET.setAdapter(adapter);
        campaignET.setTextColor(Color.BLACK);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void HandleTransaction(View view) throws Exception {
        if (TextUtils.isEmpty(campaignET.getText()) || TextUtils.isEmpty(amountET.getText())) return;
        String toCampaign = campaignET.getText().toString();
        Float amount = Float.parseFloat(amountET.getText().toString());
        String message = messageET.getText().toString();

        // TODO: change private key to user's current session
        DonationRequest request = new DonationRequest(toCampaign, Constants.USERNAME, amount, message);
        request.setSignature(RSAKey.sign(toCampaign + Constants.USERNAME + amount.toString(), Constants.PRIVATE_KEY));
        //Transaction transaction = new Transaction(Constants.PUBLIC_KEY, toCampaign, amount, message);

        Call<Object> donationCall = RetrofitUtils.blockchainInterface.ExecutePostDonate(request);
        donationCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    Toast.makeText(DonationActivity.this,
                            "You have successfully donated to the campaign!", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Toast.makeText(DonationActivity.this,
                                response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(DonationActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        /*
        Call<Boolean> transactionCall = RetrofitUtils.blockchainInterface.ExecutePostTransaction(transactionPackage);
        transactionCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200) {
                    Toast.makeText(TransactionActivity.this,
                            "Transaction carried out successfully!", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Toast.makeText(TransactionActivity.this,
                                response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(TransactionActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

         */

    }
}