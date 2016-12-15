package com.example.hansb.budgetapp.business;

/**
 * Created by HansB on 7/12/2016.
 */

public class DepositTransaction implements Transaction {
    private final double value;
    private final String description;

    public DepositTransaction(double value, String description) {

        this.value = value;
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
