package com.example.hansb.budgetapp.activities;

import android.test.UiThreadTest;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by HansB on 25/12/2016.
 */

public class TransactionDetailActivityTests extends TransactionDetailActivityTestBase {
    @Test
    @UiThreadTest
    public void testThatADepositTransactionCanBeCreated() {
        TransactionDetailActivity activity = getSut();

        setTransactionDetails("Paycheck!", 1000.00);
        clickSaveTransaction(activity);

        assertThat(FakeTransactionRepository.hasANewTransactionHasBeenCreated(), is(true));
    }

}
