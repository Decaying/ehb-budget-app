package com.example.hansb.budgetapp;

import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.interactor.TransactionInteractor;
import com.example.hansb.budgetapp.interactor.TransactionInteractorImpl;

import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest extends BaseTest<TransactionInteractorImpl> {
    FakeTransactionRepository repository;

    public ExampleUnitTest() {
        super();

        repository = new FakeTransactionRepository(Logger);
    }

    @Override
    protected TransactionInteractorImpl getSut() {
        return new TransactionInteractorImpl(Logger, repository);
    }

    @Test
    public void canLoadTransactionsOnStartup() {
        final TransactionInteractor.Callback callback = mock(TransactionInteractor.Callback.class);

        Logger.debug("starting test");
        repository.whenOneDepositTransactionIsAvailable();

        Logger.debug("getting transactions from repository");
        getSut().run(callback);

        verify(callback, only()).onTransactionsRetrieved(any(Transaction[].class));
    }
}