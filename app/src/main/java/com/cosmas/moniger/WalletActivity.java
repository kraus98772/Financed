package com.cosmas.moniger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class WalletActivity extends AppCompatActivity {

    private ArrayList<Transaction> transactions = new ArrayList<>();
    private TextView currencyView;
    private ImageButton goBackButton;

    // TODO: 5/1/22 Add settings to a wallet [ delete wallet, convert into another currency ( can convert simply into a different currency or calculate all transactions
    //  into different currency depending on current course ) ]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        initViews();
        setUpGoBackButton();
        initRecyclerView();
        addTestTransactions();

        Bundle walletInfo = getExtrasFromWalletsRecyclerView();
        setCurrencyView(walletInfo.getString("currency"));

    }

    Bundle getExtrasFromWalletsRecyclerView()
    {
        return getIntent().getExtras();
    }

    void setCurrencyView(String currency)
    {
        currencyView = findViewById(R.id.wallet_currency);
        switch(currency)
        {
            case "EUR":
                currencyView.setText("€");
                break;
            case "PLN":
                currencyView.setText("zł");
                break;
            case "USD":
                currencyView.setText("$");
                break;
            case "JPY":
                currencyView.setText("¥");
                break;
        }
    }

    void initViews()
    {
        goBackButton = findViewById(R.id.go_back_button);
    }

    void setUpGoBackButton()
    {
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WalletActivity.this, MainActivity.class));
            }
        });
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
        transactions.add(new Transaction(1000.22, new Date(2022,0,12), "Food", "You bought a cat ?"));
        transactions.add(new Transaction(22, new Date(2020,1,3), "Food", "You bought a dog ?"));
        transactions.add(new Transaction(33, new Date(2021,2,22), "Food", "You bought a slime ?"));
    }


}