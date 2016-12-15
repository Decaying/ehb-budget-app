package com.example.hansb.budgetapp.interactor;

import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.repository.TransactionRepository;

import org.apache.logging.log4j.Logger;

/**
 * Created by HansB on 8/12/2016.
 */

public class TransactionInteractorImpl implements TransactionInteractor {
    private final Logger logger;
    private final TransactionRepository transactionRepository;

    public TransactionInteractorImpl(Logger logger, TransactionRepository transactionRepository) {
        this.logger = logger;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(Callback callback) {
        logger.debug("fetching transactions");
        Transaction[] transations = transactionRepository.getLatestTransations();
        logger.debug("fetching transactions done");

        callback.onTransactionsRetrieved(transations);
    }
}
