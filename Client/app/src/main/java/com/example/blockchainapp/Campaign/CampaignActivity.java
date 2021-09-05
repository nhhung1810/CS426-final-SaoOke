package com.example.blockchainapp.Campaign;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blockchainapp.Constants;
import com.example.blockchainapp.R;
import com.example.blockchainapp.Utils.RetrofitUtils;

import org.web3j.utils.Strings;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CampaignActivity extends AppCompatActivity {
    private Campaign campaign;

    private EditText et_campaignName;
    private EditText et_description;
    private EditText et_targetAmount;
    private EditText et_expireDate;
    private EditText et_propaganda;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);

        initializeInputs();
    }

    private void initializeInputs() {
        et_campaignName = findViewById(R.id.et_campaignName);
        et_description = findViewById(R.id.et_description);
        et_targetAmount = findViewById(R.id.et_targetAmount);
        et_expireDate = findViewById(R.id.et_expireDate);
        et_propaganda = findViewById(R.id.et_propaganda);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalendar.set(Calendar.YEAR, i);
                myCalendar.set(Calendar.MONTH, i1);
                myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                updateLabel();
            }
        };

        et_expireDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CampaignActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        et_expireDate.setText(sdf.format(myCalendar.getTime()));
    }


    // btn_addCampaign onClick
    public void HandleCampaignCreation(View view) throws ParseException {

        if (!CheckCampaignValidity()) return;

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        campaign = new Campaign(Constants.PUBLIC_KEY.toString(),
                                et_campaignName.getText().toString(),
                                Constants.USERNAME,
                                et_description.getText().toString(),
                                Long.valueOf(et_targetAmount.getText().toString()),
                                format.parse(et_expireDate.getText().toString()),
                                et_propaganda.getText().toString());

        Call<Object> campaignCall = RetrofitUtils.blockchainInterface.ExecutePostCampaign(campaign);
        campaignCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    Toast.makeText(CampaignActivity.this,
                                    "Campaign was created successfully!",
                                    Toast.LENGTH_LONG).show();
                }
                else {
                    try {
                        Toast.makeText(CampaignActivity.this,
                                        response.errorBody().string(),
                                        Toast.LENGTH_LONG).show();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(CampaignActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean CheckCampaignValidity() {
        if (TextUtils.isEmpty(et_campaignName.getText().toString()) || TextUtils.isEmpty(et_description.getText().toString()) ||
        TextUtils.isEmpty(et_targetAmount.getText().toString()) || TextUtils.isEmpty(et_expireDate.getText().toString())) return false;
        return true;
    }
}