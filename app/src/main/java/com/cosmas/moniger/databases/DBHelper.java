package com.cosmas.moniger.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cosmas.moniger.utils.SimpleDate;
import com.cosmas.moniger.utils.Transaction;
import com.cosmas.moniger.utils.TransactionContent;
import com.cosmas.moniger.utils.Wallet;

import java.util.ArrayList;
import java.util.Collections;

public class DBHelper extends SQLiteOpenHelper {

    // Name of the table for wallet transactions has to be the same as the name of the wallet

    private static final String DB_NAME = "WALLETS";

    // All tables share ID_COLUMN

    // Constants for Table containing all the wallets
    private static final String WALLETS_TABLE_NAME = "WALLET_LIST";
    private static final String ID_COLUMN = "ID";
    private static final String NAME_COLUMN = "NAME";
    private static final String CURRENCY_COLUMN = "CURRENCY";
    private static final String IMAGE_COLUMN = "IMAGE";
    private static final int DB_VERSION = 1;

    // Constants for Table containing wallet's transactions

    private static final String VALUE_COLUMN = "VALUE";
    private static final String DAY_COLUMN = "TRANSACTION_DAY";
    private static final String MONTH_COLUMN = "TRANSACTION_MONTH";
    private static final String YEAR_COLUMN = "TRANSACTION_YEAR";
    private static final String CATEGORY_COLUMN = "TRANSACTION_CATEGORY";
    private static final String DESCRIPTION_COLUMN = "TRANSACTION_DESCRIPTION";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + WALLETS_TABLE_NAME
                + "( " + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COLUMN + " TEXT, "
                + CURRENCY_COLUMN + " TEXT, "
                + IMAGE_COLUMN + " TEXT)";

        sqLiteDatabase.execSQL(query);
    }

    public void addWallet(Wallet wallet)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COLUMN, wallet.getWalletName());
        values.put(CURRENCY_COLUMN, wallet.getWalletCurrency());
        values.put(IMAGE_COLUMN, wallet.getWalletImageName());

        db.insert(WALLETS_TABLE_NAME, null, values);

        String transactionTableQuery = createTransactionTableQuery(wallet);
        db.execSQL(transactionTableQuery);

    }
    public void removeWallet(String walletName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + WALLETS_TABLE_NAME + " WHERE " + NAME_COLUMN + "=\"" + walletName + "\"");
        db.execSQL("DROP TABLE " + createWalletTransactionsTableName(walletName));
        db.close();
    }

    // TODO: 5/10/22 add remove transaction feature
    public void removeTransaction(String walletName, int transactionId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String tableName = createWalletTransactionsTableName(walletName);
        String query = "DELETE FROM " + tableName + " WHERE " + ID_COLUMN + "=" + transactionId;
        db.execSQL(query);
        db.close();
    }

    public boolean isNameAvailable(String walletName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String query = "SELECT * FROM " + WALLETS_TABLE_NAME + " WHERE " + NAME_COLUMN + "=" + walletName;
        try {
            Cursor cursor1 = cursor = db.rawQuery(query, null);
        }catch (Exception e)
        {
            return true;
        }
        cursor.moveToFirst();
        return (cursor.getInt(0) == 0);
    }

    public ArrayList<Transaction> getTransactions(String walletName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String walletTransactionsTableName = createWalletTransactionsTableName(walletName);
        Cursor cursor = db.rawQuery("SELECT * FROM " + walletTransactionsTableName, null);
        ArrayList<Transaction> transactions = new ArrayList<>();

        if(cursor.moveToFirst())
        {
            do {
                int id = cursor.getInt(0);
                double value = cursor.getDouble(1);
                int day = cursor.getInt(2);
                int month = cursor.getInt(3);
                int year = cursor.getInt(4);
                SimpleDate date = new SimpleDate(day, month, year);
                Transaction transaction = new Transaction(value, date, "", "");
                transaction.setId(id);
                transactions.add(transaction);
            }while(cursor.moveToNext());
        }
        cursor.close();
        Collections.reverse(transactions);
        db.close();
        return transactions;
    }

    public TransactionContent getTransactionContent(String walletName, Transaction transaction)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String walletTransactionsTableName = createWalletTransactionsTableName(walletName);
        String query = "SELECT " + CATEGORY_COLUMN + ", " + DESCRIPTION_COLUMN + " FROM " + walletTransactionsTableName + " WHERE ID=" + transaction.getId();

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        String category = cursor.getString(0);
        String description = cursor.getString(1);

        cursor.close();
        db.close();
        return new TransactionContent(category, description);
    }

    // TODO: 5/17/22 if there's a problem with db then check if db.close() is causing any problems
    public ArrayList<Wallet> getWalletsArrayList()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + WALLETS_TABLE_NAME, null);

        ArrayList<Wallet> walletsArrayList = new ArrayList<>();

        if(cursor.moveToFirst())
        {
            do {
                String walletName = cursor.getString(1);
                String walletCurrency = cursor.getString(2);
                String walletImageName = cursor.getString(3);

                walletsArrayList.add(new Wallet(walletCurrency, walletName, walletImageName));

            } while(cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return walletsArrayList;
    }

    public ArrayList<String> getWalletNamesArrayList()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + NAME_COLUMN + " FROM " + WALLETS_TABLE_NAME, null);

        ArrayList<String> walletNamesArrayList = new ArrayList<>();

        if(cursor.moveToFirst())
        {
            do {
                String walletName = cursor.getString(0);
                walletNamesArrayList.add(walletName);

            } while(cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return walletNamesArrayList;
    }

    public String createTransactionTableQuery(Wallet wallet)
    {
        return "CREATE TABLE " + createWalletTransactionsTableName(wallet.getWalletName())
                + "( " + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + VALUE_COLUMN + " REAL, "
                + DAY_COLUMN + " INTEGER, "
                + MONTH_COLUMN + " INTEGER, "
                + YEAR_COLUMN + " INTEGER, "
                + CATEGORY_COLUMN + " TEXT, "
                + DESCRIPTION_COLUMN + " TEXT )";
    }

    public void addTransaction(String walletName, Transaction transaction)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(VALUE_COLUMN, transaction.getTransactionValue());
        values.put(DAY_COLUMN, transaction.getTransactionDate().getDay());
        values.put(MONTH_COLUMN, transaction.getTransactionDate().getMonth());
        values.put(YEAR_COLUMN, transaction.getTransactionDate().getYear());
        values.put(CATEGORY_COLUMN, transaction.getTransactionCategory());
        values.put(DESCRIPTION_COLUMN, transaction.getTransactionDescription());

        String walletTransactionsTableName = createWalletTransactionsTableName(walletName);

        db.insert(walletTransactionsTableName, null, values);
        db.close();
    }

    public String createWalletTransactionsTableName(String walletName)
    {
        return walletName.replaceAll("\\s", "_");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WALLETS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
