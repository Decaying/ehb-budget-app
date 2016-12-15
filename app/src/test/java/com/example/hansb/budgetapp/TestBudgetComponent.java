package com.example.hansb.budgetapp;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by HansB on 12/12/2016.
 */

@Singleton
@Component(modules = BudgetModule.class)
public interface TestBudgetComponent extends BudgetComponent {
    void inject(BaseTest test);
}