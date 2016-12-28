package com.example.hansb.budgetapp;

import android.test.InstrumentationTestCase;

import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;

/**
 * Created by HansB on 23/12/2016.
 */

public abstract class InstrumentationTestBase<T> extends InstrumentationTestCase implements TestBase<T> {
    protected final Logger Logger;
    protected TestAppInjector AppInjector;

    public InstrumentationTestBase() {
        AppInjector = new TestAppInjector();
        Logger = getLogger();

        configureContainer(AppInjector);

        AppInjectorImpl.setInstance(AppInjector);
    }

    private Logger getLogger() {
        return LoggerManager.getLogger(this.getClass());
    }

    public void configureContainer(TestAppInjector injector) {
    }
}
