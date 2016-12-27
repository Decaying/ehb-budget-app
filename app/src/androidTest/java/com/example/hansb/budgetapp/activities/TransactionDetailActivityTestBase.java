package com.example.hansb.budgetapp.activities;

import android.content.Intent;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.hansb.budgetapp.R;
import com.example.hansb.budgetapp.TestAppInjector;
import com.example.hansb.budgetapp.repository.FakeTransactionRepository;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by HansB on 25/12/2016.
 */
public class TransactionDetailActivityTestBase extends ActivityTestBase<TransactionDetailActivity> {
    private com.example.hansb.budgetapp.repository.FakeTransactionRepository fakeTransactionRepository;

    public TransactionDetailActivityTestBase() {
        super(TransactionDetailActivity.class);
    }

    protected FakeTransactionRepository getFakeTransactionRepository() {
        return fakeTransactionRepository;
    }

    @Override
    public void configureContainer(TestAppInjector injector) {
        super.configureContainer(injector);

        TransactionDetailActivity.Injector = injector;
        fakeTransactionRepository = new FakeTransactionRepository(injector);
        injector.setTransactionRepository(fakeTransactionRepository);
    }

    @Override
    protected Intent getIntent() {
        Intent intent = new Intent(getInstrumentation().getTargetContext(), TransactionDetailActivity.class);

        intent.putExtra("mode", TransactionDetailActivity.Mode.Create);

        return intent;
    }

    protected void setTransactionDetails() {
        setTransactionDetails("Paycheck!", 1000.00);
    }

    protected void setTransactionDetails(String description, Double value) {
        setTransactionDescription(description);
        setTransactionValue(value);
    }

    protected void setTransactionValue(Double value) {
        typeInView(R.id.transaction_value, value.toString());
    }

    protected void setTransactionDescription(String description) {
        typeInView(R.id.transaction_description, description);
    }

    protected Double getTransactionValue(TransactionDetailActivity activity) {
        return Double.parseDouble(((EditText) getView(activity, R.id.transaction_value)).getText().toString());
    }

    protected void clickSaveTransaction() {
        clickView(R.id.save_transaction);
    }

    protected void setTransactionTypeWithdraw() {
        clickView(R.id.transaction_type_withdraw);
    }

    protected void setCurrency(String currency) {
        clickView(R.id.select_transaction_currency);
        onData(allOf(is(instanceOf(String.class)), is(currency))).perform(click());
        onView(withId(R.id.select_transaction_currency)).check(matches(withSpinnerText(is(currency))));
    }

    protected String getCurrency(TransactionDetailActivity activity) {
        return ((Spinner) getView(activity, R.id.select_transaction_currency)).getSelectedItem().toString();
    }
}
