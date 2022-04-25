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
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, walletsNames, walletsImages);
        walletsRecyclerView.setAdapter(recyclerViewAdapter);
    }

    void addTestWallets()
    {

        // TODO: 4/25/22 fix last wallet not visible when the number of wallets is greater than 4
        walletsNames.add("Wallet 1");
        walletsNames.add("Wallet 2");
        walletsNames.add("Wallet 3");
        walletsNames.add("Wallet 4");

        walletsImages.add(new WalletImage().WALLET_IMAGE);
        walletsImages.add(new WalletImage().DOLAR_CASH_IMAGE);
        walletsImages.add(new WalletImage().WALLET_IMAGE);
        walletsImages.add(new WalletImage().DOLAR_CASH_IMAGE);


    }
}