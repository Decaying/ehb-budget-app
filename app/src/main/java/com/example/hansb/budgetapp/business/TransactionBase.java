package com.example.hansb.budgetapp.business;

import java.util.Date;

/**
 * Created by HansB on 27/12/2016.
 */
public abstract class TransactionBase implements Transaction {
    protected final long id;
    protected final double value;
    protected final String description;
    protected final String currency;
    private final Date createdDateTime;
    private final Double conversionRate;

    TransactionBase(long id, double value, String description, String currency, Date createdDateTime, double conversionRate) {
        this.id = id;
        this.value = value;
        this.description = description;
        this.currency = currency;
        this.createdDateTime = createdDateTime;
        this.conversionRate = conversionRate;
    }

    public TransactionBase(double value, String description, String currency, Date createdDateTime) {
        this(0, value, description, currency, createdDateTime, 0D);
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

    @Override
    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    @Override
    public Double getConversionRate() {
        return conversionRate;
    }
}
