package com.example.blockchainapp.HelpRequest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blockchainapp.Constants;
import com.example.blockchainapp.R;
import com.example.blockchainapp.Utils.RetrofitUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpRequestActivity extends AppCompatActivity {
    private AutoCompleteTextView actv_hr_campaign;
    private EditText et_hr_amount;
    private EditText et_hr_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_request);

        initializeInputs();
    }

    private void initializeInputs() {
        actv_hr_campaign = findViewById(R.id.actv_hr_campaign);
        et_hr_amount = findViewById(R.id.et_hr_amount);
        et_hr_message = findViewById(R.id.et_hr_message);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, Constants.USER_CAMPAIGN_LIST);

        actv_hr_campaign.setThreshold(1);
        actv_hr_campaign.setAdapter(adapter);
        actv_hr_campaign.setTextColor(Color.BLACK);
    }

    public void requestHelp(View view) throws Exception {
        if (TextUtils.isEmpty(actv_hr_campaign.getText()) || TextUtils.isEmpty(actv_hr_campaign.getText())) return;
        String requestedCampaign = actv_hr_campaign.getText().toString();
        Float amount = Float.parseFloat(et_hr_amount.getText().toString());
        String message = et_hr_message.getText().toString();

        // TODO: change private key to user's current session
        HelpRequest helpRequest = new HelpRequest(requestedCampaign, Constants.USERNAME, amount, message);

        Call<Object> helpRequestCall = RetrofitUtils.blockchainInterface.ExecutePostHelpRequest(helpRequest);
        helpRequestCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    Toast.makeText(HelpRequestActivity.this,
                            "Help request sent successfully!", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Toast.makeText(HelpRequestActivity.this,
                                response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(HelpRequestActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}