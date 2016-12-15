package com.example.hansb.budgetapp.interactor;

import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.repository.TransactionRepository;

import javax.inject.Inject;

/**
 * Created by HansB on 8/12/2016.
 */

public class TransactionInteractorImpl implements TransactionInteractor {
    private final TransactionRepository transactionRepository;
    private final Callback callback;

    @Inject
    public TransactionInteractorImpl(TransactionRepository transactionRepository, Callback callback) {
        this.transactionRepository = transactionRepository;
        this.callback = callback;
    }

    @Override
    public void run() {
        Transaction[] transations = transactionRepository.getLatestTransations();

        callback.onTransactionsRetrieved(transations);
    }
}
