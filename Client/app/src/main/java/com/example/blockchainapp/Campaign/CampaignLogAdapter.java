package com.example.blockchainapp.Campaign;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blockchainapp.Log.LogAdapter;
import com.example.blockchainapp.Log.TransactionLog;
import com.example.blockchainapp.R;
import com.example.blockchainapp.Utils.ItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import jnr.ffi.annotations.In;

public class CampaignLogAdapter extends RecyclerView.Adapter<CampaignLogAdapter.ViewHolder> {
    private CampaignLog[] list;
    private Context context;

    public CampaignLogAdapter(CampaignLog[] list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_from;
        TextView tv_to;
        TextView tv_amount;
        TextView tv_message;
        TextView tv_timestamp;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_from = itemView.findViewById(R.id.tv_lr_from);
            tv_to = itemView.findViewById(R.id.tv_lr_to);
            tv_amount = itemView.findViewById(R.id.tv_lr_amount);
            tv_message = itemView.findViewById(R.id.tv_lr_message);
            tv_timestamp = itemView.findViewById(R.id.tv_lr_timestamp);
        }

    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.campaign_log_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CampaignLogAdapter.ViewHolder holder, int position) {
        CampaignLog campaignLog = list[position];

        holder.tv_from.setText(campaignLog.getFrom());
        holder.tv_to.setText(campaignLog.getTo());
        holder.tv_amount.setText(campaignLog.getAmount().toString());
        holder.tv_message.setText(campaignLog.getMessage());
        holder.tv_timestamp.setText(campaignLog.getTimestamp());

    }

    @Override
    public int getItemCount() {
        return list.length;
    }
}
