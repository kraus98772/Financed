package com.cosmas.moniger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class TransactionsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Transaction> transactions = new ArrayList<>();
    String walletName, walletCurrency;

    // TODO: 5/11/22 Get rid of this activity if you fix recyclerView
    void initRecyclerView()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.test_rec_view);
        recyclerView.setLayoutManager(layoutManager);
        TransactionsRecyclerViewAdapter recyclerViewAdapter = new TransactionsRecyclerViewAdapter(this, transactions);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
    void getInfoAboutWallet()
    {
        Bundle walletInfo = getIntent().getExtras();
        walletName = walletInfo.getString("WALLET_NAME");
        walletCurrency = walletInfo.getString("CURRENCY");
    }

    void addTransactionsToRecyclerView()
    {
        DBHelper dbHelper = new DBHelper(this);
        transactions = dbHelper.getTransactions(walletName);
        dbHelper.close();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        getInfoAboutWallet();
        addTransactionsToRecyclerView();


        initRecyclerView();
    }
}