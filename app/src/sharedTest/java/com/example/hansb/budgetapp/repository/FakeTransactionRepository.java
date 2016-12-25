package com.example.hansb.budgetapp.repository;

import com.example.hansb.budgetapp.AppInjector;
import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.business.TransactionFactory;

import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HansB on 9/12/2016.
 */
public class FakeTransactionRepository implements TransactionRepository {
    private final TransactionFactory transactionFactory;
    private final Logger logger;

    private List<Transaction> transactions = new ArrayList<>();
    private boolean shouldThrowException = false;
    private boolean getAllTransactionsHasBeenCalled = false;

    public FakeTransactionRepository(AppInjector injector) {
        this.transactionFactory = injector.getTransactionFactory();
        this.logger = injector.getLogger(FakeTransactionRepository.class);
    }

    @Override
    public Transaction[] getAllTransactions() throws Exception {
        if (shouldThrowException) {
            logger.debug("failing for test");
            throw new Exception("Had to fail for test");
        }
        logger.debug("Fake transaction repository has been called");
        getAllTransactionsHasBeenCalled = true;
        Transaction[] array = new Transaction[transactions.size()];
        return transactions.toArray(array);
    }

    public void whenOneDepositTransactionIsAvailable(String type, double value, String description) throws Exception {
        logger.debug(String.format("One %s should be available", type));
        transactions.add(transactionFactory.create(type, description, value));
    }

    public void whenOneDepositTransactionIsAvailable() throws Exception {
        whenOneDepositTransactionIsAvailable("DEPOSIT", 1.00, "test description");
    }

    public void whenDbUnavailable() {
        shouldThrowException = true;
    }

    public boolean getAllTransactionsHasBeenCalled() {
        return getAllTransactionsHasBeenCalled;
    }
}
