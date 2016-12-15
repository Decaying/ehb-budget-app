package com.example.hansb.budgetapp.business;

/**
 * Created by HansB on 15/12/2016.
 */

public class TransactionFactoryImpl implements TransactionFactory {

    @Override
    public Transaction create(String type, String description, double value) throws Exception {
        Transaction transaction;

        switch (type) {
            case "DEPOSIT":
                transaction = new DepositTransaction(value, description);
                break;
            default:
                throw new Exception("Invalid transaction type: " + type);
        }

        return transaction;
    }
}
