package com.cosmas.moniger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddTransactionActivity extends AppCompatActivity {

    private ImageButton addValueButton, subtractValueButton, goBackButton;
    private Button addTransactionButton;
    private EditText valueEditText, dayEditText, monthEditText, yearEditText;

    private String walletName, walletCurrency;
    private boolean isDayActive, isMonthActive, isYearActive;

    // Variables for on hold buttons
    Handler handler = new Handler();
    Runnable runnable;


    // TODO: 5/7/22 setup date picking
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        initViews();

        getInfoAboutWallet();
        setUpGoBackButton(walletName, walletCurrency);

        setupValueEditText();
        setupAddValueButton();
        setupSubtractValueButton();

        initDateBooleans();
        setupDayEditText();
        setupMonthEditText();
        setupYearEditText();

        setupAddTransactionButton();

    }

    void initViews()
    {
        goBackButton = findViewById(R.id.go_back_button);
        addValueButton = findViewById(R.id.increase_value_button);
        subtractValueButton = findViewById(R.id.decrease_value_button);
        valueEditText = findViewById(R.id.value_edit_text);
        dayEditText = findViewById(R.id.day_edit_text);
        monthEditText = findViewById(R.id.month_edit_text);
        yearEditText = findViewById(R.id.year_edit_text);
        addTransactionButton = findViewById(R.id.add_transaction_button);
    }

    void setupValueEditText()
    {
        valueEditText.setFilters(new InputFilter[]{new DecimalDigitsInputFilter()});
    }

    void setupAddValueButton()
    {
        addValueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float current_value = Float.parseFloat(valueEditText.getText().toString());
                valueEditText.setText(String.valueOf(current_value + 1));
            }
        });

        addValueButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        if(!addValueButton.isPressed()) return;
                        float current_value = Float.parseFloat(valueEditText.getText().toString());
                        valueEditText.setText(String.valueOf(current_value + 1));
                        handler.postDelayed(runnable, 20);
                    }
                };
                handler.postDelayed(runnable, 20);
                return true;
            }
        });
    }

    void setupSubtractValueButton()
    {
        subtractValueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float current_value = Float.parseFloat(valueEditText.getText().toString());
                valueEditText.setText(String.valueOf(current_value - 1));
            }
        });

        subtractValueButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        if(!subtractValueButton.isPressed()) return;
                        float current_value = Float.parseFloat(valueEditText.getText().toString());
                        valueEditText.setText(String.valueOf(current_value - 1));
                        handler.postDelayed(runnable, 20);
                    }
                };
                handler.postDelayed(runnable, 20);
                return true;
            }
        });
    }

    void initDateBooleans()
    {
        isDayActive = false;
        isMonthActive = false;
        isYearActive = false;
    }

    void setupDayEditText()
    {
        dayEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!isDayActive)
                {
                    isDayActive = true;
                    isMonthActive = false;
                    isYearActive = false;

                    dayEditText.setBackgroundResource(R.drawable.date_field_active);
                    monthEditText.setBackgroundResource(R.drawable.date_field);
                    yearEditText.setBackgroundResource(R.drawable.date_field);
                }
            }
        });
    }

    void setupMonthEditText()
    {
        monthEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!isMonthActive)
                {
                    isDayActive = false;
                    isMonthActive = true;
                    isYearActive = false;

                    dayEditText.setBackgroundResource(R.drawable.date_field);
                    monthEditText.setBackgroundResource(R.drawable.date_field_active);
                    yearEditText.setBackgroundResource(R.drawable.date_field);
                }
            }
        });
    }

    void setupYearEditText()
    {
        yearEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!isYearActive)
                {
                    isDayActive = false;
                    isMonthActive = false;
                    isYearActive = true;

                    dayEditText.setBackgroundResource(R.drawable.date_field);
                    monthEditText.setBackgroundResource(R.drawable.date_field);
                    yearEditText.setBackgroundResource(R.drawable.date_field_active);
                }
            }
        });
    }

    void setupAddTransactionButton()
    {
        addTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddTransactionActivity.this, "Wallet's name is " + walletName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getInfoAboutWallet()
    {
        Bundle walletInfo = getIntent().getExtras();
        walletName = walletInfo.getString("WALLET_NAME");
        walletCurrency = walletInfo.getString("CURRENCY");
    }

    void setUpGoBackButton(String walletName, String walletCurrency)
    {
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTransactionActivity.this, WalletActivity.class);
                intent.putExtra("WALLET_NAME", walletName);
                intent.putExtra("CURRENCY", walletCurrency);
                startActivity(intent);
            }
        });
    }

}