package com.example.hansb.budgetapp.business;

/**
 * Created by HansB on 15/12/2016.
 */
public interface TransactionFactory {
    Transaction create(String type, String description, double value) throws Exception;

    Transaction createDeposit(String description, Double value) throws Exception;
}
