package com.example.hansb.budgetapp.interactor;

import com.example.hansb.budgetapp.BaseTestImpl;
import com.example.hansb.budgetapp.business.DepositTransaction;
import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.business.TransactionFactory;
import com.example.hansb.budgetapp.business.TransactionFactoryImpl;
import com.example.hansb.budgetapp.repository.FakeTransactionRepository;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TransactionInteractorImplTests extends BaseTestImpl<TransactionInteractorImpl> {
    private final FakeCallback callback;
    private final TransactionFactory transactionFactory = new TransactionFactoryImpl();
    FakeTransactionRepository repository;

    public TransactionInteractorImplTests() {
        super();

        repository = new FakeTransactionRepository(Logger, transactionFactory);
        callback = new FakeCallback();
    }

    @Override
    public TransactionInteractorImpl getSut() {
        return new TransactionInteractorImpl(Logger, repository);
    }

    @Test
    public void canLoadTransactions() throws Exception {
        String transactionType = "DEPOSIT";
        String transactionDescription = "a Euro saved, is a Euro earned.";
        double transactionValue = 1.00;

        repository.whenOneDepositTransactionIsAvailable(transactionType, transactionValue, transactionDescription);

        getSut().run(callback);

        assertTrue(callback.areTransactionsReceived());
        if (callback.areTransactionsReceived()) {
            Transaction transaction = callback.getTransactions()[0];
            assertThat(transaction, instanceOf(DepositTransaction.class));
            assertEquals(transaction.getDescription(), transactionDescription);
            assertEquals(transaction.getValue(), transactionValue);
        }
    }

    @Test
    public void noTransactionsAreLoadedWhenDbUnavailable() {
        repository.whenDbUnavailable();

        getSut().run(callback);

        assertFalse(callback.areTransactionsReceived());
    }
}