package com.example.hansb.budgetapp;

import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.business.TransactionFactory;
import com.example.hansb.budgetapp.repository.TransactionRepository;

import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HansB on 9/12/2016.
 */
public class FakeTransactionRepository implements TransactionRepository {
    private final Logger logger;
    private List<Transaction> transactions = new ArrayList<Transaction>();
    private boolean shouldThrowException = false;
    private TransactionFactory transactionFactory;

    public FakeTransactionRepository(Logger logger, TransactionFactory transactionFactory) {
        this.logger = logger;
        this.transactionFactory = transactionFactory;
    }

    @Override
    public Transaction[] getLatestTransations() throws Exception {
        if (shouldThrowException) {
            throw new Exception("Had to fail for test.");
        }
        Transaction[] array = new Transaction[transactions.size()];
        logger.debug("returning " + transactions.size() + " transactions");
        return transactions.toArray(array);
    }

    public void whenOneDepositTransactionIsAvailable(String type, double value, String description) throws Exception {
        transactions.add(transactionFactory.create(type, description, value));
        logger.debug("one deposit transaction is now available");
    }

    public void whenDbUnavailable() {
        shouldThrowException = true;
        logger.debug("repository access will throw an exception");
    }
}
