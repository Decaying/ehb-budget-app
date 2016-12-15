package com.example.hansb.budgetapp;

import com.example.hansb.budgetapp.business.DepositTransaction;
import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HansB on 9/12/2016.
 */
public class FakeTransactionRepository implements TransactionRepository {
    private List<Transaction> transactions = new ArrayList<Transaction>();

    @Override
    public Transaction[] getLatestTransations() {
        Transaction[] array = new Transaction[transactions.size()];
        return transactions.toArray(array);
    }

    public void whenOneDepositTransactionIsAvailable() {
        transactions.add(new DepositTransaction());
    }
}
