package com.example.hansb.budgetapp;

import com.birbit.android.jobqueue.Job;
import com.example.hansb.budgetapp.services.BudgetJobService;

import org.apache.logging.log4j.Logger;

/**
 * Created by HansB on 27/12/2016.
 */

public class FakeJobService implements BudgetJobService {

    private final Logger logger;

    public FakeJobService(AppInjector injector) {
        this.logger = injector.getLogger(FakeJobService.class);
    }

    @Override
    public void enqueueJob(Job job) {

    }
}
