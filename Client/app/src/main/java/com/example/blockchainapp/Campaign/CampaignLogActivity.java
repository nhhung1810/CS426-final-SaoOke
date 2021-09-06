package com.example.blockchainapp.Campaign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blockchainapp.Constants;
import com.example.blockchainapp.HelpRequest.HelpRequestActivity;
import com.example.blockchainapp.R;
import com.example.blockchainapp.Utils.RetrofitUtils;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CampaignLogActivity extends AppCompatActivity {

    private RecyclerView rv_log;
    private TextView tv_donatorList;
    // private TextView tv_campaignName;
    private AutoCompleteTextView actv_campaignList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_log);

        Initialize();
    }

    private void Initialize() {
        rv_log = findViewById(R.id.rv_campaignLog);
        tv_donatorList = findViewById(R.id.tv_lr_donatorList);
        actv_campaignList = findViewById(R.id.actv_lr_campaignName);

        Campaign[] campaigns = Constants.ALL_CAMPAIGN_LIST;
        ArrayList<String> campaignNames = new ArrayList<>();
        for (int i = 0; i < campaigns.length; ++i) {
            campaignNames.add(campaigns[i].getCampaignName());
        }

        String[] listCampaign = new String[campaignNames.size()];
        campaignNames.toArray(listCampaign);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, listCampaign);

        actv_campaignList.setThreshold(1);
        actv_campaignList.setAdapter(adapter);
        actv_campaignList.setTextColor(Color.BLACK);

    }

    public void HandlePrintLog(View view) {

        // Get the campaign logs
        Call<CampaignLog[]> campaignHistoryCall = RetrofitUtils.blockchainInterface.
                ExecuteGetCampaignHistory(actv_campaignList.getText().toString());

        campaignHistoryCall.enqueue(new Callback<CampaignLog[]>() {
            @Override
            public void onResponse(Call<CampaignLog[]> call, Response<CampaignLog[]> response) {
                if (response.code() == 200) {
                    Log.d("log callback",response.body().toString());
                    CampaignLogAdapter adapter = new CampaignLogAdapter(response.body(), CampaignLogActivity.this);
                    rv_log.setAdapter(adapter);
                    rv_log.setLayoutManager(new LinearLayoutManager(CampaignLogActivity.this));
                } else {
                    try {
                        Toast.makeText(CampaignLogActivity.this,
                                response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CampaignLog[]> call, Throwable t) {
                Toast.makeText(CampaignLogActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Call<String[]> donatorCall = RetrofitUtils.blockchainInterface.
                ExecuteGetDonatorList(actv_campaignList.getText().toString());
        donatorCall.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                if (response.code() == 200) {
                    String[] res = response.body();
                    String listDonator = "";
                    for (int i = 0; i < res.length; ++i) {
                        listDonator += res[i];
                        if (i != res.length - 1) listDonator += ", ";
                    }
                    tv_donatorList.setText(listDonator);
                } else {
                    try {
                        Toast.makeText(CampaignLogActivity.this,
                                response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                Toast.makeText(CampaignLogActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        // Get the donator list

    }


}