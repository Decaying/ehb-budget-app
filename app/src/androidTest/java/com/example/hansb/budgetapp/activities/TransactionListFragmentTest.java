package com.example.hansb.budgetapp.activities;

import com.example.hansb.budgetapp.TestAppInjector;
import com.example.hansb.budgetapp.TransactionListFragment;
import com.example.hansb.budgetapp.repository.FakeTransactionRepository;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by HansB on 15/12/2016.
 */
public class TransactionListFragmentTest extends FragmentTestBase<TransactionListFragment> {
    private TestAppInjector injector = new TestAppInjector();
    private FakeTransactionRepository fakeTransactionRepository;

    public TransactionListFragmentTest() {
        fakeTransactionRepository = new FakeTransactionRepository(injector);
    }

    @Test
    public void testThatListDisplaysTransactionsWhenLoaded() throws Exception {
        fakeTransactionRepository.whenOneDepositTransactionIsAvailable("DEPOSIT", 1.00, "test description");

        getSut();

        assertTrue(fakeTransactionRepository.getAllTransactionsHasBeenCalled());
    }

    @Override
    public TransactionListFragment callConstructor() {
        return new TransactionListFragment(injector);
    }
}

