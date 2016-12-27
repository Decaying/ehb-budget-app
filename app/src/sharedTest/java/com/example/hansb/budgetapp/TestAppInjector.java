package com.example.hansb.budgetapp;

import android.content.Context;

import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.services.BudgetJobService;
import com.example.hansb.budgetapp.services.TimeService;

/**
 * Created by HansB on 23/12/2016.
 */
public class TestAppInjector extends AppInjectorImpl {
    private TransactionRepository transactionRepository;
    private TimeService timeService;
    private BudgetJobService jobService;

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

    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }

    @Override
    public TimeService getTimeService() {
        return timeService;
    }

    public void setJobService(BudgetJobService jobService) {
        this.jobService = jobService;
    }

    @Override
    public BudgetJobService getJobService() {
        return jobService;
    }
}
