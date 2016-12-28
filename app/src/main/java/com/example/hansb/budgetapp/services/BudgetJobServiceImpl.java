package com.example.hansb.budgetapp.services;

import android.support.annotation.NonNull;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService;
import com.example.hansb.budgetapp.AppInjectorImpl;

/**
 * Created by HansB on 27/12/2016.
 */
public class BudgetJobServiceImpl extends FrameworkJobSchedulerService {
    @NonNull
    @Override
    protected JobManager getJobManager() {
        return (JobManager) AppInjectorImpl.getInstance().getJobQueue(getApplication());
    }
}
