package com.example.hansb.budgetapp.activities;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by HansB on 25/12/2016.
 */

public class TransactionDetailActivityTests extends TransactionDetailActivityTestBase {
    @Test
    public void testThatADepositTransactionCanBeCreated() {
        getSut();

        setTransactionDetails("Paycheck!", 1000.00);
        clickSaveTransaction();

        assertThat(FakeTransactionRepository.hasANewTransactionHasBeenCreated(), is(true));
    }

}
