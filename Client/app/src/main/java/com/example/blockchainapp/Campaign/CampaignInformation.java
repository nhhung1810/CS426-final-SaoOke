package com.example.blockchainapp.Campaign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blockchainapp.Constants;
import com.example.blockchainapp.R;
import com.example.blockchainapp.Transaction.DonationActivity;
import com.example.blockchainapp.Utils.RetrofitUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CampaignInformation extends AppCompatActivity {

    private String campaignName;
    private Campaign currentCampaign;
    

    TextView tv_campaignName;
    TextView tv_campaignOwner;
    TextView tv_campaignOwnerKey;
    TextView tv_description;
    TextView tv_targetAmount;
    TextView tv_currentFund;
    TextView tv_expireDate;
    TextView tv_propaganda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_information);

        campaignName = getIntent().getStringExtra("CampaignName");
        Initialize();

    }

    private void Initialize() {

        tv_campaignName = findViewById(R.id.tv_campaignName);
        tv_campaignOwner = findViewById(R.id.tv_campaignOwner);
        tv_campaignOwnerKey = findViewById(R.id.tv_campaignOwnerKey);
        tv_description = findViewById(R.id.tv_description);
        tv_targetAmount = findViewById(R.id.tv_targetAmount);
        tv_currentFund = findViewById(R.id.tv_currentFund);
        tv_expireDate = findViewById(R.id.tv_expireDate);
        tv_propaganda = findViewById(R.id.tv_propaganda);

        Call<Campaign> campaignCall = RetrofitUtils.blockchainInterface.ExecuteGetCampaignInformation(campaignName);
        campaignCall.enqueue(new Callback<Campaign>() {
            @Override
            public void onResponse(Call<Campaign> call, Response<Campaign> response) {
                if (response.code() == 200) {
                    currentCampaign = response.body();
                    tv_campaignName.setText(currentCampaign.getCampaignName());
                    tv_campaignOwner.setText(currentCampaign.getOwnerName());
                    tv_campaignOwnerKey.setText("..." + currentCampaign.getOwnerKey().toString().substring(45, 65) + "...");
                    tv_description.setText(currentCampaign.getDescription());
                    tv_targetAmount.setText(currentCampaign.getTargetAmount().toString());
                    tv_currentFund.setText(currentCampaign.getTotalAmount().toString());
                    tv_expireDate.setText(currentCampaign.getExpireDate().toString());
                    tv_propaganda.setText(currentCampaign.getPropaganda());


                } else {
                    try {
                        Toast.makeText(CampaignInformation.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    tv_campaignName.setText("Campaign not found!");
                    currentCampaign = null;
                }
            }

            @Override
            public void onFailure(Call<Campaign> call, Throwable t) {
                Toast.makeText(CampaignInformation.this, t.getMessage(), Toast.LENGTH_LONG).show();
                tv_campaignName.setText("Campaign not found!");
                currentCampaign = null;
            }
        });

    }
}