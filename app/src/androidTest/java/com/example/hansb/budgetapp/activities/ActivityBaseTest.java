package com.example.hansb.budgetapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.hansb.budgetapp.BaseTest;
import com.example.hansb.budgetapp.TestConfigurationFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Created by HansB on 15/12/2016.
 */
@RunWith(AndroidJUnit4.class)
public abstract class ActivityBaseTest<T extends Activity> implements BaseTest<T> {
    protected final org.apache.logging.log4j.Logger Logger;

    protected ActivityBaseTest(Class<T> activityClass) {
        ConfigurationFactory.setConfigurationFactory(new TestConfigurationFactory());
        Logger = LogManager.getContext().getLogger(this.getClass().getName());
    }

    @Rule
    public ActivityTestRule<T> activityRule;

    @Override
    public T getSut() {
        activityRule = getRule();

        activityRule.launchActivity(new Intent());

        return activityRule.getActivity();
    }

    protected abstract ActivityTestRule<T> getRule();
}
