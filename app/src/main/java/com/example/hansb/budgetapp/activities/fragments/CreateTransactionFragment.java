package com.example.hansb.budgetapp.activities.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.hansb.budgetapp.AppInjector;
import com.example.hansb.budgetapp.R;
import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.business.TransactionFactory;
import com.google.common.base.Strings;
import com.google.common.primitives.Doubles;

import org.apache.logging.log4j.Logger;

/**
 * Created by HansB on 25/12/2016.
 */

public class CreateTransactionFragment
        extends Fragment
        implements Button.OnClickListener {
    private final Logger logger;
    private final TransactionRepository transactionRepository;
    private TransactionFactory transactionFactory;

    public CreateTransactionFragment() {
        super();

        throw new UnsupportedOperationException("we need an injector");
    }

    @SuppressLint("ValidFragment")
    public CreateTransactionFragment(AppInjector injector) {
        super();

        this.logger = injector.getLogger(CreateTransactionFragment.class);
        this.transactionRepository = injector.getTransactionRepository();
        this.transactionFactory = injector.getTransactionFactory();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        logger.debug("Creating transaction detail fragment view");
        return inflater.inflate(R.layout.transaction_detail_fragment,
                container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        logger.debug("transaction detail fragment view has been created");
        super.onActivityCreated(savedInstanceState);

        Button saveTransactionButton = getSaveTransactionButtonView();
        saveTransactionButton.setOnClickListener(this);
    }

    public Button getSaveTransactionButtonView() {
        return (Button) getActivity().findViewById(R.id.save_transaction);
    }

    @Override
    public void onClick(View v) {
        String description = getDescription();
        Double value = getValue();
        TransactionFactory.TransactionType type = tryGetTransactionType();
        String currency = getCurrency();

        if (!validateTransaction(description, value)) {
            logger.debug("validation of transaction values failed");
            return;
        }

        logger.debug("validation of transaction values success");
        Transaction transaction = tryCreateTransaction(type, description, value, currency);

        if (transaction != null) {
            logger.info(String.format("Saving transaction '%s'", description));
            transactionRepository.saveTransaction(transaction);

            navigateToParent();
        }
    }

    @Nullable
    private TransactionFactory.TransactionType tryGetTransactionType() {
        TransactionFactory.TransactionType transactionType = null;
        try {
            transactionType = getTransactionType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionType;
    }

    private Transaction tryCreateTransaction(TransactionFactory.TransactionType transactionType, String description, Double value, String currency) {

        try {
            return transactionFactory.create(transactionType, description, value, currency);
        } catch (Exception ex) {
            logger.error("Failed to create transaction", ex);
        }

        return null;
    }

    private TransactionFactory.TransactionType getTransactionType() throws Exception {
        TransactionFactory.TransactionType transactionType;

        if (isDepositSelected())
            transactionType = TransactionFactory.TransactionType.Deposit;
        else if (isWithdrawSelected())
            transactionType = TransactionFactory.TransactionType.Withdraw;
        else
            throw new Exception("Invalid selection");

        return transactionType;
    }

    private boolean isDepositSelected() {
        return getTypeSelectionDepositView().isChecked();
    }

    private RadioButton getTypeSelectionDepositView() {
        return (RadioButton) getActivity().findViewById(R.id.transaction_type_deposit);
    }

    private boolean isWithdrawSelected() {
        return getTypeSelectionWithdrawView().isChecked();
    }

    private RadioButton getTypeSelectionWithdrawView() {
        return (RadioButton) getActivity().findViewById(R.id.transaction_type_withdraw);
    }

    private boolean validateTransaction(String description, Double value) {
        boolean hasError = false;
        logger.debug("validating transaction values");

        if (Strings.isNullOrEmpty(description)) {
            EditText descriptionView = getTransactionDescriptionView();
            descriptionView.setError(getString(R.string.transaction_description_error));
            hasError = true;
            logger.error(getString(R.string.transaction_description_error));
        }
        if (value <= 0) {
            EditText valueView = getTransactionValueView();
            valueView.setError(getString(R.string.transaction_value_error));
            hasError = true;
            logger.error(getString(R.string.transaction_value_error));
        }

        return !hasError;
    }

    private void navigateToParent() {
        logger.debug("navigating to parent activity");
        startActivity(NavUtils.getParentActivityIntent(getActivity()));
    }

    public String getDescription() {
        return getTransactionDescriptionView().getText().toString();
    }

    public EditText getTransactionDescriptionView() {
        return (EditText) getActivity().findViewById(R.id.transaction_description);
    }

    public Double getValue() {
        String text = getTransactionValueView().getText().toString();

        Double value = Doubles.tryParse(text);

        return value != null ? value : 0D;
    }

    private EditText getTransactionValueView() {
        return (EditText) getActivity().findViewById(R.id.transaction_value);
    }

    public String getCurrency() {
        return getCurrencyView().getSelectedItem().toString();
    }

    private Spinner getCurrencyView() {
        return (Spinner) getActivity().findViewById(R.id.select_transaction_currency);
    }
}
