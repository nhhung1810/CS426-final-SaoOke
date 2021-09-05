package com.example.blockchainapp.Campaign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.blockchainapp.Log.HistoryActivity;
import com.example.blockchainapp.Log.LogAdapter;
import com.example.blockchainapp.Log.TransactionLogList;
import com.example.blockchainapp.R;
import com.example.blockchainapp.Utils.RetrofitUtils;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            loadCampaignList();
        }

    }

    private void loadCampaignList() {

        Call<Campaign[]> logCall = RetrofitUtils.blockchainInterface.ExecuteGetAllCampaign();
        logCall.enqueue(new Callback<Campaign[]>() {
            @Override
            public void onResponse(Call<Campaign[]> call, Response<Campaign[]> response) {
                if (response.code() == 200) {
                    Log.d("log callback",response.body().toString());
                    CampaignListAdapter adapter = new CampaignListAdapter(response.body(), CampaignListActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(CampaignListActivity.this));
                }

            }
            @Override
            public void onFailure(Call<Campaign[]> call, Throwable t) {
                Toast.makeText(CampaignListActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

//        recyclerView.setAdapter(new CampaignListAdapter());
//        recyclerView.setLayoutManager(new LinearLayoutManager(CampaignListActivity.this));
    }
}