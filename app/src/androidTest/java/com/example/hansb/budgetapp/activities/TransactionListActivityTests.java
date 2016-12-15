package com.example.hansb.budgetapp.activities;

import com.example.hansb.budgetapp.TransactionListActivity;
import com.example.hansb.budgetapp.business.TransactionFactory;
import com.example.hansb.budgetapp.business.TransactionFactoryImpl;
import com.example.hansb.budgetapp.interactor.TransactionInteractor;
import com.example.hansb.budgetapp.interactor.TransactionInteractorImpl;
import com.example.hansb.budgetapp.repository.FakeTransactionRepository;

import org.junit.Test;

/**
 * Created by HansB on 15/12/2016.
 */
public class TransactionListActivityTests extends ActivityBaseTest<TransactionListActivity> {
    private final TransactionFactory transactionFactory = new TransactionFactoryImpl();
    private FakeTransactionRepository fakeTransactionRepository;

    public TransactionListActivityTests() {
        super(TransactionListActivity.class);
        fakeTransactionRepository = new FakeTransactionRepository(Logger, transactionFactory);
    }

    @Override
    public TransactionListActivity getSut() {
        TransactionInteractor transactionInteractor = new TransactionInteractorImpl(Logger, fakeTransactionRepository);
        return new TransactionListActivity(transactionInteractor);
    }

    @Test
    public void testThatListDisplaysTransactionsWhenLoaded() throws Exception {
        fakeTransactionRepository.whenOneDepositTransactionIsAvailable("DEPOSIT", 1.00, "test description");

        getSut();

        assertTrue(true);
    }
}
