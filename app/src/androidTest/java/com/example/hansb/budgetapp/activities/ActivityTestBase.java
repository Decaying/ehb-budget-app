package com.example.hansb.budgetapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.TouchUtils;
import android.view.View;

import com.example.hansb.budgetapp.InstrumentationTestBase;

import org.junit.Rule;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

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

        getIntent();

        activityRule.launchActivity(getIntent());

        return activityRule.getActivity();
    }

    protected Intent getIntent() {
        return new Intent();
    }

    protected void clickView(View clickableView) {
        TouchUtils.clickView(this, clickableView);
    }


    protected <TView extends View> TView getView(T activity, @IdRes int viewId) {
        TView transactionDescription = (TView) activity.findViewById(viewId);

        assertThat(transactionDescription, is(notNullValue()));

        return transactionDescription;
    }
}
