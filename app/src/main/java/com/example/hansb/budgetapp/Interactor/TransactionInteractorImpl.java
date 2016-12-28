package com.example.hansb.budgetapp.interactor;

import android.content.Context;

import com.example.hansb.budgetapp.AppInjector;
import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.business.Transaction;
import com.noveogroup.android.log.Logger;

;

/**
 * Created by HansB on 8/12/2016.
 */

public class TransactionInteractorImpl implements TransactionInteractor {
    private final Logger logger;
    private final TransactionRepository transactionRepository;

    public TransactionInteractorImpl(Context context, AppInjector injector) {
        this.logger = injector.getLogger(TransactionInteractorImpl.class);
        this.transactionRepository = injector.getTransactionRepository(context);
    }

    @Override
    public void run(Callback callback) {
        logger.d("fetching transactions");
        Transaction[] transations;

        try {
            transations = transactionRepository.getAllTransactions();
        } catch (Exception e) {
            logger.e("fetching transactions failed", e);
            return;
        }

        logger.d("fetching transactions success");
        callback.onTransactionsRetrieved(transations);
    }
}
