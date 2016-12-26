package com.example.hansb.budgetapp.budgetapp.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.hansb.budgetapp.AppInjector;
import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.business.DepositTransaction;
import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.business.TransactionFactory;

import org.apache.logging.log4j.Logger;

/**
 * Created by HansB on 12/12/2016.
 */

public class SqlLiteTransactionRepository extends SQLiteOpenHelper implements TransactionRepository {
    private TransactionFactory transactionFactory;
    private Logger logger;

    private class TransactionEntry {
        private static final String TABLE_NAME = "transactions";

        private static final String ID = "id";
        private static final String ID_TYPE = "INTEGER PRIMARY KEY";

        private static final String DESCRIPTION = "description";
        private static final String DESCRIPTION_TYPE = "TEXT";

        private static final String VALUE = "value";
        private static final String VALUE_TYPE = "REAL";

        private static final String TYPE = "type";
        private static final String TYPE_TYPE = "TEXT NOT NULL";
    }

    private static final String DATABASE_NAME = "budgetapp.db";
    private static final int DATABASE_VERSION = 2;

    private String[] projection = {
            TransactionEntry.ID,
            TransactionEntry.DESCRIPTION,
            TransactionEntry.VALUE,
            TransactionEntry.TYPE
    };

    public SqlLiteTransactionRepository(AppInjector injector) {
        super(injector.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
        this.transactionFactory = injector.getTransactionFactory();
        this.logger = injector.getLogger(SqlLiteTransactionRepository.class);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        logger.debug("Create transaction table");
        String CREATE_TRANSACTIONS_TABLE =
                "CREATE TABLE " + TransactionEntry.TABLE_NAME +
                        "(" +
                        TransactionEntry.ID + " " + TransactionEntry.ID_TYPE + "," +
                        TransactionEntry.DESCRIPTION + " " + TransactionEntry.DESCRIPTION_TYPE + "," +
                        TransactionEntry.TYPE + " " + TransactionEntry.TYPE_TYPE + "," +
                        TransactionEntry.VALUE + " " + TransactionEntry.VALUE_TYPE +
                        ")";

        db.execSQL(CREATE_TRANSACTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        logger.debug("Re-create database");

        logger.debug("Drop transaction table");
        db.execSQL("DROP TABLE IF EXISTS " + TransactionEntry.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public Transaction[] getAllTransactions() throws Exception {
        logger.debug("Initialize database access");
        SQLiteDatabase db = getReadableDatabase();
        Transaction[] transactions = null;

        try {
            logger.debug("Loading transactions from database");
            transactions = queryDatabaseForTransactions(db);
            logger.debug("Loading transactions from database completed");
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

        int numberOfTransactions = cursor.getCount();

        logger.debug(String.format("loaded %d transactions", numberOfTransactions));
        transactions = new Transaction[numberOfTransactions];

        if (cursor.moveToFirst()) {
            transactions[0] = createTransactionFrom(cursor);

            int i = 1;
            while (cursor.moveToNext()) {
                transactions[i] = createTransactionFrom(cursor);
                i++;
            }
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
    public void createTransaction(Transaction transaction) {
        logger.debug("Initialize database access");
        SQLiteDatabase db = getWritableDatabase();

        try {
            logger.debug("Writing transaction to database");
            insertTransaction(db, transaction);
            logger.debug("Writing transaction to database completed");
        } finally {
            db.close();
        }
    }

    private void insertTransaction(SQLiteDatabase db, Transaction transaction) {
        ContentValues values = new ContentValues();

        values.put(TransactionEntry.DESCRIPTION, transaction.getDescription());
        values.put(TransactionEntry.VALUE, transaction.getValue());

        if (transaction instanceof DepositTransaction)
            values.put(TransactionEntry.TYPE, "DEPOSIT");

        db.insertOrThrow(TransactionEntry.TABLE_NAME, null, values);
    }
}
