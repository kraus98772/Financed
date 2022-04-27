package com.cosmas.moniger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageButton addWalletButton, settingsButton;
    private RecyclerView walletsRecyclerView;

    private ArrayList<String> walletsNames = new ArrayList<>();
    private ArrayList<Integer> walletsImages = new ArrayList<>();
    private ArrayList<Wallet> wallets = new ArrayList<>();

    void initLayoutElements()
    {
        addWalletButton = findViewById(R.id.add_wallet_button);
        settingsButton = findViewById(R.id.settings_wallet_button);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayoutElements();
        addTestWallets();
        initRecyclerView();


    }

    void initRecyclerView()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        walletsRecyclerView = findViewById(R.id.wallets_recycler_view);
        walletsRecyclerView.setLayoutManager(layoutManager);
        WalletsRecyclerViewAdapter walletsRecyclerViewAdapter = new WalletsRecyclerViewAdapter(this, wallets);
        walletsRecyclerView.setAdapter(walletsRecyclerViewAdapter);
    }

    void addTestWallets()
    {

        // TODO: 4/25/22 fix last wallet not visible when the number of wallets is greater than 4
        wallets.add(new Wallet(new Currency().EUR, "Wallet 1" , new WalletImage().WALLET_IMAGE));
        wallets.add(new Wallet(new Currency().JPY, "Wallet 2" , new WalletImage().DOLAR_CASH_IMAGE));
        wallets.add(new Wallet(new Currency().USD, "Wallet 3" , new WalletImage().WALLET_IMAGE));
        wallets.add(new Wallet(new Currency().PLN, "Wallet 3" , new WalletImage().DOLAR_CASH_IMAGE));


    }
}