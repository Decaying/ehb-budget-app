package com.example.hansb.budgetapp.budgetapp;

import com.example.hansb.budgetapp.business.Transaction;

/**
 * Created by HansB on 7/12/2016.
 */

public interface TransactionRepository {
    Transaction[] getAllTransactions() throws Exception;

    Transaction saveTransaction(Transaction transaction);
}
