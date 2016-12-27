package com.example.hansb.budgetapp.activities;

import android.app.Instrumentation;

import com.example.hansb.budgetapp.R;
import com.example.hansb.budgetapp.business.DepositTransaction;
import com.example.hansb.budgetapp.business.WithdrawTransaction;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
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
        assertThat(getFakeTransactionRepository().newlyCreatedTransaction(), instanceOf(DepositTransaction.class));
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

    @Test
    public void testThatAWithdrawTransactionCanBeCreated() {
        getSut();

        setTransactionTypeWithdraw();
        setTransactionDetails();
        clickSaveTransaction();

        assertThat(getFakeTransactionRepository().hasANewTransactionHasBeenCreated(), is(true));
        assertThat(getFakeTransactionRepository().newlyCreatedTransaction(), instanceOf(WithdrawTransaction.class));
    }

    @Test
    public void testThatDefaultCurrencyIsEUR() {
        TransactionDetailActivity activity = getSut();

        assertThat(getCurrency(activity), is("EUR"));
    }

    @Test
    public void testThatCurrencyCanBeDollar() {
        TransactionDetailActivity activity = getSut();

        setCurrency("USD");

        assertThat(getCurrency(activity), is("USD"));
    }

    @Test
    public void testThatWhenCurrencyIsDollarItGetsSavedToDb() {
        getSut();

        setTransactionDetails();
        setCurrency("USD");
        clickSaveTransaction();

        assertThat(getFakeTransactionRepository().hasANewTransactionHasBeenCreated(), is(true));
        assertThat(getFakeTransactionRepository().newlyCreatedTransaction().getCurrency(), is("USD"));
    }

    @Test
    public void testThatWhenSavedTransactionHasCreationDateNow() {
        getSut();

        setTransactionDetails();
        clickSaveTransaction();

        assertThat(getFakeTransactionRepository().newlyCreatedTransaction().getCreatedDateTime(), is(getFakeTimeService().now()));
    }
}
