package com.example.hansb.budgetapp.business;

import java.util.Date;

public class WithdrawTransaction extends TransactionBase {

    WithdrawTransaction(long id, double value, String description, String currency, Date createdDateTime) {
        super(id, value, description, currency, createdDateTime);
    }

    public WithdrawTransaction(double value, String description, String currency, Date creationDateTime) {
        super(value, description, currency, creationDateTime);
    }
}
