package com.example.hansb.budgetapp.activities;

import android.app.Instrumentation;

import com.example.hansb.budgetapp.R;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by HansB on 25/12/2016.
 */

public class TransactionDetailActivityTests extends TransactionDetailActivityTestBase {
    @Test
    public void testThatADepositTransactionCanBeCreated() {
        getSut();

        setTransactionDetails();
        clickSaveTransaction();

        assertThat(getFakeTransactionRepository().hasANewTransactionHasBeenCreated(), is(true));
    }

    @Test
    public void testThatSavingGoesBackToBudgetOverview() {
        getSut();
        Instrumentation.ActivityMonitor mainActivityMonitor = setupActivityMonitor(MainActivity.class);

        setTransactionDetails();
        clickSaveTransaction();

        waitForActivity(mainActivityMonitor);
    }

    @Test
    public void testThatNegativeAmountDoesNotWork() {
        TransactionDetailActivity activity = getSut();

        setTransactionValue(-1000.00);

        assertThat(getTransactionValue(activity), is(1000.00));
    }

    @Test
    public void testThatDescriptionIsMandatory() {
        getSut();

        clickSaveTransaction();

        onView(withId(R.id.transaction_description)).check(matches(hasErrorText("Description is required")));
    }

    @Test
    public void testThatValueIsMandatory() {
        getSut();

        clickSaveTransaction();

        onView(withId(R.id.transaction_value)).check(matches(hasErrorText("Value is required")));
    }
}
