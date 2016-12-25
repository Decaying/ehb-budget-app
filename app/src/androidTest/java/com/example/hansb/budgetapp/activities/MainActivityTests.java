package com.example.hansb.budgetapp.activities;

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
        MainActivity activity = getSut();

        callAndWaitForTransactionDetailActivity(activity);
    }
}
