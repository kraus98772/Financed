package com.cosmas.moniger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    // Name of the table for wallet transactions has to be the same as the name of the wallet

    private static final String DB_NAME = "WALLETS";

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


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + WALLETS_TABLE_NAME
                + "( " + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COLUMN + " TEXT, "
                + CURRENCY_COLUMN + " TEXT, "
                + IMAGE_COLUMN + " INTEGER)";

        sqLiteDatabase.execSQL(query);
    }

    public void addWallet(Wallet wallet)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COLUMN, wallet.getWalletName());
        values.put(CURRENCY_COLUMN, wallet.getWalletCurrency());
        values.put(IMAGE_COLUMN, wallet.getImage());

        db.insert(WALLETS_TABLE_NAME, null, values);

        String transactionTableQuery = createTransactionTableQuery(wallet);
        db.execSQL(transactionTableQuery);

    }

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
                int walletImage = cursor.getInt(3);

                walletsArrayList.add(new Wallet(walletCurrency, walletName, walletImage));

            } while(cursor.moveToNext());
        }

        cursor.close();
        return walletsArrayList;
    }

    public String createTransactionTableQuery(Wallet wallet)
    {
        return "CREATE TABLE " + wallet.getWalletName().replaceAll("\\s", "")
                + "( " + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + VALUE_COLUMN + " INTEGER, "
                + DAY_COLUMN + " INTEGER, "
                + MONTH_COLUMN + " INTEGER, "
                + YEAR_COLUMN + " INTEGER)";
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WALLETS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}