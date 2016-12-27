package com.example.hansb.budgetapp.business;

public class WithdrawTransaction extends TransactionBase {

    WithdrawTransaction(long id, double value, String description, String currency) {
        super(id, value, description, currency);
    }

    WithdrawTransaction(double value, String description, String currency) {
        super(value, description, currency);
    }
}
