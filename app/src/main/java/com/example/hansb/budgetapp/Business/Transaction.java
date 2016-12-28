package com.example.hansb.budgetapp.business;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by HansB on 7/12/2016.
 */

public interface Transaction extends Serializable {
    Long getId();

    double getValue();

    String getDescription();

    String getCurrency();

    Date getCreatedDateTime();

    Double getConversionRate();
}
