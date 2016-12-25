package com.example.hansb.budgetapp.activities;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hansb.budgetapp.MainActivity;
import com.example.hansb.budgetapp.R;
import com.example.hansb.budgetapp.TestAppInjector;
import com.example.hansb.budgetapp.repository.FakeTransactionRepository;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

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
    public void testThatRepositoryGetsCalledWhenLoaded() throws Exception {
        fakeTransactionRepository.whenOneDepositTransactionIsAvailable();

        getSut();

        assertTrue(fakeTransactionRepository.getAllTransactionsHasBeenCalled());
    }

    @Test
    public void testThatListViewIsPopulated() throws Exception {
        fakeTransactionRepository.whenOneDepositTransactionIsAvailable();

        MainActivity activity = getSut();

        ListView listview = getTransactionListView(activity);

        assertThat(listview.getCount(), is(1));
    }

    @Test
    public void testThatListViewContainsDetails() throws Exception {
        fakeTransactionRepository.whenOneDepositTransactionIsAvailable("DEPOSIT", 123.7458, "Purchased an instrumentation test");

        MainActivity activity = getSut();

        View transactionView = getTransactionItemView(activity, 0);

        TextView headerLine = (TextView) transactionView.findViewById(R.id.headerLine);
        TextView detailLine = (TextView) transactionView.findViewById(R.id.detailLine);

        assertThat(headerLine.getText().toString(), is("Purchased an instrumentation test"));
        assertThat(detailLine.getText().toString(), is("Transaction value: 123.75"));
    }

    private View getTransactionItemView(MainActivity activity, int position) {
        View transactionView = getTransactionListView(activity).getChildAt(position);

        assertThat(transactionView, is(notNullValue()));

        return transactionView;
    }

    private ListView getTransactionListView(MainActivity activity) {
        ListView listview = (ListView) activity.findViewById(R.id.transactionlistview);

        assertThat(listview, is(notNullValue()));

        return listview;
    }
}
