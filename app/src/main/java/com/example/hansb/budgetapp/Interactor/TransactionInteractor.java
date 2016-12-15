package com.example.hansb.budgetapp.interactor;

import com.example.hansb.budgetapp.business.Transaction;

/**
 * Created by HansB on 7/12/2016.
 */
public interface TransactionInteractor {
    void run();

    interface Callback {
        void onTransactionsRetrieved(Transaction[] message);
    }
}
