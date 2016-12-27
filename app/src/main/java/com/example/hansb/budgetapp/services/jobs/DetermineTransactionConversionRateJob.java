package com.example.hansb.budgetapp.services.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.hansb.budgetapp.business.Transaction;

/**
 * Created by HansB on 27/12/2016.
 */

public class DetermineTransactionConversionRateJob extends Job {

    public static final int PRIORITY_NORMAL = 1;
    private final Transaction transaction;

    public DetermineTransactionConversionRateJob(Transaction transaction) {
        super(new Params(PRIORITY_NORMAL).requireNetwork());
        this.transaction = transaction;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {

    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
