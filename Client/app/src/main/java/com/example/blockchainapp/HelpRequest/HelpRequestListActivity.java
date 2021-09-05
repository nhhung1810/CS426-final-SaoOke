package com.example.blockchainapp.HelpRequest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.blockchainapp.Campaign.Campaign;
import com.example.blockchainapp.Constants;
import com.example.blockchainapp.R;
import com.example.blockchainapp.Utils.RetrofitUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpRequestListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_request_list);

        recyclerView = findViewById(R.id.rv_helpRequest);
        spinner = findViewById(R.id.spinner_campaignList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i < Constants.USER_CAMPAIGN_LIST.length)
                loadHelpRequests(Constants.USER_CAMPAIGN_LIST[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        loadCampaignList();
        // loadHelpRequests();
    }


    private void loadCampaignList() {

        Call<Campaign[]> logCall = RetrofitUtils.blockchainInterface.ExecuteGetAllCampaign();
        logCall.enqueue(new Callback<Campaign[]>() {
            @Override
            public void onResponse(Call<Campaign[]> call, Response<Campaign[]> response) {
                if (response.code() == 200) {
                    Log.d("log callback",response.body().toString());
                    //CampaignListAdapter adapter = new CampaignListAdapter(response.body(), HelpRequestListActivity.this);
                    //recyclerView.setAdapter(adapter);
                    //recyclerView.setLayoutManager(new LinearLayoutManager(HelpRequestListActivity.this));
                    Campaign[] campaigns = response.body();
                    ArrayList<String> campaignNames = new ArrayList<>();
                    for (int i = 0; i < campaigns.length; ++i) {
                        campaignNames.add(campaigns[i].getCampaignName());
                    }
                    Constants.USER_CAMPAIGN_LIST = new String[campaignNames.size()];
                    campaignNames.toArray(Constants.USER_CAMPAIGN_LIST);
                    ArrayAdapter adapter = new ArrayAdapter(HelpRequestListActivity.this,
                            android.R.layout.simple_spinner_item, Constants.USER_CAMPAIGN_LIST);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                }

            }
            @Override
            public void onFailure(Call<Campaign[]> call, Throwable t) {
                Toast.makeText(HelpRequestListActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

//        recyclerView.setAdapter(new CampaignListAdapter());
//        recyclerView.setLayoutManager(new LinearLayoutManager(CampaignListActivity.this));
    }

    private void loadHelpRequests(String campaignName) {

        Call<HelpRequest[]> helpCall = RetrofitUtils.blockchainInterface.ExecuteGetHelpRequest(campaignName);
        helpCall.enqueue(new Callback<HelpRequest[]>() {
            @Override
            public void onResponse(Call<HelpRequest[]> call, Response<HelpRequest[]> response) {
                Log.d("log callback",response.body().toString());
                HelpRequestAdapter adapter = new HelpRequestAdapter(response.body(), HelpRequestListActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(HelpRequestListActivity.this));
            }

            @Override
            public void onFailure(Call<HelpRequest[]> call, Throwable t) {
                Toast.makeText(HelpRequestListActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

//        recyclerView.setAdapter(new HelpRequestAdapter());
//        recyclerView.setLayoutManager(new LinearLayoutManager(HelpRequestListActivity.this));
    }
}