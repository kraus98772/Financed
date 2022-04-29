package com.cosmas.moniger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddWalletActivity extends AppCompatActivity {

    private Spinner currencyChoiceDropdownList;
    private CurrencySpinnerAdapter spinnerAdapter;
    private ImageButton goBackButton;
    private ArrayList<String> currencies = new ArrayList<>();
    // It's just an object that contains all the currencies and their symbols
    private Currency currencyNamesHolderObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet);
        // currency holder initialization
        currencyNamesHolderObject = new Currency();
        goBackButton = findViewById(R.id.go_back_button);

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddWalletActivity.this, MainActivity.class));
            }
        });
        addCurrencies();

        currencyChoiceDropdownList = findViewById(R.id.dropdown_currency_spinner);

        spinnerAdapter = new CurrencySpinnerAdapter(AddWalletActivity.this, currencies);
        currencyChoiceDropdownList.setAdapter(spinnerAdapter);

    }

    void addCurrencies()
    {
        currencies.add(currencyNamesHolderObject.USD_SYMBOL + " " + currencyNamesHolderObject.USD);
        currencies.add(currencyNamesHolderObject.GBP_SYMBOL + " " + currencyNamesHolderObject.GBP);
        currencies.add(currencyNamesHolderObject.EUR_SYMBOL + " " + currencyNamesHolderObject.EUR);
        currencies.add(currencyNamesHolderObject.JPY_SYMBOL + " " + currencyNamesHolderObject.JPY);
        currencies.add(currencyNamesHolderObject.PLN_SYMBOL + " " + currencyNamesHolderObject.PLN);

    }
}