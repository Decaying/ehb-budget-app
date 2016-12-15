package com.example.hansb.budgetapp.repository.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.repository.TransactionRepository;

/**
 * Created by HansB on 12/12/2016.
 */

public class SqlLiteTransactionRepository extends SQLiteOpenHelper implements TransactionRepository {

    private static final String DATABASE_NAME = "budgetapp";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TRANSACTIONS = "transactions";
    private static final String KEY_ID = "id";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_VALUE = "value";


    public SqlLiteTransactionRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public Transaction[] getLatestTransations() {
        return new Transaction[0];
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE " + TABLE_TRANSACTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DESCRIPTION + " TEXT,"
                + KEY_VALUE + " REAL" + ")";
        db.execSQL(CREATE_TRANSACTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);
    }
}
