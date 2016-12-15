package com.example.hansb.budgetapp;

import com.example.hansb.budgetapp.interactor.TransactionInteractor;

import org.junit.Test;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest extends BaseTest {
    FakeTransactionRepository repository;
    FakeTransactionInteractorCallback callback;

    @Inject
    TransactionInteractor interactor;

    public ExampleUnitTest() {
        super();
    }

    @Override
    protected void setupBudgetModule(TestBudgetModule module) {
        repository = new FakeTransactionRepository();
        callback = new FakeTransactionInteractorCallback();

        module.setRepository(repository);
        module.setCallback(callback);
    }


    @Test
    public void canLoadTransactionsOnStartup() {
        Logger.debug("starting test");
        repository.whenOneDepositTransactionIsAvailable();

        Logger.debug("get transactions from repository");
        interactor.run();

        Logger.debug("verify transactions from repository");
        assertThat(callback.getReceivedTransactions().length, is(1));
    }
}