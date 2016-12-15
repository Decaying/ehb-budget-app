package com.example.hansb.budgetapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationFactory;

/**
 * Created by HansB on 9/12/2016.
 */
public abstract class BaseTest<T> {
    protected final Logger Logger;

    BaseTest() {
        ConfigurationFactory.setConfigurationFactory(new TestConfigurationFactory());
        Logger = LogManager.getContext().getLogger(this.getClass().getName());
    }

    protected abstract T getSut();
}