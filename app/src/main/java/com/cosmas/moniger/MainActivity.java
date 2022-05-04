package com.cosmas.moniger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageButton settingsButton;

    private Button addWalletButton;
    private RecyclerView walletsRecyclerView;

    private ArrayList<Wallet> wallets = new ArrayList<>();

    void initLayoutElements() {
        //addWalletButton = findViewById(R.id.add_wallet_button);
        addWalletButton = findViewById(R.id.add_wallet_button);
        settingsButton = findViewById(R.id.settings_wallet_button);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayoutElements();
        getWallets();
        initRecyclerView();

        addWalletButtonSetup();

    }

    void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        walletsRecyclerView = findViewById(R.id.wallets_recycler_view);
        walletsRecyclerView.setLayoutManager(layoutManager);
        WalletsRecyclerViewAdapter walletsRecyclerViewAdapter = new WalletsRecyclerViewAdapter(this, wallets);
        walletsRecyclerView.setAdapter(walletsRecyclerViewAdapter);
    }

    void addWalletButtonSetup()
    {
        addWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddWalletActivity.class));
            }
        });
    }

    void getWallets()
    {
        // TODO: 4/25/22 fix last wallet not visible when the number of wallets is greater than 5
        DBHelper dbHelper = new DBHelper(MainActivity.this);
        wallets = dbHelper.getWalletsArrayList();
        dbHelper.close();
    }
}