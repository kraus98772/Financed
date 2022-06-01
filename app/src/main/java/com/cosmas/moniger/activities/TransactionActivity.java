package com.cosmas.moniger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cosmas.moniger.utils.Currency;
import com.cosmas.moniger.databases.DBHelper;
import com.cosmas.moniger.R;


public class TransactionActivity extends AppCompatActivity {

    ImageButton goBackButton, removeTransactionButton;
    TextView transactionValueView, transactionCurrencyView, transactionDateView, transactionCategoryView, transactionDescriptionView;
    String walletName, walletCurrency, transactionDate, transactionCategory, transactionDescription;
    RelativeLayout popupWarning;
    Button confirmRemoveButton, cancelRemoveButton;

    double transactionValue;
    int transactionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        initViews();
        getTransactionInfo();
        setupViewValues();

        setupRemoveTransactionButton();
        setupConfirmRemoveButton();
        setupCancelRemoveButton();

        setupGoBackButton();
    }

    void initViews()
    {
        removeTransactionButton = findViewById(R.id.delete_transaction_button);
        goBackButton = findViewById(R.id.go_back_button);
        transactionValueView = findViewById(R.id.transaction_value);
        transactionCurrencyView = findViewById(R.id.transaction_currency);
        transactionDateView = findViewById(R.id.transaction_date_holder);
        transactionCategoryView = findViewById(R.id.category_holder);
        transactionDescriptionView = findViewById(R.id.description_holder);
        popupWarning = findViewById(R.id.remove_wallet_popup);
        confirmRemoveButton = findViewById(R.id.popup_confirm_button);
        cancelRemoveButton = findViewById(R.id.popup_deny_button);
    }


    void getTransactionInfo()
    {
        Bundle transactionInfo = getIntent().getExtras();
        walletName = transactionInfo.getString("WALLET_NAME");
        walletCurrency = transactionInfo.getString("CURRENCY");
        transactionValue = transactionInfo.getDouble("VALUE");
        transactionDate = transactionInfo.getString("DATE");
        transactionCategory = transactionInfo.getString("CATEGORY");
        transactionDescription = transactionInfo.getString("DESCRIPTION");
        transactionId = transactionInfo.getInt("ID");
    }

    void setupViewValues(){
        transactionValueView.setText(String.valueOf(transactionValue));
        transactionCurrencyView.setText(Currency.getCurrencySign(walletCurrency));
        transactionDateView.setText(transactionDate);
        transactionCategoryView.setText(transactionCategory);
        transactionDescriptionView.setText(transactionDescription);
    }

    void setupGoBackButton()
    {
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    void goBack()
    {
        finish();
    }

    void setupRemoveTransactionButton()
    {
        removeTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWarning.setVisibility(View.VISIBLE);
            }
        });
    }

    void setupConfirmRemoveButton()
    {
        confirmRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(TransactionActivity.this);

                dbHelper.removeTransaction(walletName, transactionId);
                dbHelper.close();

                Intent intent = new Intent(TransactionActivity.this, WalletActivity.class);
                intent.putExtra("WALLET_NAME", walletName);
                intent.putExtra("CURRENCY", walletCurrency);
                startActivity(intent);
            }
        });
    }
    void setupCancelRemoveButton()
    {
        cancelRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWarning.setVisibility(View.GONE);
            }
        });
    }
}