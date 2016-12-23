package com.example.hansb.budgetapp.repository;

import com.example.hansb.budgetapp.AppInjector;
import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.business.TransactionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HansB on 9/12/2016.
 */
public class FakeTransactionRepository implements TransactionRepository {
    private final TransactionFactory transactionFactory;

    private List<Transaction> transactions = new ArrayList<>();
    private boolean shouldThrowException = false;
    private boolean getAllTransactionsHasBeenCalled = false;

    public FakeTransactionRepository(AppInjector injector) {
        this.transactionFactory = injector.getTransactionFactory();
    }

    @Override
    public Transaction[] getAllTransactions() throws Exception {
        if (shouldThrowException) {
            throw new Exception("Had to fail for test.");
        }
        getAllTransactionsHasBeenCalled = true;
        Transaction[] array = new Transaction[transactions.size()];
        return transactions.toArray(array);
    }

    public void whenOneDepositTransactionIsAvailable(String type, double value, String description) throws Exception {
        transactions.add(transactionFactory.create(type, description, value));
    }

    public void whenDbUnavailable() {
        shouldThrowException = true;
    }

    public boolean getAllTransactionsHasBeenCalled() {
        return getAllTransactionsHasBeenCalled;
    }
}
