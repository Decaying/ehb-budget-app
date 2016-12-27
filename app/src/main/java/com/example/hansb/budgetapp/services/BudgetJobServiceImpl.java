package com.example.hansb.budgetapp.services;

import android.support.annotation.NonNull;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService;
import com.example.hansb.budgetapp.AppInjector;
import com.example.hansb.budgetapp.AppInjectorImpl;
import com.example.hansb.budgetapp.activities.MainActivity;

/**
 * Created by HansB on 27/12/2016.
 */
public class BudgetJobServiceImpl extends FrameworkJobSchedulerService implements BudgetJobService {
    public static AppInjector Injector;

    @NonNull
    @Override
    protected JobManager getJobManager() {
        if (Injector == null)
            Injector = MainActivity.Injector;
        if (Injector == null)
            Injector = new AppInjectorImpl(getApplication());

        return Injector.getJobManagerConfigurator().getJobmanager();
    }

    @Override
    public void enqueueJob(Job job) {
        this.getJobManager().addJobInBackground(job);
    }
}
