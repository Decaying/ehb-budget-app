package com.example.hansb.budgetapp;

import com.example.hansb.budgetapp.interactor.TransactionInteractor;
import com.example.hansb.budgetapp.repository.TransactionRepository;

import org.apache.logging.log4j.Logger;

import dagger.Component;

/**
 * Created by HansB on 12/12/2016.
 */

@Component(modules = {
        BudgetModule.class
})
public interface BudgetComponent {
    TransactionRepository transactionRepository();

    Logger logger();

    TransactionInteractor.Callback callback();

    void inject(MainActivity activity);
}
