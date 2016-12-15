package com.example.hansb.budgetapp;

import com.example.hansb.budgetapp.interactor.TransactionInteractor;
import com.example.hansb.budgetapp.repository.TransactionRepository;

import org.apache.logging.log4j.Logger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by HansB on 12/12/2016.
 */

@Module
public class TestBudgetModule extends BudgetModule {

    private Logger logger;
    private TransactionRepository repository;
    private TransactionInteractor.Callback callback;

    public TestBudgetModule(Logger logger) {
        super(null);

        this.logger = logger;
    }

    @Provides
    public Logger provideLogger() {
        return logger;
    }

    @Provides
    public TransactionRepository provideTransactionRepository() {
        return repository;
    }

    public void setRepository(TransactionRepository repository) {
        this.repository = repository;
    }

    public void setCallback(FakeTransactionInteractorCallback callback) {
        this.callback = callback;
    }

    public TransactionInteractor.Callback getCallback() {
        return callback;
    }
}