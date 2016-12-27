package com.example.hansb.budgetapp.services;

import com.birbit.android.jobqueue.Job;

/**
 * Created by HansB on 27/12/2016.
 */
public interface BudgetJobService {
    void enqueueJob(Job job);
}
