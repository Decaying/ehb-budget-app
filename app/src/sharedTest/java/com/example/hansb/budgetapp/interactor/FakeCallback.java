package com.example.hansb.budgetapp.interactor;

import com.example.hansb.budgetapp.business.Transaction;

/**
 * Created by HansB on 15/12/2016.
 */
class FakeCallback implements TransactionInteractor.Callback {

    private boolean transactionsReceived;
    private Transaction[] transactions;

    @Override
    public void onTransactionsRetrieved(Transaction[] message) {
        this.transactions = message;
        transactionsReceived = true;
    }

    public boolean areTransactionsReceived() {
        return transactionsReceived;
    }

    public Transaction[] getTransactions() {
        return transactions;
    }
}
