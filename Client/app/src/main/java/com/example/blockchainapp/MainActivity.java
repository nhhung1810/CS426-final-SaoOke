package com.example.blockchainapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.blockchainapp.Account.AccountActivity;
import com.example.blockchainapp.Auth.LoginActivity;
import com.example.blockchainapp.Campaign.CampaignOptionSelectActivity;
import com.example.blockchainapp.Campaign.GrantActivity;
import com.example.blockchainapp.HelpRequest.HelpRequestActivity;
import com.example.blockchainapp.HelpRequest.HelpRequestOptionSelectActivity;
import com.example.blockchainapp.Log.HistoryActivity;
import com.example.blockchainapp.Transaction.DonationActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tv_username;
    private TextView tv_balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Constants.SESSION_ACTIVE) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Initialize();
        }

    }

    private void Initialize() {
        tv_username = findViewById(R.id.tv_dashboard);
        tv_balance = findViewById(R.id.tv_balance);

        // BlockchainUtils.GetBalance(tv_balance);
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        tv_username.setText(Constants.USERNAME + "'s Dashboard");
        tv_balance.setText(currencyFormatter.format(Constants.BALANCE).toString() + " VNĐ");
    }

    public void DonateTab(View view) {
        Intent intent = new Intent(this, DonationActivity.class);
        startActivity(intent);
    }

    public void AccountTab(View view) {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    public void HistoryTab(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void RequestTab(View view) {
        Intent intent = new Intent(this, HelpRequestOptionSelectActivity.class);
        startActivity(intent);
    }

    public void CampaignTab(View view) {
        Intent intent = new Intent(this, CampaignOptionSelectActivity.class);
        startActivity(intent);
    }

    public void GrantTab(View view) {
        Intent intent = new Intent(this, GrantActivity.class);
        startActivity(intent);
    }
}