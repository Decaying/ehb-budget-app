package com.example.hansb.budgetapp.activities;

import android.support.test.rule.ActivityTestRule;

import com.example.hansb.budgetapp.MainActivity;
import com.example.hansb.budgetapp.TestAppInjector;
import com.example.hansb.budgetapp.repository.FakeTransactionRepository;

import org.junit.Test;

public class MainActivityTests extends ActivityTestBase<MainActivity> {
    private FakeTransactionRepository fakeTransactionRepository;

    public MainActivityTests() {
        TestAppInjector testAppInjector = new TestAppInjector();

        MainActivity.Injector = testAppInjector;

        fakeTransactionRepository = new FakeTransactionRepository(testAppInjector);

        testAppInjector.setTransactionRepository(fakeTransactionRepository);
    }

    @Override
    protected ActivityTestRule<MainActivity> getRule() {
        return new MainActivityTestRule();
    }

    private class MainActivityTestRule extends ActivityTestRule<MainActivity> {
        public MainActivityTestRule() {
            super(MainActivity.class);
        }
    }

    @Test
    public void testThatListDisplaysTransactionsWhenLoaded() throws Exception {
        fakeTransactionRepository.whenOneDepositTransactionIsAvailable("DEPOSIT", 1.00, "test description");

        getSut();

        assertTrue(fakeTransactionRepository.getAllTransactionsHasBeenCalled());
    }
}
