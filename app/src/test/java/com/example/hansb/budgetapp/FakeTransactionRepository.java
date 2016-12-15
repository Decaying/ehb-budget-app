package com.example.hansb.budgetapp;

import com.example.hansb.budgetapp.business.DepositTransaction;
import com.example.hansb.budgetapp.business.Transaction;
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

    public FakeTransactionRepository(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Transaction[] getLatestTransations() {
        Transaction[] array = new Transaction[transactions.size()];
        logger.debug("returning " + transactions.size() + " transactions");
        return transactions.toArray(array);
    }

    public void whenOneDepositTransactionIsAvailable() {
        transactions.add(new DepositTransaction());
        logger.debug("one deposit transaction is now available");
    }
}
