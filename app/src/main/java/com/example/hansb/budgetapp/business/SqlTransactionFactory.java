package com.example.hansb.budgetapp.business;

import java.util.Date;

/**
 * Created by HansB on 27/12/2016.
 */

public interface SqlTransactionFactory {
    Transaction createFromSql(String type, long id, String description, double value, String currency, Date dateTimeCreated) throws Exception;

    String getSqlTypeDeposit();

    String getSqlTypeWithdraw();
}
