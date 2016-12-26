package com.example.hansb.budgetapp.activities;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.example.hansb.budgetapp.InstrumentationTestBase;

import org.junit.Rule;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
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

    protected void clickView(@IdRes int viewId) {
        onView(withId(viewId))
                .perform(click());
    }

    protected void typeInView(int viewId, String text) {
        onView(withId(viewId))
                .perform(typeText(text), closeSoftKeyboard());
    }

    protected void clickUpNavigation(TransactionDetailActivity activity) {
        onView(withContentDescription("Navigate up")).perform(click());
    }

    protected <TView extends View> TView getView(T activity, @IdRes int viewId) {
        TView transactionDescription = (TView) activity.findViewById(viewId);

        assertThat(transactionDescription, is(notNullValue()));

        return transactionDescription;
    }

    protected <TActivity extends Activity> Instrumentation.ActivityMonitor setupActivityMonitor(Class<TActivity> activityClass) {
        return getInstrumentation().addMonitor(activityClass.getName(), null, false);
    }

    protected <TActivity extends Activity> TActivity waitForActivity(Instrumentation.ActivityMonitor activityMonitor) {
        TActivity nextActivity = (TActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);

        assertNotNull(nextActivity);

        return nextActivity;
    }
}
