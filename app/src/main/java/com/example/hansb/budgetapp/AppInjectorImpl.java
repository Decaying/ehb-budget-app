package com.example.hansb.budgetapp;

import android.content.Context;

import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.budgetapp.sqlite.SqlLiteTransactionRepository;
import com.example.hansb.budgetapp.business.TransactionFactory;
import com.example.hansb.budgetapp.business.TransactionFactoryImpl;
import com.example.hansb.budgetapp.interactor.TransactionInteractor;
import com.example.hansb.budgetapp.interactor.TransactionInteractorImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationFactory;

/**
 * Created by HansB on 23/12/2016.
 */
public class AppInjectorImpl implements AppInjector {

    private Context context;

    public AppInjectorImpl(Context context) {
        this.context = context;

        ConfigurationFactory.setConfigurationFactory(new BudgetAppConfigurationFactory());
    }

    public TransactionRepository getTransactionRepository() {
        return new SqlLiteTransactionRepository(this);
    }

    public Logger getLogger(Class<?> type) {
        return LogManager.getLogger(type);
    }

    public TransactionInteractor getTransactionInteractor() {
        return new TransactionInteractorImpl(this);
    }

    public TransactionFactory getTransactionFactory() {
        return new TransactionFactoryImpl();
    }

    public Context getContext() {
        return context;
    }
}
