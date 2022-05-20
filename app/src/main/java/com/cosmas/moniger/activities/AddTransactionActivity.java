package com.cosmas.moniger.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cosmas.moniger.adapters.CategorySpinnerAdapter;
import com.cosmas.moniger.databases.DBHelper;
import com.cosmas.moniger.utils.DateTextFormatter;
import com.cosmas.moniger.utils.DecimalDigitsInputFilter;
import com.cosmas.moniger.R;
import com.cosmas.moniger.utils.SimpleDate;
import com.cosmas.moniger.utils.Transaction;

import java.time.LocalDateTime;
import java.util.Calendar;

public class AddTransactionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ImageButton addValueButton, subtractValueButton, goBackButton;
    private Button addTransactionButton;
    private EditText valueEditText, descriptionView;
    private TextView dateText;
    private String[] categories;
    private Spinner categorySpinner;

    private RelativeLayout datePicker;
    private LocalDateTime todayDate;
    private SimpleDate currentlySetDate;

    private String walletName, walletCurrency;

    // Variables for on hold buttons
    Handler handler = new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        initViews();
        getInfoAboutWallet();

        Bundle walletInfo = getInfoAboutWallet();
        walletName = walletInfo.getString("WALLET_NAME");
        walletCurrency = walletInfo.getString("CURRENCY");

        // Initializing default date as today
        todayDate = LocalDateTime.now();
        currentlySetDate = new SimpleDate(todayDate.getDayOfMonth(), todayDate.getMonthValue() - 1, todayDate.getYear());

        setUpGoBackButton(walletName, walletCurrency);

        setupValueEditText();
        setupAddValueButton();
        setupSubtractValueButton();
        setupDatePicker();

        categorySpinner = findViewById(R.id.category_spinner);
        categories = this.getResources().getStringArray(R.array.categories);
        categorySpinner.setAdapter(new CategorySpinnerAdapter(categories));

        setupAddTransactionButton(walletName, walletCurrency);

    }

    void initViews() {
        goBackButton = findViewById(R.id.go_back_button);
        addValueButton = findViewById(R.id.increase_value_button);
        subtractValueButton = findViewById(R.id.decrease_value_button);
        valueEditText = findViewById(R.id.value_edit_text);
        datePicker = findViewById(R.id.date_picker);
        dateText = findViewById(R.id.date_text);
        descriptionView = findViewById(R.id.description_holder);
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

                double transactionValue = Double.parseDouble(valueEditText.getText().toString());

                if(transactionValue == 0)
                {
                    Toast.makeText(AddTransactionActivity.this, "Transaction value can't be 0", Toast.LENGTH_SHORT).show();
                }else
                {
                    DBHelper dbHelper = new DBHelper(AddTransactionActivity.this);
                    dbHelper.addTransaction(walletName,
                            new Transaction(transactionValue,
                                    currentlySetDate, categorySpinner.getSelectedItem().toString(), descriptionView.getText().toString()));
                    goBack(walletName, walletCurrency);
                }

            }
        });
    }

    Bundle getInfoAboutWallet() {
        Bundle walletInfo = getIntent().getExtras();
        walletName = walletInfo.getString("WALLET_NAME");
        walletCurrency = walletInfo.getString("CURRENCY");
        return walletInfo;
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
        String date = dateTextFormatter.formatText(new SimpleDate(dayOfMonth, month, year), "/");
        dateText.setText(date);
        currentlySetDate = new SimpleDate(dayOfMonth, month, year);
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
        goBack(getInfoAboutWallet().getString("WALLET_NAME"), getInfoAboutWallet().getString("CURRENCY"));
    }

    void goBack(String walletName, String walletCurrency)
    {
        Intent intent = new Intent(AddTransactionActivity.this, WalletActivity.class);
        intent.putExtra("WALLET_NAME", walletName);
        intent.putExtra("CURRENCY", walletCurrency);
        startActivity(intent);
    }
}