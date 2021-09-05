package com.example.blockchainapp.Campaign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.blockchainapp.R;

public class CampaignListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_list);


        recyclerView = findViewById(R.id.rv_campaign);
        String sortType = getIntent().getStringExtra("SORT_TYPE");
        if (sortType.equals("ALL")) {
            loadCampaignList();
        } else if (sortType.equals("USER")) {

        }



    }

    private void loadCampaignList() {
//        recyclerView.setAdapter(new CampaignListAdapter());
//        recyclerView.setLayoutManager(new LinearLayoutManager(CampaignListActivity.this));
    }
}