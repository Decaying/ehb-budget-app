package com.example.hansb.budgetapp;

import com.birbit.android.jobqueue.Job;
import com.example.hansb.budgetapp.services.JobQueue;
import com.noveogroup.android.log.Logger;

/**
 * Created by HansB on 28/12/2016.
 */
public class FakeJobQueue implements JobQueue {
    private final Logger logger;

    public FakeJobQueue(AppInjector injector) {
        this.logger = injector.getLogger(FakeJobQueue.class);
    }

    @Override
    public void addJobInBackground(Job job) {
        logger.v("Added job in background (ID = " + job.getId() + ")");
    }
}
