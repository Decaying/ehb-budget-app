package com.example.hansb.budgetapp.activities;

import android.app.Instrumentation;
import android.view.View;
import android.widget.ListView;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MainActivityTests extends MainActivityTestBase {

    @Test
    public void testThatRepositoryGetsCalledWhenLoaded() throws Exception {
        FakeTransactionRepository.whenOneDepositTransactionIsAvailable();

        getSut();

        assertTrue(FakeTransactionRepository.getAllTransactionsHasBeenCalled());
    }

    @Test
    public void testThatListViewIsPopulated() throws Exception {
        FakeTransactionRepository.whenOneDepositTransactionIsAvailable();
        MainActivity activity = getSut();

        ListView listview = getTransactionListView(activity);

        assertThat(listview.getCount(), is(1));
    }

    @Test
    public void testThatListViewContainsDetails() throws Exception {
        String transactionDescription = "Purchased an instrumentation test";
        FakeTransactionRepository.whenOneDepositTransactionIsAvailable("DEPOSIT", 123.7458, transactionDescription);
        MainActivity activity = getSut();

        View transactionView = getTransactionItemView(activity, 0);

        assertThat(getHeaderLine(transactionView).getText().toString(), is(transactionDescription));
        assertThat(getDetailLine(transactionView).getText().toString(), is("Transaction value: 123.75"));
    }

    @Test
    public void testThatTransactionDetailActivityCanBeCalled() throws Exception {
        getSut();
        Instrumentation.ActivityMonitor activityMonitor = setupActivityMonitor(TransactionDetailActivity.class);

        clickAddTransactionButton();

        waitForActivity(activityMonitor);
    }

    @Test
    public void testThatUpNavigationGoesBackToMainActivity() {
        getSut();
        Instrumentation.ActivityMonitor transactionDetailActivityMonitor = setupActivityMonitor(TransactionDetailActivity.class);
        Instrumentation.ActivityMonitor mainActivityMonitor = setupActivityMonitor(MainActivity.class);

        clickAddTransactionButton();

        TransactionDetailActivity transactionDetailActivity = waitForActivity(transactionDetailActivityMonitor);

        clickUpNavigation(transactionDetailActivity);

        waitForActivity(mainActivityMonitor);
    }
}
