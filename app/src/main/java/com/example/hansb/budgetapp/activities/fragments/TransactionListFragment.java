package com.example.hansb.budgetapp.activities.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hansb.budgetapp.AppInjector;
import com.example.hansb.budgetapp.R;
import com.example.hansb.budgetapp.activities.TransactionDetailActivity;
import com.example.hansb.budgetapp.business.DepositTransaction;
import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.interactor.TransactionInteractor;

import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by HansB on 15/12/2016.
 */

public class TransactionListFragment
        extends Fragment
        implements FloatingActionButton.OnClickListener {

    private final Logger logger;
    private final TransactionInteractor transactionInteractor;

    public TransactionListFragment() {
        super();

        throw new UnsupportedOperationException("we need an injector");
    }

    @SuppressLint("ValidFragment")
    public TransactionListFragment(AppInjector injector) {
        super();
        this.logger = injector.getLogger(TransactionListFragment.class);
        this.transactionInteractor = injector.getTransactionInteractor();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        logger.debug("Creating transaction list fragment view");
        return inflater.inflate(R.layout.transaction_list_fragment,
                container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        logger.debug("transaction list fragment view has been created");
        super.onActivityCreated(savedInstanceState);

        logger.debug("Setting transaction adapter");
        ListView transactionsView = findListView();
        transactionsView.setAdapter(getTransactionAdapter());

        FloatingActionButton addTransactionButton = getAddTransactionButtonView();
        addTransactionButton.setOnClickListener(this);

        loadTransactions();
    }

    private ListView findListView() {
        return (ListView) getActivity().findViewById(R.id.transactionlist);
    }

    private FloatingActionButton getAddTransactionButtonView() {
        return (FloatingActionButton) getActivity().findViewById(R.id.add_transaction);
    }

    private void loadTransactions() {
        logger.debug("Loading transactions");
        transactionInteractor.run(transactionInteractorCallback());
    }

    @NonNull
    private TransactionInteractor.Callback transactionInteractorCallback() {
        return new TransactionInteractor.Callback() {
            @Override
            public void onTransactionsRetrieved(Transaction[] message) {
                displayTransactions(Arrays.asList(message));
            }
        };
    }

    private void displayTransactions(List<Transaction> transactions) {
        logger.debug("Transactions loaded, displaying now");
        findListView().setAdapter(getTransactionAdapter(transactions));
    }

    private TransactionAdapter getTransactionAdapter() {
        return getTransactionAdapter(new ArrayList<Transaction>());
    }

    private TransactionAdapter getTransactionAdapter(List<Transaction> transactions) {
        return new TransactionAdapter(
                getActivity(),
                R.layout.transaction_list_item,
                transactions);
    }

    @Override
    public void onClick(View v) {
        createNewTransaction();
    }

    private void createNewTransaction() {
        logger.info("Creating a new transaction");
        Intent createTransaction = new Intent(getActivity(), TransactionDetailActivity.class);
        createTransaction.putExtra("mode", TransactionDetailActivity.Mode.Create);
        startActivity(createTransaction);
    }

    private class TransactionAdapter extends ArrayAdapter<Transaction> {

        private final Context context;
        private final List<Transaction> transactions;

        public TransactionAdapter(Context context, int textViewResourceId,
                                  List<Transaction> transactions) {
            super(context, textViewResourceId, transactions);
            this.context = context;
            this.transactions = transactions;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Transaction currentTransaction = transactions.get(position);

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View transactionListItemView = inflater.inflate(R.layout.transaction_list_item, parent, false);

            TextView headerLine = (TextView) transactionListItemView.findViewById(R.id.headerLine);
            TextView detailLine = (TextView) transactionListItemView.findViewById(R.id.detailLine);
            ImageView imageView = (ImageView) transactionListItemView.findViewById(R.id.icon);

            headerLine.setText(currentTransaction.getDescription());
            detailLine.setText(String.format("Transaction value: %.2f", currentTransaction.getValue()));

            if (currentTransaction instanceof DepositTransaction)
                imageView.setImageResource(R.drawable.deposit);
            else
                imageView.setImageResource(R.drawable.withdrawal);

            return transactionListItemView;
        }
    }
}
