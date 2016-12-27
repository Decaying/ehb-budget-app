package com.example.hansb.budgetapp.business;

/**
 * Created by HansB on 15/12/2016.
 */

public class TransactionFactoryImpl implements SqlTransactionFactory, TransactionFactory {

    private final String depositType = "DEPOSIT";
    private final String withdrawType = "WITHDRAW";

    @Override
    public String getSqlTypeDeposit() {
        return depositType;
    }

    @Override
    public String getSqlTypeWithdraw() {
        return withdrawType;
    }


    @Override
    public Transaction createFromSql(String type, long id, String description, double value, String currency) throws Exception {
        Transaction transaction;

        switch (type) {
            case depositType:
                transaction = new DepositTransaction(value, description, currency);
                break;
            case withdrawType:
                transaction = new WithdrawTransaction(value, description, currency);
                break;
            default:
                throw new Exception("Invalid transaction type: " + type);
        }

        return transaction;
    }

    @Override
    public Transaction create(TransactionType type, String description, Double value, String currency) throws Exception {
        return createFromSql(getSqlTypeName(type), 0, description, value, currency);
    }

    private String getSqlTypeName(TransactionType type) {
        switch (type) {
            case Deposit:
                return getSqlTypeDeposit();
            case Withdraw:
                return getSqlTypeWithdraw();
        }
        return null;
    }
}
