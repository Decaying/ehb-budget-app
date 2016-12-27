package com.example.hansb.budgetapp.business;

/**
 * Created by HansB on 7/12/2016.
 */

public class DepositTransaction extends TransactionBase {
    DepositTransaction(long id, double value, String description, String currency) {
        super(id, value, description, currency);
    }

    DepositTransaction(double value, String description, String currency) {
        super(value, description, currency);
    }
}

