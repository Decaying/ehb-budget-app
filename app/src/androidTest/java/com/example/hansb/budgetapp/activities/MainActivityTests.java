package com.example.hansb.budgetapp.activities;

import android.support.test.rule.ActivityTestRule;

import com.example.hansb.budgetapp.MainActivity;

public class MainActivityTests extends ActivityTestBase<MainActivity> {
    @Override
    protected ActivityTestRule<MainActivity> getRule() {
        return new MainActivityTestRule();
    }

    private class MainActivityTestRule extends ActivityTestRule<MainActivity> {
        public MainActivityTestRule() {
            super(MainActivity.class);
        }
    }
}
