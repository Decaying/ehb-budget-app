package com.example.hansb.budgetapp;

import android.content.Context;

import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.services.JobQueue;
import com.example.hansb.budgetapp.services.TimeService;

/**
 * Created by HansB on 23/12/2016.
 */
public class TestAppInjector extends AppInjectorImpl {
    private TransactionRepository transactionRepository;
    private TimeService timeService;

    @Override
    public TransactionRepository getTransactionRepository(Context context) {
        return transactionRepository;
    }

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }

    @Override
    public TimeService getTimeService() {
        return timeService;
    }

    @Override
    public JobQueue getJobQueue(Context context) {
        return new FakeJobQueue(this);
    }
}
