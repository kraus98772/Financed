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

        String transactionDateText = dateTextFormatter(transactions.get(position).getTransactionDate());
        holder.transactionDate.setText(transactionDateText);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        // TODO: 4/26/22 Add currency sign to transactions' values by sending additional info through Intent to the activity

        TextView transactionValue, transactionCurrency, transactionDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionValue = itemView.findViewById(R.id.transaction_value_holder);
            //transactionCurrency = itemView.findViewById(R.id.transaction_currency_holder);
            transactionDate = itemView.findViewById(R.id.transaction_date_holder);
        }
    }

    String dateTextFormatter(Date date)
    {
        int day = date.getDate();
        // Increased by one because getMonth returns an index of a month which is between 0 and 11
        // TODO: 4/27/22 Reconsider it after adding a function that adds new transactions
        int month = date.getMonth() + 1;
        int year = date.getYear();

        String dayText = "", monthText = "", yearText;

        if (day < 10)
        {
            dayText = "0" + Integer.toString(day);
        }else
        {
            dayText = Integer.toString(day);
        }

        if(month < 10) {
            monthText = "0" + Integer.toString(month);
        }else
        {
            monthText = Integer.toString(month);
        }

        yearText = Integer.toString(year);

        String dateString = dayText + "." + monthText + "." + yearText;
        return dateString;
    }
}

