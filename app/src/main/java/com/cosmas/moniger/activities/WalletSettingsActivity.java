package com.cosmas.moniger.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.cosmas.moniger.databases.DBHelper;
import com.cosmas.moniger.R;

public class WalletSettingsActivity extends AppCompatActivity {

    private ImageButton goBackButton;
    private Button confirmWalletRemovalButton, cancelWalletRemovalButton;
    private RelativeLayout removeWalletButton, popUpRemoveWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_settings);

        initViews();
        Bundle extras = getExtrasfromWalletActivity();
        setUpGoBackButton(extras.getString("WALLET_NAME"), extras.getString("CURRENCY"));
        setUpRemoveWalletButton();
        setUpConfirmWalletRemovalButton();
        setUpCancelWalletRemovalButton();
    }

    Bundle getExtrasfromWalletActivity()
    {
        return getIntent().getExtras();
    }

    void initViews()
    {
        goBackButton = findViewById(R.id.go_back_button);
        removeWalletButton = findViewById(R.id.remove_wallet_button);
        popUpRemoveWallet = findViewById(R.id.remove_wallet_popup);
        confirmWalletRemovalButton = findViewById(R.id.popup_confirm_button);
        cancelWalletRemovalButton = findViewById(R.id.popup_deny_button);
    }

    void setUpGoBackButton(String walletName, String walletCurrency)
    {
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack(walletName, walletCurrency);
            }
        });
    }

    @Override
    public void onBackPressed() {
        goBack(getExtrasfromWalletActivity().getString("WALLET_NAME"), getExtrasfromWalletActivity().getString("CURRENCY"));
    }

    void goBack(String walletName, String walletCurrency)
    {
        Intent intent = new Intent(WalletSettingsActivity.this, WalletActivity.class);
        intent.putExtra("WALLET_NAME", walletName);
        intent.putExtra("CURRENCY", walletCurrency);
        startActivity(intent);
    }

    void setUpRemoveWalletButton()
    {
        removeWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpRemoveWallet.setVisibility(View.VISIBLE);
            }
        });
    }

    void setUpConfirmWalletRemovalButton()
    {
        confirmWalletRemovalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(WalletSettingsActivity.this);
                dbHelper.removeWallet(getExtrasfromWalletActivity().getString("WALLET_NAME"));
                dbHelper.close();
                startActivity(new Intent(WalletSettingsActivity.this, MainActivity.class));
            }
        });
    }

    void setUpCancelWalletRemovalButton()
    {
        cancelWalletRemovalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpRemoveWallet.setVisibility(View.GONE);
            }
        });
    }
}