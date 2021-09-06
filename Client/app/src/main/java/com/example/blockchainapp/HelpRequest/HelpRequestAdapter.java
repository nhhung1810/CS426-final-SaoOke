package com.example.blockchainapp.HelpRequest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blockchainapp.Campaign.Campaign;
import com.example.blockchainapp.Campaign.CampaignInformation;
import com.example.blockchainapp.R;
import com.example.blockchainapp.Transaction.GrantActivity;
import com.example.blockchainapp.Utils.ItemClickListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

public class HelpRequestAdapter extends RecyclerView.Adapter<HelpRequestAdapter.ViewHolder> {
    private HelpRequest[] list;
    private Context context;
    private String campaignName;


    public HelpRequestAdapter(HelpRequest[] list, Context context, String campaignName) {
        this.list = list;
        this.context = context;
        this.campaignName = campaignName;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        // TextView tv_hr_campaignName;
        TextView tv_hr_username;
        TextView tv_hr_amount;
        TextView tv_hr_message;

        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            // tv_hr_campaignName = itemView.findViewById(R.id.tv_hr_campaignName);
            tv_hr_username = itemView.findViewById(R.id.tv_hr_username);
            tv_hr_amount = itemView.findViewById(R.id.tv_hr_amount);
            tv_hr_message = itemView.findViewById(R.id.tv_hr_message);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), true);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), true);
            return true;
        }
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.help_request_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HelpRequestAdapter.ViewHolder holder, int position) {
        HelpRequest helpRequest = list[position];

        // holder.tv_hr_campaignName.setText(helpRequest.getCampaignName());
        holder.tv_hr_username.setText(helpRequest.getUsername());
        holder.tv_hr_amount.setText(helpRequest.getAmount().toString());
        holder.tv_hr_message.setText(helpRequest.getMessage());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                // String campaignName = list[position].getCampaignName();
                String toUser = list[position].getUsername();
                Long amount = list[position].getAmount();
                Intent intent = new Intent(context, GrantActivity.class);
                intent.putExtra("CampaignName", campaignName);
                intent.putExtra("ToUser", toUser);
                intent.putExtra("Amount", amount);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.length;
    }
}
