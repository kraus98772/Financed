package com.cosmas.moniger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
        // TODO: 4/26/22 Get only date from transaction field, because it's now showing too much text
        holder.transactionDate.setText(transactions.get(position).getTransactionDate().toString());
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
}
