package com.example.blockchainapp.HelpRequest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.blockchainapp.R;

public class HelpRequestListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_request_list);

        recyclerView = findViewById(R.id.rv_helpRequest);
        loadHelpRequests();
    }

    private void loadHelpRequests() {
//        recyclerView.setAdapter(new HelpRequestAdapter());
//        recyclerView.setLayoutManager(new LinearLayoutManager(HelpRequestListActivity.this));
    }
}