package com.cosmas.moniger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class TransactionsRecyclerViewAdapter extends RecyclerView.Adapter<TransactionsRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public TransactionsRecyclerViewAdapter(Context mContext, ArrayList<Transaction> transactions) {
        this.mContext = mContext;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.transactionValue.setText(String.valueOf(transactions.get(position).getTransactionValue()));
        DateTextFormatter dateTextFormatter = new DateTextFormatter();
        String transactionDateText = dateTextFormatter.formatText(transactions.get(position).getTransactionDate(), ".");
        holder.transactionDate.setText(transactionDateText);
    }

    @Override
    public int getItemCount() {
        return transactions != null ? transactions.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView transactionValue, transactionCurrency, transactionDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionValue = itemView.findViewById(R.id.transaction_value_holder);
            //transactionCurrency = itemView.findViewById(R.id.transaction_currency_holder);
            transactionDate = itemView.findViewById(R.id.transaction_date_holder);
        }
    }


}