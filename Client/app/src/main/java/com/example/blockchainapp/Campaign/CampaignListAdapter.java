package com.example.blockchainapp.Campaign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blockchainapp.Log.LogAdapter;
import com.example.blockchainapp.Log.TransactionLog;
import com.example.blockchainapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CampaignListAdapter extends RecyclerView.Adapter<CampaignListAdapter.ViewHolder> {
    private List<Campaign> list;
    private Context context;

    public CampaignListAdapter(List<Campaign> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_campaignName;
        TextView tv_leaderName;
        TextView tv_targetAmount;
        TextView tv_expireDate;
        TextView tv_propaganda;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_campaignName = itemView.findViewById(R.id.tv_campaignName);
            tv_leaderName = itemView.findViewById(R.id.tv_leaderName);
            tv_targetAmount = itemView.findViewById(R.id.tv_targetAmount);
            tv_expireDate = itemView.findViewById(R.id.tv_expireDate);
            tv_propaganda = itemView.findViewById(R.id.tv_propaganda);
        }
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.campaign_row, parent, false);
        return new LogAdapter.LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CampaignListAdapter.ViewHolder holder, int position) {
        Campaign campaign = list.get(position);

        holder.tv_campaignName.setText(campaign.getCampaignName());
        holder.tv_leaderName.setText(campaign.getOwnerName());
        holder.tv_targetAmount.setText(campaign.getTargetAmount().toString());
        holder.tv_expireDate.setText(campaign.getExpireDate().toString());
        holder.tv_propaganda.setText(campaign.getPropaganda());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
