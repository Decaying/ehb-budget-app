package com.example.hansb.budgetapp.activities;

import android.test.InstrumentationTestCase;

import com.example.hansb.budgetapp.TestBase;
import com.example.hansb.budgetapp.TestConfigurationFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.ConfigurationFactory;

/**
 * Created by HansB on 23/12/2016.
 */

public abstract class InstrumentationTestBase<T> extends InstrumentationTestCase implements TestBase<T> {
    protected final org.apache.logging.log4j.Logger Logger;

    public InstrumentationTestBase() {
        ConfigurationFactory.setConfigurationFactory(new TestConfigurationFactory());
        Logger = LogManager.getContext().getLogger(this.getClass().getName());
    }
}
