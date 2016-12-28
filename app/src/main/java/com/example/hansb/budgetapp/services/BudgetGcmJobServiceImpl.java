package com.example.hansb.budgetapp.services;

import android.support.annotation.NonNull;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.scheduling.GcmJobSchedulerService;
import com.example.hansb.budgetapp.AppInjectorImpl;

/**
 * Created by HansB on 28/12/2016.
 */
public class BudgetGcmJobServiceImpl extends GcmJobSchedulerService {
    @NonNull
    @Override
    protected JobManager getJobManager() {
        return (JobManager) AppInjectorImpl.getInstance().getJobQueue(getApplication());
    }
}
