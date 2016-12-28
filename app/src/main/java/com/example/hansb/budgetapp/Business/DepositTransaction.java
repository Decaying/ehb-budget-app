package com.example.hansb.budgetapp.business;

import java.util.Date;

/**
 * Created by HansB on 7/12/2016.
 */

public class DepositTransaction extends TransactionBase {
    DepositTransaction(long id, double value, String description, String currency, Date createdDateTime, double conversionRate) {
        super(id, value, description, currency, createdDateTime, conversionRate);
    }

    public DepositTransaction(double value, String description, String currency, Date creationDateTime) {
        super(value, description, currency, creationDateTime);
    }
}

