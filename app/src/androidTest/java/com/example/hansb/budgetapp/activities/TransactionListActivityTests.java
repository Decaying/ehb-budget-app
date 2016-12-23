package com.example.hansb.budgetapp.activities;

import android.support.test.rule.ActivityTestRule;

import com.example.hansb.budgetapp.TransactionListFragment;
import com.example.hansb.budgetapp.business.TransactionFactory;
import com.example.hansb.budgetapp.business.TransactionFactoryImpl;
import com.example.hansb.budgetapp.interactor.TransactionInteractor;
import com.example.hansb.budgetapp.interactor.TransactionInteractorImpl;
import com.example.hansb.budgetapp.repository.FakeTransactionRepository;

import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by HansB on 15/12/2016.
 */
public class TransactionListActivityTests extends ActivityBaseTest<TransactionListFragment> {
    private final TransactionFactory transactionFactory = new TransactionFactoryImpl();
    private FakeTransactionRepository fakeTransactionRepository;
    private final TransactionInteractor transactionInteractor;

    public TransactionListActivityTests() {
        super(TransactionListFragment.class);
        fakeTransactionRepository = new FakeTransactionRepository(Logger, transactionFactory);
        transactionInteractor = new TransactionInteractorImpl(Logger, fakeTransactionRepository);
    }

    @Override
    protected ActivityTestRule<TransactionListFragment> getRule() {
        return new TransactionListActivityTestRule();
    }

    @Test
    @Ignore
    public void testThatListDisplaysTransactionsWhenLoaded() throws Exception {
        fakeTransactionRepository.whenOneDepositTransactionIsAvailable("DEPOSIT", 1.00, "test description");

        getSut();

        assertTrue(fakeTransactionRepository.getAllTransactionsHasBeenCalled());
    }

    private class TransactionListActivityTestRule extends ActivityTestRule<TransactionListFragment> {
        public TransactionListActivityTestRule() {
            super(TransactionListFragment.class);
        }

        @Override
        protected void afterActivityLaunched() {
            TransactionListFragment activity = getActivity();
            activity.setTransactionInteractor(transactionInteractor);

            super.afterActivityLaunched();
        }
    }
}

