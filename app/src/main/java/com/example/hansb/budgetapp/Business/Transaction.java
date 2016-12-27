package com.example.hansb.budgetapp.business;

import java.util.Date;

/**
 * Created by HansB on 7/12/2016.
 */

public interface Transaction {
    Long getId();

    double getValue();

    String getDescription();

    String getCurrency();

    Date getCreatedDateTime();
}
