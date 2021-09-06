package com.example.blockchainapp.Transaction;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blockchainapp.Account.PublicKey;
import com.example.blockchainapp.Account.RSAKey;
import com.example.blockchainapp.Auth.RegisterActivity;
import com.example.blockchainapp.Constants;
import com.example.blockchainapp.MainActivity;
import com.example.blockchainapp.R;
import com.example.blockchainapp.Utils.RetrofitUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GrantActivity extends AppCompatActivity {

    private AutoCompleteTextView campaignET;
    private EditText toUserET;
    private EditText amountET;
    private EditText messageET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        Initialize();
    }

    private void Initialize() {
        campaignET = findViewById(R.id.actv_campaign);
        toUserET = findViewById(R.id.et_toUser);;
        amountET = findViewById(R.id.et_amount);
        messageET = findViewById(R.id.et_message);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, Constants.USER_CAMPAIGN_LIST);

        campaignET.setThreshold(1);
        campaignET.setAdapter(adapter);
        campaignET.setTextColor(Color.BLACK);
    }

    private boolean CheckValidTransaction() {
        if (TextUtils.isEmpty(campaignET.getText()) || TextUtils.isEmpty(amountET.getText()) || TextUtils.isEmpty(toUserET.getText())) {
            Toast.makeText(GrantActivity.this,
                    "Please enter a valid campaign name, a valid username and a valid money amount.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (Long.valueOf(amountET.getText().toString()) < Constants.BALANCE) {
            Toast.makeText(GrantActivity.this,
                    "Please recheck your campaign's balance, it is insufficient compared to the input amount.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void HandleGrant(View view) throws Exception {

        if (!CheckValidTransaction()) return;
        String campaign = campaignET.getText().toString();
        String toUser = toUserET.getText().toString();
        Long amount = Long.valueOf(amountET.getText().toString());
        String message = messageET.getText().toString();

        // TODO: change public key to campaign's name
        GrantRequest request = new GrantRequest(campaign, toUser, amount, message);
        // request.setSignature(RSAKey.sign(campaign + toUser + amount.toString(), Constants.PRIVATE_KEY));
        System.out.println("Here:" + toUser);

        Call<PublicKey> publicKeyCall = RetrofitUtils.blockchainInterface.ExecuteGetPublicKey(toUser);
        publicKeyCall.enqueue(new Callback<PublicKey>() {
            @Override
            public void onResponse(Call<PublicKey> call, Response<PublicKey> response) {
                if (response.code() == 200) {
                    String toUserPublicKey = response.body().publicKey;
                    try {
                        System.out.println("Obtained public key");
                        System.out.println(Constants.REAL_PUBLIC_KEY);
                        System.out.println(toUserPublicKey);
                        System.out.println(amount.toString());
                        request.setSignature(RSAKey.sign(Constants.REAL_PUBLIC_KEY +
                                toUserPublicKey + amount.toString(), Constants.PRIVATE_KEY));

                        Call<Object> grantCall = RetrofitUtils.blockchainInterface.ExecutePostGrant(request);
                        grantCall.enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                if (response.code() == 200) {
                                    Toast.makeText(GrantActivity.this,
                                            "You have successfully granted the fund to " + toUser + "!", Toast.LENGTH_LONG).show();

                                    AlertDialog.Builder builder = new AlertDialog.Builder(GrantActivity.this);
                                    builder.setTitle("Successfully granted!");
                                    builder.setMessage("Thank you for the good deed, the needed person will receive the fund soon.");
                                    builder.setPositiveButton("Confirm",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(GrantActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                }
                                            });
                                    builder.show();
                                } else {
                                    try {
                                        Toast.makeText(GrantActivity.this,
                                                response.errorBody().string(), Toast.LENGTH_LONG).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {
                                Toast.makeText(GrantActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Toast.makeText(GrantActivity.this,
                                response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PublicKey> call, Throwable t) {
                Toast.makeText(GrantActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        //Transaction transaction = new Transaction(Constants.PUBLIC_KEY, toCampaign, amount, message);
        /*
        Transaction transaction = new Transaction(Constants.PUBLIC_KEY, toUser, amount, message);
        String signature = RSAKey.sign(transaction, Constants.PRIVATE_KEY);
        TransactionPackage transactionPackage = new TransactionPackage(transaction, signature);

        Call<Boolean> transactionCall = RetrofitUtils.blockchainInterface.ExecutePostTransaction(transactionPackage);
        transactionCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200) {
                    Toast.makeText(GrantActivity.this,
                            "Transaction carried out successfully!", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Toast.makeText(GrantActivity.this,
                                response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(GrantActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

         */
    }

}