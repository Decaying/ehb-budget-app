package com.example.hansb.budgetapp.budgetapp.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.business.TransactionFactory;

import org.apache.logging.log4j.Logger;

/**
 * Created by HansB on 12/12/2016.
 */

public class SqlLiteTransactionRepository extends SQLiteOpenHelper implements TransactionRepository {

    private TransactionFactory transactionFactory;

    private class TransactionEntry {
        private static final String TABLE_NAME = "transactions";

        private static final String ID = "id";
        private static final String ID_TYPE = "INTEGER PRIMARY KEY";

        private static final String DESCRIPTION = "description";
        private static final String DESCRIPTION_TYPE = "TEXT";

        private static final String VALUE = "value";
        private static final String VALUE_TYPE = "REAL";

        private static final String TYPE = "type";
        private static final String TYPE_TYPE = "TEXT";
    }

    private static final String DATABASE_NAME = "budgetapp.db";
    private static final int DATABASE_VERSION = 1;
    private final Logger logger;

    private String[] projection = {
            TransactionEntry.ID,
            TransactionEntry.DESCRIPTION,
            TransactionEntry.VALUE,
            TransactionEntry.TYPE
    };

    public SqlLiteTransactionRepository(Logger logger, Context context, TransactionFactory transactionFactory) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.logger = logger;
        this.transactionFactory = transactionFactory;
    }

    @Override
    public Transaction[] getAllTransactions() throws Exception {
        SQLiteDatabase db = getReadableDatabase();
        Transaction[] transactions = new Transaction[0];

        try {
            transactions = queryDatabaseForTransactions(db);
        } finally {
            db.close();
        }

        return transactions;
    }

    private Transaction[] queryDatabaseForTransactions(SQLiteDatabase db) throws Exception {
        Cursor cursor = null;
        Transaction[] transactions = new Transaction[0];

        try {
            cursor = db.query(TransactionEntry.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null);

            transactions = readTransactionsFrom(cursor);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return transactions;
    }

    @NonNull
    private Transaction[] readTransactionsFrom(Cursor cursor) throws Exception {
        Transaction[] transactions;

        transactions = new Transaction[cursor.getCount()];

        cursor.moveToFirst();
        transactions[0] = createTransactionFrom(cursor);

        int i = 1;
        while (cursor.moveToNext()) {
            transactions[i] = createTransactionFrom(cursor);
            i++;
        }
        return transactions;
    }

    private Transaction createTransactionFrom(Cursor cursor) throws Exception {
        String type = cursor.getString(cursor.getColumnIndexOrThrow(TransactionEntry.TYPE));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(TransactionEntry.DESCRIPTION));
        double value = cursor.getDouble(cursor.getColumnIndexOrThrow(TransactionEntry.VALUE));

        return transactionFactory.create(type, description, value);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        logger.debug("Create database");

        String CREATE_TRANSACTIONS_TABLE =
                "CREATE TABLE " + TransactionEntry.TABLE_NAME +
                        "(" +
                        TransactionEntry.ID + " " + TransactionEntry.ID_TYPE + " PRIMARY KEY," +
                        TransactionEntry.DESCRIPTION + " " + TransactionEntry.DESCRIPTION_TYPE + "," +
                        TransactionEntry.TYPE + " " + TransactionEntry.TYPE_TYPE + "," +
                        TransactionEntry.VALUE + " " + TransactionEntry.VALUE_TYPE +
                        ")";

        db.execSQL(CREATE_TRANSACTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        logger.debug("Re-create database");

        db.execSQL("DROP TABLE IF EXISTS " + TransactionEntry.TABLE_NAME);
        onCreate(db);
    }
}
