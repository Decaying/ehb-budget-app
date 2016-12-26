package com.example.hansb.budgetapp.activities;

import android.content.Intent;
import android.widget.Button;

import com.example.hansb.budgetapp.R;
import com.example.hansb.budgetapp.TestAppInjector;
import com.example.hansb.budgetapp.repository.FakeTransactionRepository;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by HansB on 25/12/2016.
 */
public class TransactionDetailActivityTestBase extends ActivityTestBase<TransactionDetailActivity> {
    protected com.example.hansb.budgetapp.repository.FakeTransactionRepository FakeTransactionRepository;

    public TransactionDetailActivityTestBase() {
        super(TransactionDetailActivity.class);
    }

    @Override
    public void configureContainer(TestAppInjector injector) {
        super.configureContainer(injector);

        TransactionDetailActivity.Injector = injector;
        FakeTransactionRepository = new FakeTransactionRepository(injector);
        injector.setTransactionRepository(FakeTransactionRepository);
    }

    @Override
    protected Intent getIntent() {
        Intent intent = new Intent(getInstrumentation().getTargetContext(), TransactionDetailActivity.class);

        intent.putExtra("mode", TransactionDetailActivity.Mode.Create);

        return intent;
    }

    protected void setTransactionDetails(String description, Double value) {
        onView(withId(R.id.transaction_description))
                .perform(typeText(description), closeSoftKeyboard());
        onView(withId(R.id.transaction_value))
                .perform(typeText(value.toString()), closeSoftKeyboard());
    }

    protected void clickSaveTransaction(TransactionDetailActivity activity) {
        clickView(getAddTransactionButton(activity));
    }

    private Button getAddTransactionButton(TransactionDetailActivity activity) {
        Button createTransactionButton = (Button) activity.findViewById(R.id.save_transaction);

        assertThat(createTransactionButton, is(notNullValue()));

        return createTransactionButton;
    }
}
