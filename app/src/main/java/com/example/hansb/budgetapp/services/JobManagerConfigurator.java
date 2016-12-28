package com.example.hansb.budgetapp.services;

import android.content.Context;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.di.DependencyInjector;
import com.birbit.android.jobqueue.log.CustomLogger;
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService;
import com.example.hansb.budgetapp.AppInjector;
import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.services.jobs.DetermineTransactionConversionRateJob;

import org.apache.logging.log4j.Logger;

/**
 * Created by HansB on 27/12/2016.
 */

public class JobManagerConfigurator {

    private final Logger logger;
    private final Context context;
    private JobManager jobmanager;
    private TransactionRepository transactionRepository;

    public JobManagerConfigurator(AppInjector injector) {
        this.logger = injector.getLogger(JobManagerConfigurator.class);
        this.context = injector.getContext();
        this.transactionRepository = injector.getTransactionRepository();
    }

    public JobManager getJobmanager() {
        if (jobmanager == null)
            jobmanager = this.configureJobManager();
        return jobmanager;
    }

    private JobManager configureJobManager() {
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
                        logger.debug(String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        logger.error(String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        logger.error(String.format(text, args));
                    }

                    @Override
                    public void v(String text, Object... args) {
                        logger.info(String.format(text, args));
                    }
                });

        builder.scheduler(FrameworkJobSchedulerService.createSchedulerFor(context,
                BudgetJobServiceImpl.class), true);
        return new JobManager(builder.build());
    }
}
