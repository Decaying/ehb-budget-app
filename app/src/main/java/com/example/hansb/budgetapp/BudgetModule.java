package com.example.hansb.budgetapp;

import android.content.Context;

import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.interactor.TransactionInteractor;
import com.example.hansb.budgetapp.repository.TransactionRepository;
import com.example.hansb.budgetapp.repository.sqlite.SqlLiteTransactionRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by HansB on 12/12/2016.
 */
@Module
public class BudgetModule {
    protected final Context context;

    public BudgetModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context context() {
        return context;
    }

    @Provides
    public Logger provideLogger() {
        return LogManager.getLogger();
    }

    @Provides
    public TransactionInteractor.Callback provideCallback() {
        return new TransactionInteractor.Callback() {
            @Override
            public void onTransactionsRetrieved(Transaction[] message) {

            }
        };
    }

    @Provides
    public TransactionRepository provideTransactionRepository(Context context) {
        return new SqlLiteTransactionRepository(context);
    }
}