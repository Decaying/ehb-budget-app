package com.example.hansb.budgetapp;

import android.content.Context;

import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.budgetapp.sqlite.SqlLiteTransactionRepository;
import com.example.hansb.budgetapp.business.TransactionFactory;
import com.example.hansb.budgetapp.business.TransactionFactoryImpl;
import com.example.hansb.budgetapp.interactor.TransactionInteractor;
import com.example.hansb.budgetapp.interactor.TransactionInteractorImpl;
import com.example.hansb.budgetapp.services.TimeService;
import com.example.hansb.budgetapp.services.TimeServiceImpl;

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

    @Override
    public TransactionRepository getTransactionRepository() {
        return new SqlLiteTransactionRepository(this);
    }

    @Override
    public Logger getLogger(Class<?> type) {
        return LogManager.getLogger(type);
    }

    @Override
    public TransactionInteractor getTransactionInteractor() {
        return new TransactionInteractorImpl(this);
    }

    @Override
    public TransactionFactory getTransactionFactory() {
        return new TransactionFactoryImpl();
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public TimeService getTimeService() {
        return new TimeServiceImpl();
    }
}
