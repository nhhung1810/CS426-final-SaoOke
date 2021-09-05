package com.example.blockchainapp.Campaign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.blockchainapp.Constants;
import com.example.blockchainapp.R;

import java.sql.Date;

public class CampaignActivity extends AppCompatActivity {
    private Campaign campaign;

    private EditText et_campaignName;
    private EditText et_leaderName;
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
        et_leaderName = findViewById(R.id.et_leaderName);
        et_targetAmount = findViewById(R.id.et_targetAmount);
        et_expireDate = findViewById(R.id.et_expireDate);
        et_propaganda = findViewById(R.id.et_propaganda);
    }

    // btn_addCampaign onClick
    public void HandleCampaignCreation(View view) {
        campaign = new Campaign(Constants.PUBLIC_KEY,
                                et_campaignName.getText().toString(),
                                et_leaderName.getText().toString(),
                                Float.parseFloat(et_targetAmount.getText().toString()),
                                Date.valueOf(et_expireDate.getText().toString()),
                                et_propaganda.getText().toString());

    }
}