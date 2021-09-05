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

public class CampaignListAdapter extends RecyclerView.Adapter<CampaignListAdapter.ViewHolder> {
    private CampaignList list;
    private Context context;

    public CampaignListAdapter(CampaignList list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        TextView tv_campaignName;
        TextView tv_leaderName;
        TextView tv_description;
        TextView tv_targetAmount;
        TextView tv_expireDate;
        TextView tv_propaganda;

        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_campaignName = itemView.findViewById(R.id.tv_campaignName);
            tv_leaderName = itemView.findViewById(R.id.tv_leaderName);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_targetAmount = itemView.findViewById(R.id.tv_targetAmount);
            tv_expireDate = itemView.findViewById(R.id.tv_expireDate);
            tv_propaganda = itemView.findViewById(R.id.tv_propaganda);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), true);
            return true;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), true);
        }
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.campaign_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CampaignListAdapter.ViewHolder holder, int position) {
        Campaign campaign = list.ListOfCampaign.get(position);

        holder.tv_campaignName.setText(campaign.getCampaignName());
        holder.tv_leaderName.setText(campaign.getOwnerName());
        holder.tv_description.setText(campaign.getDescription());
        holder.tv_targetAmount.setText(campaign.getTargetAmount().toString());
        holder.tv_expireDate.setText(campaign.getExpireDate().toString());
        holder.tv_propaganda.setText(campaign.getPropaganda());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                String campaignName = list.ListOfCampaign.get(position).getCampaignName();
                Intent intent = new Intent(context, CampaignInformation.class);
                intent.putExtra("CampaignName", campaignName);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.ListOfCampaign.size();
    }
}
