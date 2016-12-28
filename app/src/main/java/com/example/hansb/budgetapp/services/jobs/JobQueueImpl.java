package com.example.hansb.budgetapp.services.jobs;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.example.hansb.budgetapp.services.JobQueue;

/**
 * Created by HansB on 28/12/2016.
 */

public class JobQueueImpl extends JobManager implements JobQueue {
    /**
     * Creates a JobManager with the given configuration
     *
     * @param configuration The configuration to be used for the JobManager
     * @see Configuration.Builder
     */
    public JobQueueImpl(Configuration configuration) {
        super(configuration);
    }
}
