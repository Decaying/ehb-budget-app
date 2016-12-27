package com.example.hansb.budgetapp.business;

import java.util.Date;

/**
 * Created by HansB on 15/12/2016.
 */
public interface TransactionFactory {
    enum TransactionType {
        Deposit,
        Withdraw
    }

    Transaction create(TransactionType type, String description, Double value, String currency, Date createdDateTime) throws Exception;
}