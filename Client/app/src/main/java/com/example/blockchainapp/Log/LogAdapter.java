package com.example.blockchainapp.Log;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blockchainapp.R;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {

    TransactionLogList transactionLogList;
    Context context;

    public class LogViewHolder extends RecyclerView.ViewHolder {
        TextView tv_fromAddress;
        TextView tv_toAddress;
        TextView tv_amount;
        TextView tv_timestamp;
        TextView tv_signature;
        public LogViewHolder(View itemView)
        {
            super(itemView);
            tv_fromAddress = itemView.findViewById(R.id.tv_fromAddress);
            tv_toAddress = itemView.findViewById(R.id.tv_toAddress);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_timestamp = itemView.findViewById(R.id.tv_timeStamp);
            tv_signature = itemView.findViewById(R.id.tv_signature);
        }
    }

    public LogAdapter(TransactionLogList transactionLogList, Context context) {
        this.transactionLogList = transactionLogList;
        this.context = context;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public LogAdapter.LogViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.log_row, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull LogAdapter.LogViewHolder holder, int position) {
        TransactionLog log = transactionLogList.logs.get(position);
        holder.tv_fromAddress.setText(log.getFromAddress());
        holder.tv_toAddress.setText(log.getToAddress());
        holder.tv_amount.setText(String.valueOf(log.getAmount()));
        holder.tv_timestamp.setText(log.getTimestamp().toString());
        holder.tv_signature.setText(log.getSignature().toString());
    }

    @Override
    public int getItemCount() {
        return transactionLogList.logs.size();
    }
}
