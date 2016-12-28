package com.example.hansb.budgetapp;

import android.content.Context;

import com.birbit.android.jobqueue.JobManager;
import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.business.TransactionFactory;
import com.example.hansb.budgetapp.interactor.TransactionInteractor;
import com.example.hansb.budgetapp.services.JobManagerConfigurator;
import com.example.hansb.budgetapp.services.TimeService;
import com.noveogroup.android.log.Logger;

/**
 * Created by HansB on 23/12/2016.
 */
public interface AppInjector {
    TransactionRepository getTransactionRepository();

    Logger getLogger(Class<?> type);

    TransactionInteractor getTransactionInteractor();

    TransactionFactory getTransactionFactory();

    Context getContext();

    TimeService getTimeService();

    JobManager getJobManager();

    JobManagerConfigurator getJobManagerConfigurator();
}
