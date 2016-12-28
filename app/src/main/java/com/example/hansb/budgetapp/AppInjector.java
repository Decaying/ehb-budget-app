package com.example.hansb.budgetapp;

import android.content.Context;

import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.business.TransactionFactory;
import com.example.hansb.budgetapp.interactor.TransactionInteractor;
import com.example.hansb.budgetapp.services.JobQueue;
import com.example.hansb.budgetapp.services.TimeService;
import com.noveogroup.android.log.Logger;

/**
 * Created by HansB on 23/12/2016.
 */
public interface AppInjector {
    TransactionRepository getTransactionRepository(Context context);

    Logger getLogger(Class<?> type);

    TransactionInteractor getTransactionInteractor(Context context);

    TransactionFactory getTransactionFactory();

    TimeService getTimeService();

    JobQueue getJobQueue(Context context);
}
