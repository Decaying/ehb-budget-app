package com.example.hansb.budgetapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.interactor.TransactionInteractor;

/**
 * Created by HansB on 15/12/2016.
 */

public class TransactionListActivity extends ListActivity {
    private final TransactionInteractor transactionInteractor;

    public TransactionListActivity(TransactionInteractor transactionInteractor) {
        this.transactionInteractor = transactionInteractor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transactionInteractor.run(transactionInteractorCallback());
    }

    @NonNull
    private TransactionInteractor.Callback transactionInteractorCallback() {
        return new TransactionInteractor.Callback() {
            @Override
            public void onTransactionsRetrieved(Transaction[] message) {
                assert false;
                // TODO: 15/12/2016 display transactions in list
            }
        };
    }

    public ViewGroup getRoot() {
        return (ViewGroup) findViewById(android.R.id.content);
    }
}
