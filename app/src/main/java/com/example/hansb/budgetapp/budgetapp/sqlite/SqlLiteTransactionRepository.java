package com.example.hansb.budgetapp.budgetapp.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.hansb.budgetapp.AppInjector;
import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.business.DepositTransaction;
import com.example.hansb.budgetapp.business.SqlTransactionFactory;
import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.business.WithdrawTransaction;

import org.apache.logging.log4j.Logger;

import java.util.Date;

/**
 * Created by HansB on 12/12/2016.
 */

public class SqlLiteTransactionRepository extends SQLiteOpenHelper implements TransactionRepository {
    private SqlTransactionFactory transactionFactory;
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

        private static final String CURRENCY = "CURRENCY";
        private static final String CURRENCY_TYPE = "TEXT NOT NULL";

        private static final String CREATED_AT = "CREATEDAT";
        private static final String CREATED_AT_TYPE = "INTEGER";
    }

    private static final String DATABASE_NAME = "budgetapp.db";
    private static final int DATABASE_VERSION = 4;

    private String[] projection = {
            TransactionEntry.ID,
            TransactionEntry.DESCRIPTION,
            TransactionEntry.VALUE,
            TransactionEntry.TYPE,
            TransactionEntry.CURRENCY,
            TransactionEntry.CREATED_AT
    };

    public SqlLiteTransactionRepository(AppInjector injector) {
        super(injector.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
        this.transactionFactory = (SqlTransactionFactory) injector.getTransactionFactory();
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
                        TransactionEntry.VALUE + " " + TransactionEntry.VALUE_TYPE + "," +
                        TransactionEntry.CURRENCY + " " + TransactionEntry.CURRENCY_TYPE + "," +
                        TransactionEntry.CREATED_AT + " " + TransactionEntry.CREATED_AT_TYPE +
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

    private Transaction queryDatabaseForTransaction(SQLiteDatabase db, long id) throws Exception {
        Cursor cursor = null;
        Transaction transaction = null;

        try {
            cursor = db.query(TransactionEntry.TABLE_NAME,
                    projection,
                    TransactionEntry.ID + "=" + id,
                    null,
                    null,
                    null,
                    null);

            transaction = readTransactionFrom(cursor);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return transaction;
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

    @NonNull
    private Transaction readTransactionFrom(Cursor cursor) throws Exception {
        Transaction transaction = null;

        if (cursor.moveToFirst()) {
            transaction = createTransactionFrom(cursor);
        }

        return transaction;
    }

    private Transaction createTransactionFrom(Cursor cursor) throws Exception {
        Long id = cursor.getLong(cursor.getColumnIndex(TransactionEntry.ID));
        String type = cursor.getString(cursor.getColumnIndexOrThrow(TransactionEntry.TYPE));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(TransactionEntry.DESCRIPTION));
        double value = cursor.getDouble(cursor.getColumnIndexOrThrow(TransactionEntry.VALUE));
        String currency = cursor.getString(cursor.getColumnIndexOrThrow(TransactionEntry.CURRENCY));
        Long transactionDateTimeFromDb = cursor.getLong(cursor.getColumnIndexOrThrow(TransactionEntry.CREATED_AT));
        Date transactionDateTime = new Date(transactionDateTimeFromDb);

        return transactionFactory.createFromSql(type, id, description, value, currency, transactionDateTime);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        logger.debug("Initialize database access");
        SQLiteDatabase db = getWritableDatabase();

        long transactionId;

        try {
            logger.debug("Writing transaction to database");
            transactionId = insertTransaction(db, transaction);
            logger.debug("Writing transaction to database completed");

            return queryDatabaseForTransaction(db, transactionId);
        } catch (Exception e) {
            logger.error("Unable to write to database", e);
        } finally {
            db.close();
        }
        return null;
    }

    private long insertTransaction(SQLiteDatabase db, Transaction transaction) {
        ContentValues values = new ContentValues();

        values.put(TransactionEntry.DESCRIPTION, transaction.getDescription());
        values.put(TransactionEntry.VALUE, transaction.getValue());
        values.put(TransactionEntry.CURRENCY, transaction.getCurrency());
        values.put(TransactionEntry.CREATED_AT, transaction.getCreatedDateTime().getTime());

        if (transaction instanceof DepositTransaction)
            values.put(TransactionEntry.TYPE, transactionFactory.getSqlTypeDeposit());
        if (transaction instanceof WithdrawTransaction)
            values.put(TransactionEntry.TYPE, transactionFactory.getSqlTypeWithdraw());

        return db.insertOrThrow(TransactionEntry.TABLE_NAME, null, values);
    }
}
