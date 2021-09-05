package com.example.blockchainapp.HelpRequest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blockchainapp.R;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

public class HelpRequestAdapter extends RecyclerView.Adapter<HelpRequestAdapter.ViewHolder> {
    private List<HelpRequest> list;
    private Context context;

    public HelpRequestAdapter(List<HelpRequest> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_hr_campaignName;
        TextView tv_hr_username;
        TextView tv_hr_amount;
        TextView tv_hr_message;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_hr_campaignName = itemView.findViewById(R.id.tv_hr_campaignName);
            tv_hr_username = itemView.findViewById(R.id.tv_hr_username);
            tv_hr_amount = itemView.findViewById(R.id.tv_hr_amount);
            tv_hr_message = itemView.findViewById(R.id.tv_hr_message);
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
        HelpRequest helpRequest = list.get(position);

        holder.tv_hr_campaignName.setText(helpRequest.getCampaignName());
        holder.tv_hr_username.setText(helpRequest.getUsername());
        holder.tv_hr_amount.setText(helpRequest.getAmount().toString());
        holder.tv_hr_message.setText(helpRequest.getMessage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
