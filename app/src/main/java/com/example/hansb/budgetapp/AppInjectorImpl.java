package com.example.hansb.budgetapp;

import android.content.Context;

import com.birbit.android.jobqueue.JobManager;
import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.budgetapp.sqlite.SqlLiteTransactionRepository;
import com.example.hansb.budgetapp.business.TransactionFactory;
import com.example.hansb.budgetapp.business.TransactionFactoryImpl;
import com.example.hansb.budgetapp.interactor.TransactionInteractor;
import com.example.hansb.budgetapp.interactor.TransactionInteractorImpl;
import com.example.hansb.budgetapp.services.JobManagerConfigurator;
import com.example.hansb.budgetapp.services.TimeService;
import com.example.hansb.budgetapp.services.TimeServiceImpl;
import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;


/**
 * Created by HansB on 23/12/2016.
 */
public class AppInjectorImpl implements AppInjector {

    private Context context;
    private JobManager jobManager;

    public AppInjectorImpl(Context context) {
        this.context = context;
    }

    @Override
    public TransactionRepository getTransactionRepository() {
        return new SqlLiteTransactionRepository(this);
    }

    @Override
    public Logger getLogger(Class<?> type) {
        return LoggerManager.getLogger(type);
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

    @Override
    public JobManager getJobManager() {
        if (jobManager == null) {
            jobManager = new JobManagerConfigurator(this).getJobmanager();
        }
        return jobManager;
    }

    @Override
    public JobManagerConfigurator getJobManagerConfigurator() {
        return new JobManagerConfigurator(this);
    }
}
