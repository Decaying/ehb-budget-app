package com.example.hansb.budgetapp.activities;

import android.widget.ListView;

import com.example.hansb.budgetapp.MainActivity;
import com.example.hansb.budgetapp.R;
import com.example.hansb.budgetapp.TestAppInjector;
import com.example.hansb.budgetapp.repository.FakeTransactionRepository;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MainActivityTests extends ActivityTestBase<MainActivity> {
    private FakeTransactionRepository fakeTransactionRepository;

    public MainActivityTests() {
        super(MainActivity.class);
        TestAppInjector testAppInjector = new TestAppInjector();
        MainActivity.Injector = testAppInjector;
        fakeTransactionRepository = new FakeTransactionRepository(testAppInjector);
        testAppInjector.setTransactionRepository(fakeTransactionRepository);
    }

    @Test
    public void testThatListDisplaysTransactionsWhenLoaded() throws Exception {
        fakeTransactionRepository.whenOneDepositTransactionIsAvailable("DEPOSIT", 1.00, "test description");

        getSut();

        assertTrue(fakeTransactionRepository.getAllTransactionsHasBeenCalled());
    }

    @Test
    public void testThatListViewIsPopulated() throws Exception {
        fakeTransactionRepository.whenOneDepositTransactionIsAvailable("DEPOSIT", 1.00, "test description");

        MainActivity activity = getSut();

        assertTrue(fakeTransactionRepository.getAllTransactionsHasBeenCalled());
        ListView listview = (ListView) activity.findViewById(R.id.transactionlistview);

        assertThat(listview.getCount(), is(1));
    }
}
