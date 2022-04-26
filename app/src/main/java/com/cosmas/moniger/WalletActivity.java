package com.cosmas.moniger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;

public class WalletActivity extends AppCompatActivity {

    private ArrayList<Transaction> transactions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        initRecyclerView();
        addTestTransactions();


    }

    void initRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.transactions_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        TransactionsRecyclerViewAdapter recyclerViewAdapter = new TransactionsRecyclerViewAdapter(this, transactions);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    void addTestTransactions()
    {
        transactions.add(new Transaction(1000, new Date(2022,1,12), "Food", "You bought a cat ?"));
        transactions.add(new Transaction(22, new Date(2022,2,12), "Food", "You bought a dog ?"));
        transactions.add(new Transaction(33, new Date(2022,3,12), "Food", "You bought a slime ?"));
    }

}