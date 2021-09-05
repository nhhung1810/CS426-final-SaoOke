package com.example.blockchainapp.HelpRequest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.blockchainapp.Campaign.CampaignActivity;
import com.example.blockchainapp.Campaign.CampaignListActivity;
import com.example.blockchainapp.R;

public class HelpRequestOptionSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_request_option_select);
    }

    public void createHelpRequest(View view) {
        startActivity(new Intent(this, HelpRequestActivity.class));
    }

    public void viewHelpRequestList(View view) {
        startActivity(new Intent(this, HelpRequestListActivity.class));
    }

}