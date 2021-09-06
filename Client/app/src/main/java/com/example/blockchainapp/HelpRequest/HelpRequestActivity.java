package com.example.blockchainapp.HelpRequest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blockchainapp.Auth.RegisterActivity;
import com.example.blockchainapp.Campaign.Campaign;
import com.example.blockchainapp.Constants;
import com.example.blockchainapp.MainActivity;
import com.example.blockchainapp.R;
import com.example.blockchainapp.Utils.RetrofitUtils;

import java.io.IOException;
import java.util.ArrayList;

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

        Campaign[] campaigns = Constants.ALL_CAMPAIGN_LIST;
        ArrayList<String> campaignNames = new ArrayList<>();
        for (int i = 0; i < campaigns.length; ++i) {
            campaignNames.add(campaigns[i].getCampaignName());
        }

        String[] listCampaign = new String[campaignNames.size()];
        campaignNames.toArray(listCampaign);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, listCampaign);

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

                    AlertDialog.Builder builder = new AlertDialog.Builder(HelpRequestActivity.this);
                    builder.setTitle("Successfully sent request!");
                    builder.setMessage("Please patiently wait until the campaign owner responds to your help request.");
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(HelpRequestActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                    builder.show();
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