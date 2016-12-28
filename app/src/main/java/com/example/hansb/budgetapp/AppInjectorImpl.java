package com.example.hansb.budgetapp;

import android.content.Context;

import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.budgetapp.sqlite.SqlLiteTransactionRepository;
import com.example.hansb.budgetapp.business.TransactionFactory;
import com.example.hansb.budgetapp.business.TransactionFactoryImpl;
import com.example.hansb.budgetapp.interactor.TransactionInteractor;
import com.example.hansb.budgetapp.interactor.TransactionInteractorImpl;
import com.example.hansb.budgetapp.services.JobQueue;
import com.example.hansb.budgetapp.services.JobQueueConfigurator;
import com.example.hansb.budgetapp.services.TimeService;
import com.example.hansb.budgetapp.services.TimeServiceImpl;
import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;


/**
 * Created by HansB on 23/12/2016.
 */
public class AppInjectorImpl implements AppInjector {

    private static AppInjectorImpl instance = new AppInjectorImpl();

    public static AppInjectorImpl getInstance() {
        return instance;
    }

    public static void setInstance(AppInjectorImpl instance) {
        AppInjectorImpl.instance = instance;
    }

    protected AppInjectorImpl() {
    }


    private JobQueue jobQueue;

    @Override
    public TransactionRepository getTransactionRepository(Context context) {
        return new SqlLiteTransactionRepository(context, this);
    }

    @Override
    public Logger getLogger(Class<?> type) {
        return LoggerManager.getLogger(type);
    }

    @Override
    public TransactionInteractor getTransactionInteractor(Context context) {
        return new TransactionInteractorImpl(context, this);
    }

    @Override
    public TransactionFactory getTransactionFactory() {
        return new TransactionFactoryImpl();
    }

    @Override
    public TimeService getTimeService() {
        return new TimeServiceImpl();
    }

    @Override
    public JobQueue getJobQueue(Context context) {
        if (jobQueue == null) {
            jobQueue = new JobQueueConfigurator(context, this).getJobQueue();
        }
        return jobQueue;
    }
}
