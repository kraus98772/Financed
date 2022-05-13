package com.cosmas.moniger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;


public class AddWalletActivity extends AppCompatActivity {

    // fields for the dropdown currency menu
    private Spinner currencyChoiceDropdownList;
    private CurrencySpinnerAdapter spinnerAdapter;

    private ImageButton goBackButton;
    private Button addWalletButton;
    private EditText walletNameEditText;
    private RelativeLayout image_wallet, image_cash;

    private int selected_image = 0;
    private boolean isWalletImageSelected = false;
    private boolean isCashImageSelected = false;

    // Holds the currencies (items) for the spinner
    private final String[] currencyNames = {Currency.USD, Currency.EUR, Currency.GBP, Currency.JPY, Currency.PLN};
    private final String[] currencySymbols = {Currency.USD_SYMBOL, Currency.EUR_SYMBOL, Currency.GBP_SYMBOL, Currency.JPY_SYMBOL, Currency.PLN_SYMBOL};

    // TODO: 5/13/22 Store names of drawables in database and create a function to retrieve resource id depending on the name instead of storing ids

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet);

        initViews();

        setupGoBackButton();

        setupSpinnerAdapter();

        currencyChoiceDropdownList.setAdapter(spinnerAdapter);

        setupImageChoice();
        // TODO: 5/11/22 Change storing resource's id and store string literals https://stackoverflow.com/questions/56134995/sqlite-database-cursor-is-returning-the-wrong-resource-ids

        setupAddWalletButton();
    }

    void initViews()
    {
        goBackButton = findViewById(R.id.go_back_button);
        walletNameEditText = findViewById(R.id.edit_text_wallet_name);
        currencyChoiceDropdownList = findViewById(R.id.dropdown_currency_spinner);
        image_wallet = findViewById(R.id.image_wallet);
        image_cash = findViewById(R.id.image_cash);
        addWalletButton = findViewById(R.id.add_wallet_button);
    }

    void setupSpinnerAdapter()
    {
        spinnerAdapter = new CurrencySpinnerAdapter(currencyNames, currencySymbols);
    }

    void setupGoBackButton()
    {
        goBackButton.setOnClickListener(view -> startActivity(new Intent(AddWalletActivity.this, MainActivity.class)));
    }

    void setupImageChoice()
    {
        image_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isWalletImageSelected)
                {
                    isWalletImageSelected = true;
                    image_wallet.setBackgroundResource(R.drawable.wallet_image_background_selected);
                    selected_image = WalletImage.WALLET_IMAGE;

                    isCashImageSelected = false;
                    image_cash.setBackgroundResource(R.drawable.wallet_image_background);
                }
            }
        });

        image_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isCashImageSelected)
                {

                    isCashImageSelected = true;
                    image_cash.setBackgroundResource(R.drawable.wallet_image_background_selected);
                    selected_image = WalletImage.DOLLAR_CASH_IMAGE;

                    isWalletImageSelected = false;
                    image_wallet.setBackgroundResource(R.drawable.wallet_image_background);

                }
            }
        });
    }

    void setupAddWalletButton()
    {
        addWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!areFieldsValid(currencyChoiceDropdownList.getSelectedItem().toString(), walletNameEditText.getText().toString(), selected_image))
                {
                    Toast.makeText(AddWalletActivity.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                }else
                {
                    DBHelper dbHelper = new DBHelper(AddWalletActivity.this);
                    dbHelper.addWallet(new Wallet(currencyChoiceDropdownList.getSelectedItem().toString(), walletNameEditText.getText().toString(), selected_image));
                    dbHelper.close();
                }
                startActivity(new Intent(AddWalletActivity.this, MainActivity.class));
            }
        });
    }

    boolean areFieldsValid(String name, String currency, int image)
    {
        if (name.isEmpty() || name.charAt(0) == ' ') { return false; }
        if(currency.isEmpty()) { return false; }
        return image != 0;
    }

}