package com.example.hansb.budgetapp.repository;

import android.test.suitebuilder.annotation.LargeTest;

import com.example.hansb.budgetapp.AppInjectorImpl;
import com.example.hansb.budgetapp.InstrumentationTestBase;
import com.example.hansb.budgetapp.budgetapp.sqlite.SqlLiteTransactionRepository;
import com.example.hansb.budgetapp.business.Transaction;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by HansB on 24/12/2016.
 */

public class SqlLiteTransactionRepositoryTests extends InstrumentationTestBase<SqlLiteTransactionRepository> {
    @Override
    public SqlLiteTransactionRepository getSut() {
        return new SqlLiteTransactionRepository(new AppInjectorImpl(getInstrumentation().getTargetContext()));
    }

    @LargeTest
    public void testThatDatabaseAccessWorks() throws Exception {
        Transaction[] transactions = getSut().getAllTransactions();

        assertThat(transactions, is(notNullValue()));

        Logger.info(String.format("Found %d transactions", transactions.length));
    }
}
