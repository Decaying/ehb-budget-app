package com.example.hansb.budgetapp.activities;

import android.content.Intent;
import android.widget.EditText;

import com.example.hansb.budgetapp.R;
import com.example.hansb.budgetapp.TestAppInjector;
import com.example.hansb.budgetapp.repository.FakeTransactionRepository;

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

}
