package com.example.hansb.budgetapp.business;

public class WithdrawTransaction extends TransactionBase {

    WithdrawTransaction(long id, double value, String description) {
        super(id, value, description);
    }

    WithdrawTransaction(double value, String description) {
        super(value, description);
    }
}
