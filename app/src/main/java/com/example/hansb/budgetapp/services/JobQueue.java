package com.example.hansb.budgetapp.services;

import com.birbit.android.jobqueue.Job;

/**
 * Created by HansB on 28/12/2016.
 */

public interface JobQueue {
    void addJobInBackground(Job job);
}
