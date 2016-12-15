package com.example.hansb.budgetapp;

import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.interactor.TransactionInteractor;

/**
 * Created by HansB on 9/12/2016.
 */
public class FakeTransactionInteractorCallback implements TransactionInteractor.Callback {
    private Transaction[] receivedTransactions;

    @Override
    public void onTransactionsRetrieved(Transaction[] message) {
        receivedTransactions = message;
    }

    public Transaction[] getReceivedTransactions() {
        return receivedTransactions;
    }
}
