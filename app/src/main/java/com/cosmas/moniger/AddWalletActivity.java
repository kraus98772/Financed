package com.cosmas.moniger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddWalletActivity extends AppCompatActivity {

    private Spinner currencyChoiceDropdownList;
    private CurrencySpinnerAdapter spinnerAdapter;

    private ImageButton goBackButton;
    private Button addWalletButton;
    private EditText walletNameEditText;

    // It's just an object that contains all the currencies and their symbols
    private Currency currencyNamesHolderObject;
    // Holds the currencies (items) for the spinner
    private ArrayList<String> currenciesNames = new ArrayList<>();
    private ArrayList<String> currenciesSymbols = new ArrayList<>();

    // TODO: 5/1/22 Add image selection to the AddWalletAcitvity

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

        spinnerAdapter = new CurrencySpinnerAdapter(AddWalletActivity.this, currenciesNames, currenciesSymbols);
        currencyChoiceDropdownList.setAdapter(spinnerAdapter);

        walletNameEditText = findViewById(R.id.edit_text_wallet_name);

        addWalletButton = findViewById(R.id.add_wallet_button);
        addWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(AddWalletActivity.this);
                dbHelper.addWallet(new Wallet(String.valueOf(currencyChoiceDropdownList.getSelectedItem().toString()), walletNameEditText.getText().toString(), 12));

            }
        });

    }

    void addCurrencies()
    {
        currenciesNames.add(currencyNamesHolderObject.USD);
        currenciesSymbols.add(currencyNamesHolderObject.USD_SYMBOL);

        currenciesNames.add(currencyNamesHolderObject.GBP);
        currenciesSymbols.add(currencyNamesHolderObject.GBP_SYMBOL);

        currenciesNames.add(currencyNamesHolderObject.EUR);
        currenciesSymbols.add(currencyNamesHolderObject.EUR_SYMBOL);

        currenciesNames.add(currencyNamesHolderObject.JPY);
        currenciesSymbols.add(currencyNamesHolderObject.JPY_SYMBOL);

        currenciesNames.add(currencyNamesHolderObject.PLN);
        currenciesSymbols.add(currencyNamesHolderObject.PLN_SYMBOL);
    }

}