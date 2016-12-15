package com.example.hansb.budgetapp.repository;

import com.example.hansb.budgetapp.business.Transaction;

/**
 * Created by HansB on 7/12/2016.
 */

public interface TransactionRepository {
    Transaction[] getLatestTransations() throws Exception;
}
