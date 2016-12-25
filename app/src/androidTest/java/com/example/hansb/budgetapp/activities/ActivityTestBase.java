package com.example.hansb.budgetapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.hansb.budgetapp.InstrumentationTestBase;

import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Created by HansB on 15/12/2016.
 */
@RunWith(AndroidJUnit4.class)
public abstract class ActivityTestBase<T extends Activity> extends InstrumentationTestBase<T> {
    private final Class<T> activityType;
    @Rule
    public ActivityTestRule<T> activityRule;

    public ActivityTestBase(Class<T> activityType) {
        super();
        this.activityType = activityType;
    }

    @Override
    public T getSut() {
        activityRule = new ActivityTestRule<>(activityType, true, false);
        this.injectInstrumentation(InstrumentationRegistry.getInstrumentation());

        activityRule.launchActivity(new Intent());

        return activityRule.getActivity();
    }
}
