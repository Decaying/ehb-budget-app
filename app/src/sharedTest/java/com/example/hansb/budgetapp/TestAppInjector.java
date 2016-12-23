package com.example.hansb.budgetapp;

import android.content.Context;

import com.example.hansb.budgetapp.budgetapp.TransactionRepository;

/**
 * Created by HansB on 23/12/2016.
 */
public class TestAppInjector extends AppInjectorImpl {
    private TransactionRepository transactionRepository;

    public TestAppInjector() {
        super(null);
    }

    @Override
    public TransactionRepository getTransactionRepository() {
        return transactionRepository;
    }

    @Override
    public Context getContext() {
        throw new UnsupportedOperationException("We don't have a context in our tests");
    }

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
