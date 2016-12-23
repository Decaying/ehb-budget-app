package com.example.hansb.budgetapp.interactor;

import com.example.hansb.budgetapp.AppInjector;
import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.business.Transaction;

import org.apache.logging.log4j.Logger;

/**
 * Created by HansB on 8/12/2016.
 */

public class TransactionInteractorImpl implements TransactionInteractor {
    private final Logger logger;
    private final TransactionRepository transactionRepository;

    public TransactionInteractorImpl(AppInjector injector) {
        this.logger = injector.getLogger(TransactionInteractorImpl.class);
        this.transactionRepository = injector.getTransactionRepository();
    }

    @Override
    public void run(Callback callback) {
        logger.debug("fetching transactions");
        Transaction[] transations;

        try {
            transations = transactionRepository.getAllTransactions();
        } catch (Exception e) {
            logger.error("fetching transactions failed", e);
            return;
        }

        logger.debug("fetching transactions success");
        callback.onTransactionsRetrieved(transations);
    }
}
