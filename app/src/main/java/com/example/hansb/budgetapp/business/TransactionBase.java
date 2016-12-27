package com.example.hansb.budgetapp.business;

/**
 * Created by HansB on 27/12/2016.
 */
public class TransactionBase implements Transaction {
    protected final long id;
    protected final double value;
    protected final String description;

    TransactionBase(double value, String description) {
        this(0, value, description);
    }

    TransactionBase(long id, double value, String description) {
        this.id = id;
        this.value = value;
        this.description = description;
    }

    @Override
    public Long getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
