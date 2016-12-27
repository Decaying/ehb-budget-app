package com.example.hansb.budgetapp.business;

/**
 * Created by HansB on 7/12/2016.
 */

public class DepositTransaction extends TransactionBase {
    DepositTransaction(long id, double value, String description) {
        super(id, value, description);
    }

    DepositTransaction(double value, String description) {
        super(value, description);
    }
}

