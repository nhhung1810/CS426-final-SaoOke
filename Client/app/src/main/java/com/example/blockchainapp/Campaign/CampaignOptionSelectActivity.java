package com.example.blockchainapp.Campaign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.blockchainapp.R;

public class CampaignOptionSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_option_select);
    }

    public void createCampaign(View view) {
        startActivity(new Intent(this, CampaignActivity.class));
    }

    public void viewCampaignList(View view) {
        startActivity(new Intent(this, CampaignListActivity.class));
    }
}