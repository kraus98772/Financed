package com.cosmas.moniger.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cosmas.moniger.databases.DBHelper;
import com.cosmas.moniger.utils.DateTextFormatter;
import com.cosmas.moniger.R;
import com.cosmas.moniger.utils.Transaction;
import com.cosmas.moniger.activities.TransactionActivity;

import java.util.ArrayList;

public class TransactionsRecyclerViewAdapter extends RecyclerView.Adapter<TransactionsRecyclerViewAdapter.ViewHolder> {

    public static final String TAG = "TransactionsRecyclerViewAdapter";
    private Context mContext;
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private final String walletName, walletCurrency;

    public TransactionsRecyclerViewAdapter(Context mContext, ArrayList<Transaction> transactions, String walletName, String walletCurrency) {
        this.mContext = mContext;
        this.transactions = transactions;
        this.walletName = walletName;
        this.walletCurrency = walletCurrency;
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
        holder.transactionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTransactionActivity(transactions.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactions != null ? transactions.size() : 0;
    }


    public void openTransactionActivity(Transaction transaction)
    {
        DBHelper dbHelper = new DBHelper(mContext);
        String category = dbHelper.getTransactionContent(walletName, transaction).getCategory();
        String description = dbHelper.getTransactionContent(walletName, transaction).getDescription();

        Intent intent = new Intent(mContext, TransactionActivity.class);
        intent.putExtra("WALLET_NAME", walletName);
        intent.putExtra("CURRENCY", walletCurrency);
        intent.putExtra("VALUE", transaction.getTransactionValue());
        intent.putExtra("DATE", transaction.getTransactionDate().toString());
        intent.putExtra("CATEGORY", category);
        intent.putExtra("DESCRIPTION", description);
        intent.putExtra("ID", transaction.getId());
        mContext.startActivity(intent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView transactionValue, transactionCurrency, transactionDate;
        RelativeLayout transactionLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionValue = itemView.findViewById(R.id.transaction_value_holder);
            //transactionCurrency = itemView.findViewById(R.id.transaction_currency_holder);
            transactionDate = itemView.findViewById(R.id.transaction_date_holder);
            transactionLayout = itemView.findViewById(R.id.transaction_layout);
        }
    }
}