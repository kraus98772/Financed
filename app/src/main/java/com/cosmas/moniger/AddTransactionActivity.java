package com.cosmas.moniger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class AddTransactionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ImageButton addValueButton, subtractValueButton, goBackButton;
    private Button addTransactionButton;
    private EditText valueEditText;
    private TextView dateText;

    private RelativeLayout datePicker;
    private Date currentlySetDate;

    private String walletName, walletCurrency;

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
        setupDatePicker();

        setupAddTransactionButton(walletName, walletCurrency);

    }

    void initViews() {

        goBackButton = findViewById(R.id.go_back_button);
        addValueButton = findViewById(R.id.increase_value_button);
        subtractValueButton = findViewById(R.id.decrease_value_button);
        valueEditText = findViewById(R.id.value_edit_text);
        datePicker = findViewById(R.id.date_picker);
        dateText = findViewById(R.id.date_text);
        addTransactionButton = findViewById(R.id.add_transaction_button);
    }

    void setupValueEditText() {
        valueEditText.setFilters(new InputFilter[]{new DecimalDigitsInputFilter()});
    }

    void setupAddValueButton() {
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
                        if (!addValueButton.isPressed()) return;
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

    void setupSubtractValueButton() {
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
                        if (!subtractValueButton.isPressed()) return;
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

    void setupAddTransactionButton(String walletName, String walletCurrency) {
        addTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(AddTransactionActivity.this);
                dbHelper.addTransaction(walletName,
                        new Transaction(Double.parseDouble(valueEditText.getText().toString()),
                                currentlySetDate, "", ""));
                goBack(walletName, walletCurrency);
            }
        });
    }

    void getInfoAboutWallet() {
        Bundle walletInfo = getIntent().getExtras();
        walletName = walletInfo.getString("WALLET_NAME");
        walletCurrency = walletInfo.getString("CURRENCY");
    }

    void setupDatePicker() {
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    void showDatePickerDialog()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        DateTextFormatter dateTextFormatter = new DateTextFormatter();
        String date = dateTextFormatter.formatText(new Date(year, month, dayOfMonth), "/");
        dateText.setText(date);
        currentlySetDate = new Date(year, month + 1, dayOfMonth);
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

    void goBack(String walletName, String walletCurrency)
    {
        Intent intent = new Intent(AddTransactionActivity.this, WalletActivity.class);
        intent.putExtra("WALLET_NAME", walletName);
        intent.putExtra("CURRENCY", walletCurrency);
        startActivity(intent);
    }
}