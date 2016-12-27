package com.example.hansb.budgetapp.interactor;

import com.example.hansb.budgetapp.TestAppInjector;
import com.example.hansb.budgetapp.TestBaseImpl;
import com.example.hansb.budgetapp.business.DepositTransaction;
import com.example.hansb.budgetapp.business.Transaction;
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
public class TransactionInteractorImplTests extends TestBaseImpl<TransactionInteractorImpl> {
    private final TestTransactionInteractorCallback callback;

    private TestAppInjector injector = new TestAppInjector();
    private FakeTransactionRepository repository;

    public TransactionInteractorImplTests() {
        super();

        repository = new FakeTransactionRepository(injector);
        callback = new TestTransactionInteractorCallback();

        injector.setTransactionRepository(repository);
    }

    @Test
    public void canLoadTransactions() throws Exception {
        String transactionDescription = "a Euro saved, is a Euro earned.";
        double transactionValue = 1.00;
        String transactionCurrency = "EUR";

        repository.whenOneDepositTransactionIsAvailable(transactionValue, transactionDescription, transactionCurrency);

        getSut().run(callback);

        assertTrue(callback.areTransactionsReceived());

        Transaction transaction = callback.getTransactions()[0];

        assertThat(transaction, instanceOf(DepositTransaction.class));
        assertEquals(transaction.getDescription(), transactionDescription);
        assertEquals(transaction.getValue(), transactionValue);
        assertEquals(transaction.getCurrency(), transactionCurrency);

    }

    @Test
    public void noTransactionsAreLoadedWhenDbUnavailable() {
        repository.whenDbUnavailable();

        getSut().run(callback);

        assertFalse(callback.areTransactionsReceived());
    }

    @Override
    public TransactionInteractorImpl getSut() {
        return new TransactionInteractorImpl(injector);
    }
}