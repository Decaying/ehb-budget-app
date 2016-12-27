package com.example.hansb.budgetapp.business;

/**
 * Created by HansB on 27/12/2016.
 */
public abstract class TransactionBase implements Transaction {
    protected final long id;
    protected final double value;
    protected final String description;
    protected final String currency;

    TransactionBase(double value, String description, String currency) {
        this(0, value, description, currency);
    }

    TransactionBase(long id, double value, String description, String currency) {
        this.id = id;
        this.value = value;
        this.description = description;
        this.currency = currency;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getCurrency() {
        return currency;
    }
}
