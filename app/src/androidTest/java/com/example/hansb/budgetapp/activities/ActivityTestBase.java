package com.example.hansb.budgetapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Created by HansB on 15/12/2016.
 */
@RunWith(AndroidJUnit4.class)
public abstract class ActivityTestBase<T extends Activity> extends InstrumentationTestBase<T> {
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
