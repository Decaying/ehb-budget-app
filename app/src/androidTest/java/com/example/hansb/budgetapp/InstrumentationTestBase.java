package com.example.hansb.budgetapp;

import android.test.InstrumentationTestCase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.ConfigurationFactory;

/**
 * Created by HansB on 23/12/2016.
 */

public abstract class InstrumentationTestBase<T> extends InstrumentationTestCase implements TestBase<T> {
    protected final org.apache.logging.log4j.Logger Logger;
    protected final TestAppInjector TestAppInjector;

    public InstrumentationTestBase() {
        TestAppInjector = new TestAppInjector();
        ConfigurationFactory.setConfigurationFactory(new TestConfigurationFactory());
        Logger = LogManager.getContext().getLogger(this.getClass().getName());
    }

    public void configureContainer(TestAppInjector injector) {
    }
}
