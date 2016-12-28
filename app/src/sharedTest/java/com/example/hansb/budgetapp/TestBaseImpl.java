package com.example.hansb.budgetapp;

import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;

;

/**
 * Created by HansB on 9/12/2016.
 */
public abstract class TestBaseImpl<T> implements TestBase<T> {
    protected final Logger Logger;

    public TestBaseImpl() {
        Logger = getLogger();
    }

    private Logger getLogger() {
        return LoggerManager.getLogger(this.getClass());
    }
}