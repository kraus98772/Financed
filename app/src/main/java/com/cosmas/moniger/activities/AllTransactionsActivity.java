package com.cosmas.moniger.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cosmas.moniger.R;
import com.cosmas.moniger.adapters.TransactionsRecyclerViewAdapter;
import com.cosmas.moniger.databases.DBHelper;
import com.cosmas.moniger.utils.DateTextFormatter;
import com.cosmas.moniger.utils.SimpleDate;
import com.cosmas.moniger.utils.Transaction;

import java.util.ArrayList;
import java.util.Calendar;

public class AllTransactionsActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private ImageButton goBackButton;
    private SimpleDate today;
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private RelativeLayout datePickerButton;
    private String walletName, walletCurrency;
    private RecyclerView recyclerView;
    private TransactionsRecyclerViewAdapter adapter;
    private TextView dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_transactions);

        goBackButton = findViewById(R.id.go_back_button);
        setupGoBackButton();

        today = new SimpleDate();
        datePickerButton = findViewById(R.id.date_picker);
        initDatePicker();
        dateText = findViewById(R.id.date_text);
        dateText.setText(today.toString());

        getInfoAboutWallet();

        //Initialize transactions to ones done today
        initTransactions(today);
        initAdapter();
        initRecyclerView(adapter);

    }

    @Override
    public void onBackPressed() {

    }

    void setupGoBackButton()
    {
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void initAdapter() {
        adapter = new TransactionsRecyclerViewAdapter(AllTransactionsActivity.this, transactions, walletName, walletCurrency);
    }

    void initRecyclerView(TransactionsRecyclerViewAdapter adapter)
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.transactions_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    void getInfoAboutWallet()
    {
        Bundle walletInfo = getIntent().getExtras();
        walletName = walletInfo.getString("WALLET_NAME");
        walletCurrency = walletInfo.getString("CURRENCY");
    }

    void initTransactions(SimpleDate date)
    {
        DBHelper dbHelper = new DBHelper(this);
        transactions = dbHelper.getTransactions(walletName, date.getYear(), date.getMonth(), date.getDay());
        dbHelper.close();
    }

    ArrayList<Transaction> getTransactions(SimpleDate date)
    {
        DBHelper dbHelper = new DBHelper(this);
        transactions = dbHelper.getTransactions(walletName, date.getYear(), date.getMonth(), date.getDay());
        dbHelper.close();
        return transactions;
    }


    // TODO: 5/30/22 Change date style for this: DEC 19 2022
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                SimpleDate date = new SimpleDate(day, month, year);
                String dateFormated = DateTextFormatter.formatText(date, "/");
                // Changing index of month to the number of the month
                month+=1;
                dateText.setText(dateFormated);
                //adapter.addNewTransaction(new Transaction(200.00, new SimpleDate(day, month, year), "", ""));
                adapter.setNewData(getTransactions(date));
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}