package com.example.hansb.budgetapp.services;

import android.content.Context;
import android.os.Build;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.di.DependencyInjector;
import com.birbit.android.jobqueue.log.CustomLogger;
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService;
import com.birbit.android.jobqueue.scheduling.GcmJobSchedulerService;
import com.example.hansb.budgetapp.AppInjector;
import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.services.jobs.DetermineTransactionConversionRateJob;
import com.example.hansb.budgetapp.services.jobs.JobQueueImpl;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.noveogroup.android.log.Logger;

/**
 * Created by HansB on 27/12/2016.
 */

public class JobQueueConfigurator {

    private final Context context;
    private final Logger logger;
    private JobQueueImpl jobmanager;
    private TransactionRepository transactionRepository;

    public JobQueueConfigurator(Context context, AppInjector injector) {
        this.context = context;
        this.logger = injector.getLogger(JobQueueConfigurator.class);
        this.transactionRepository = injector.getTransactionRepository(context);
    }

    public JobQueueImpl getJobQueue() {
        if (jobmanager == null)
            jobmanager = this.configureJobManager();
        return jobmanager;
    }

    private JobQueueImpl configureJobManager() {
        Configuration.Builder builder = new Configuration.Builder(context)
                .minConsumerCount(1) // always keep at least one consumer alive
                .maxConsumerCount(3) // up to 3 consumers at a time
                .loadFactor(3) // 3 jobs per consumer
                .consumerKeepAlive(120) // wait 2 minute
                .injector(new DependencyInjector() {
                    @Override
                    public void inject(Job job) {
                        DetermineTransactionConversionRateJob determineTransactionConversionRateJob = (DetermineTransactionConversionRateJob) job;

                        if (determineTransactionConversionRateJob != null) {
                            determineTransactionConversionRateJob.setLogger(logger);
                            determineTransactionConversionRateJob.setTransactionRepository(transactionRepository);
                        }
                    }
                })
                .customLogger(new CustomLogger() {
                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        logger.d(String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        logger.e(String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        logger.e(String.format(text, args));
                    }

                    @Override
                    public void v(String text, Object... args) {
                        logger.v(String.format(text, args));
                    }
                });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.scheduler(FrameworkJobSchedulerService.createSchedulerFor(context,
                    BudgetJobServiceImpl.class));
        } else {
            int enableGcm = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
            if (enableGcm == ConnectionResult.SUCCESS) {
                builder.scheduler(GcmJobSchedulerService.createSchedulerFor(context,
                        BudgetGcmJobServiceImpl.class), false);
            }
        }
        return new JobQueueImpl(builder.build());
    }
}
