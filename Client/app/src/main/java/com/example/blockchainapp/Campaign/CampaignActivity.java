package com.example.blockchainapp.Campaign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blockchainapp.Constants;
import com.example.blockchainapp.R;
import com.example.blockchainapp.Utils.RetrofitUtils;

import java.io.IOException;
import java.sql.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CampaignActivity extends AppCompatActivity {
    private Campaign campaign;

    private EditText et_campaignName;
    private EditText et_description;
    private EditText et_targetAmount;
    private EditText et_expireDate;
    private EditText et_propaganda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);

        initializeInputs();
    }

    private void initializeInputs() {
        et_campaignName = findViewById(R.id.et_campaignName);
        et_description = findViewById(R.id.et_description);
        et_targetAmount = findViewById(R.id.et_targetAmount);
        et_expireDate = findViewById(R.id.et_expireDate);
        et_propaganda = findViewById(R.id.et_propaganda);
    }

    // btn_addCampaign onClick
    public void HandleCampaignCreation(View view) {
        campaign = new Campaign(Constants.PUBLIC_KEY,
                                et_campaignName.getText().toString(),
                                Constants.USERNAME,
                                et_description.getText().toString(),
                                Float.parseFloat(et_targetAmount.getText().toString()),
                                Date.valueOf(et_expireDate.getText().toString()),
                                et_propaganda.getText().toString());

        Call<Boolean> campaignCall = RetrofitUtils.blockchainInterface.ExecutePostCampaign(campaign);
        campaignCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200) {
                    Toast.makeText(CampaignActivity.this,
                                    "Campaign was created successfully!",
                                    Toast.LENGTH_LONG).show();
                }
                else {
                    try {
                        Toast.makeText(CampaignActivity.this,
                                        response.errorBody().string(),
                                        Toast.LENGTH_LONG).show();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(CampaignActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}