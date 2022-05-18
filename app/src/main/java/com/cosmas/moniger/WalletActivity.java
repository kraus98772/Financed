package com.cosmas.moniger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class WalletActivity extends AppCompatActivity {

    private ArrayList<Transaction> transactions = new ArrayList<>();
    private TextView currencyView, walletValueView;
    private ImageButton goBackButton, settingsButton;
    private Button addTransactionButton;
    private String walletName, walletCurrency;

    private RecyclerView recyclerView;

    // TODO: 5/1/22 Add settings to a wallet [ delete wallet, convert into another currency ( can convert simply into a different currency or calculate all transactions
    //  into different currency depending on current course ) ]

    // TODO: 5/11/22 set default date in addTransaction to current day

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);


        getInfoAboutWallet();
        initViews();

        addTransactionsToRecyclerView();

        setupGoBackButton();
        initRecyclerView();

        setupWalletValue();

        setCurrencyView(walletCurrency);

        setupSettingsButton(walletName, walletCurrency);
        setupAddTransactionButton(walletName, walletCurrency);

    }

    void getInfoAboutWallet()
    {
        Bundle walletInfo = getIntent().getExtras();
        walletName = walletInfo.getString("WALLET_NAME");
        walletCurrency = walletInfo.getString("CURRENCY");
    }

    void setCurrencyView(String currency)
    {
        currencyView = findViewById(R.id.wallet_currency);
        currencyView.setText(Currency.getCurrencySign(walletCurrency));
    }

    void initViews()
    {
        goBackButton = findViewById(R.id.go_back_button);
        settingsButton = findViewById(R.id.settings_button);
        walletValueView = findViewById(R.id.wallet_value);
        addTransactionButton = findViewById(R.id.add_transaction_button);
    }

    void setupSettingsButton(String walletName, String walletCurrency)
    {
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletActivity.this, WalletSettingsActivity.class);
                intent.putExtra("WALLET_NAME", walletName);
                intent.putExtra("CURRENCY", walletCurrency);
                startActivity(intent);
            }
        });
    }

    void setupGoBackButton()
    {
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WalletActivity.this, MainActivity.class));
            }
        });
    }

    void setupAddTransactionButton(String walletName, String walletCurrency)
    {
        addTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTransactionActivityIntent = new Intent(WalletActivity.this, AddTransactionActivity.class);
                addTransactionActivityIntent.putExtra("WALLET_NAME", walletName);
                addTransactionActivityIntent.putExtra("CURRENCY", walletCurrency);
                startActivity(addTransactionActivityIntent);
            }
        });
    }

    void setupWalletValue()
    {
        double sum = 0;
        for(int i = 0; i < transactions.size(); i++)
        {
            sum += transactions.get(i).getTransactionValue();
        }
        walletValueView.setText(String.valueOf(sum));
    }

    void initRecyclerView()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.transactions_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        TransactionsRecyclerViewAdapter recyclerViewAdapter = new TransactionsRecyclerViewAdapter(this, transactions, walletName, walletCurrency);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    void addTransactionsToRecyclerView()
    {
        DBHelper dbHelper = new DBHelper(this);
        transactions = dbHelper.getTransactions(walletName);
        dbHelper.close();

    }
}