package com.example.hansb.budgetapp.services;

import android.support.annotation.NonNull;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.scheduling.GcmJobSchedulerService;
import com.example.hansb.budgetapp.AppInjector;
import com.example.hansb.budgetapp.AppInjectorImpl;
import com.example.hansb.budgetapp.activities.MainActivity;

/**
 * Created by HansB on 28/12/2016.
 */
public class BudgetGcmJobServiceImpl extends GcmJobSchedulerService {
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
}
