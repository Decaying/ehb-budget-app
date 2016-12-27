package com.example.hansb.budgetapp.services;

import android.content.Context;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.log.CustomLogger;
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService;
import com.example.hansb.budgetapp.AppInjector;

import org.apache.logging.log4j.Logger;

/**
 * Created by HansB on 27/12/2016.
 */

public class JobManagerConfigurator {

    private final Logger logger;
    private final Context context;
    private JobManager jobmanager;

    public JobManagerConfigurator(AppInjector injector) {
        this.logger = injector.getLogger(JobManagerConfigurator.class);
        this.context = injector.getContext();
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
