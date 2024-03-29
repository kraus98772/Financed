package com.cosmas.moniger.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TextView;

import com.cosmas.moniger.utils.Currency;
import com.cosmas.moniger.databases.DBHelper;
import com.cosmas.moniger.R;
import com.cosmas.moniger.utils.Transaction;
import com.cosmas.moniger.adapters.TransactionsRecyclerViewAdapter;

import java.util.ArrayList;

public class WalletActivity extends AppCompatActivity {

    private ArrayList<Transaction> transactions = new ArrayList<>();
    private TextView currencyView, walletValueView;
    private ImageButton goBackButton, settingsButton;
    private Button addTransactionButton, allTransactionsButton;
    private String walletName, walletCurrency;

    private RecyclerView recyclerView;

    // TODO: 5/1/22 Add settings to a wallet [ delete wallet, convert into another currency ( can convert simply into a different currency or calculate all transactions
    //  into different currency depending on current course ) ]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);


        getInfoAboutWallet();
        initViews();

        addTransactionsToRecyclerView();

        setupGoBackButton();
        initRecyclerView();

        setCurrencyView(walletCurrency);
        setupWalletValue();

        setupSettingsButton(walletName, walletCurrency);
        setupAddTransactionButton(walletName, walletCurrency);
        setupAllTransactionsButton(walletName, walletCurrency);

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
        allTransactionsButton = findViewById(R.id.all_transactions_button);
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(WalletActivity.this, MainActivity.class));
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
        if(sum < 0)
        {
            walletValueView.setTextColor(Color.parseColor("#BB342F"));
            currencyView.setTextColor(Color.parseColor("#BB342F"));
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

    void setupAllTransactionsButton(String walletName, String walletCurrency)
    {
        allTransactionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletActivity.this, AllTransactionsActivity.class);
                intent.putExtra("WALLET_NAME", walletName);
                intent.putExtra("CURRENCY", walletCurrency);
                startActivity(intent);
            }
        });
    }
}