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
import com.noveogroup.android.log.Logger;

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

        private static final String CURRENCY = "currency";
        private static final String CURRENCY_TYPE = "TEXT NOT NULL";

        private static final String CREATED_AT = "createdAt";
        private static final String CREATED_AT_TYPE = "INTEGER";

        private static final String CONVERSION_RATE = "conversionRate";
        private static final String CONVERSION_RATE_TYPE = "REAL";
    }

    private static final String DATABASE_NAME = "budgetapp.db";
    private static final int DATABASE_VERSION = 5;

    private String[] projection = {
            TransactionEntry.ID,
            TransactionEntry.DESCRIPTION,
            TransactionEntry.VALUE,
            TransactionEntry.TYPE,
            TransactionEntry.CURRENCY,
            TransactionEntry.CREATED_AT,
            TransactionEntry.CONVERSION_RATE
    };

    public SqlLiteTransactionRepository(AppInjector injector) {
        super(injector.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
        this.transactionFactory = (SqlTransactionFactory) injector.getTransactionFactory();
        this.logger = injector.getLogger(SqlLiteTransactionRepository.class);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        logger.d("Create transaction table");
        String CREATE_TRANSACTIONS_TABLE =
                "CREATE TABLE " + TransactionEntry.TABLE_NAME +
                        "(" +
                        TransactionEntry.ID + " " + TransactionEntry.ID_TYPE + "," +
                        TransactionEntry.DESCRIPTION + " " + TransactionEntry.DESCRIPTION_TYPE + "," +
                        TransactionEntry.TYPE + " " + TransactionEntry.TYPE_TYPE + "," +
                        TransactionEntry.VALUE + " " + TransactionEntry.VALUE_TYPE + "," +
                        TransactionEntry.CURRENCY + " " + TransactionEntry.CURRENCY_TYPE + "," +
                        TransactionEntry.CREATED_AT + " " + TransactionEntry.CREATED_AT_TYPE + "," +
                        TransactionEntry.CONVERSION_RATE + " " + TransactionEntry.CONVERSION_RATE_TYPE +
                        ")";

        db.execSQL(CREATE_TRANSACTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        logger.d("Re-create database");

        logger.d("Drop transaction table");
        db.execSQL("DROP TABLE IF EXISTS " + TransactionEntry.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public Transaction[] getAllTransactions() throws Exception {
        logger.d("Initialize database access");
        SQLiteDatabase db = getReadableDatabase();
        Transaction[] transactions = null;

        try {
            logger.d("Loading transactions from database");
            transactions = queryDatabaseForTransactions(db);
            logger.d("Loading transactions from database completed");
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

        logger.d(String.format("loaded %d transactions", numberOfTransactions));
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
        Double conversionRate = cursor.getDouble(cursor.getColumnIndexOrThrow(TransactionEntry.CONVERSION_RATE));
        Long transactionDateTimeFromDb = cursor.getLong(cursor.getColumnIndexOrThrow(TransactionEntry.CREATED_AT));
        Date transactionDateTime = new Date(transactionDateTimeFromDb);

        return transactionFactory.createFromSql(type, id, description, value, currency, transactionDateTime, conversionRate);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        logger.d("Initialize database access");
        SQLiteDatabase db = getWritableDatabase();

        long transactionId;

        try {
            logger.d("Writing transaction to database");
            transactionId = insertTransaction(db, transaction);
            logger.d("Writing transaction to database completed");

            return queryDatabaseForTransaction(db, transactionId);
        } catch (Exception e) {
            logger.e("Unable to write to database", e);
        } finally {
            db.close();
        }
        return null;
    }

    @Override
    public void setConversionRateFor(Long transactionId, Double conversionRate) {
        logger.d("Initialize database access");
        SQLiteDatabase db = getWritableDatabase();

        try {
            logger.d("Setting transaction conversion rate");
            updateTransactionConversionRate(db, transactionId, conversionRate);
            logger.d("Setting transaction conversion rate completed");
        } finally {
            db.close();
        }
    }

    private void updateTransactionConversionRate(SQLiteDatabase db, Long transactionId, Double conversionRate) {
        db.beginTransaction();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TransactionEntry.CONVERSION_RATE, conversionRate);

            int rowsAffected = db.update(TransactionEntry.TABLE_NAME,
                    contentValues,
                    TransactionEntry.ID + "=" + transactionId,
                    null);

            if (rowsAffected != 1)
                logger.e("Unable to update transaction with id " + transactionId);
            else
                db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private long insertTransaction(SQLiteDatabase db, Transaction transaction) {
        ContentValues values = new ContentValues();

        values.put(TransactionEntry.DESCRIPTION, transaction.getDescription());
        values.put(TransactionEntry.VALUE, transaction.getValue());
        values.put(TransactionEntry.CURRENCY, transaction.getCurrency());
        values.put(TransactionEntry.CREATED_AT, transaction.getCreatedDateTime().getTime());
        values.put(TransactionEntry.CONVERSION_RATE, transaction.getConversionRate());

        if (transaction instanceof DepositTransaction)
            values.put(TransactionEntry.TYPE, transactionFactory.getSqlTypeDeposit());
        if (transaction instanceof WithdrawTransaction)
            values.put(TransactionEntry.TYPE, transactionFactory.getSqlTypeWithdraw());

        return db.insertOrThrow(TransactionEntry.TABLE_NAME, null, values);
    }
}
